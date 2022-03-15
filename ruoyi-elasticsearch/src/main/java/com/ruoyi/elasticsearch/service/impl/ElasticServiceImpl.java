package com.ruoyi.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.elasticsearch.domain.SystemLog;
import com.ruoyi.elasticsearch.domain.vo.FeatureData;
import com.ruoyi.elasticsearch.domain.vo.FieldData;
import com.ruoyi.elasticsearch.service.IElasticService;
import com.ruoyi.elasticsearch.util.NumberCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ResourceNotFoundException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
@Slf4j
@Service("elasticService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ElasticServiceImpl implements IElasticService {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    private  AttckServiceImpl attckService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void createIndex() {
        boolean isCreate = elasticsearchTemplate.indexOps(IndexCoordinates.of("winlog_aaaa")).create();
        System.out.println(isCreate);
    }

    @Override
    public void deleteIndex(String index) {
        boolean delete = elasticsearchTemplate.indexOps(IndexCoordinates.of("winlog_aaaa")).delete();
        System.out.println(delete);
    }

    @Override
    public List queryIndex(String name) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(name);
        // 执行搜索,向ES发起http请求
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        String[] indices = getIndexResponse.getIndices();
        List<String> list = Arrays.asList(indices);
        return list;
    }

    @Override
    public void addEsContent() {
        SystemLog systemLog = new SystemLog();
        systemLog.setUuid("adndndndddd");

        IndexQuery indexQuery = new IndexQueryBuilder()
            .withId(systemLog.getUuid().toString())
            .withObject(systemLog)
            .build();
        String index = elasticsearchTemplate.index(indexQuery, IndexCoordinates.of("winlog_aaaa",""));
        System.out.println(index);
    }

    @Override
    public List queryList() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.matchQuery("uuid.keyword", "c64ee747-7926-4fd3-95bf-556ef8e311a6"))
            .build();
        SearchHits<SystemLog> searchHits =  elasticsearchTemplate.search(
            nativeSearchQuery, SystemLog.class,IndexCoordinates.of("winlog_2022.02.23_172.20.1.131"));
        if (searchHits.getTotalHits() == 0) {
            throw new ResourceNotFoundException("没有相关的记录");
        }
        ArrayList<SystemLog> list = new ArrayList<>();
        for (SearchHit<SystemLog> hit: searchHits){
            list.add(hit.getContent());
        }
        return list;
    }

    @Override
    public boolean queryIndexsDataList(String index,String startTime,String endTime){
        boolean flag = true;

        List<FeatureData> orRulesList = attckService.getfeatureDataList();


        List<String> list = null;
        try {
            list = queryIndex(index);
        } catch (IOException e) {
            log.error("没有查到相关的index",e);
            flag = false;
        }
        if(list != null && list.size()>0){
            list.forEach(e ->{
                try {
                    int from = 0;
                    int size = 500;
                    org.elasticsearch.search.SearchHits hits = searchIndexDate(e,startTime,endTime,orRulesList,from,size);
                    dealResult(e,hits);
                    long totalHits = hits.getTotalHits().value;
                    long pageIndex = 0;
                    if(totalHits>0){
                        if(totalHits%size>0){
                            pageIndex = (totalHits/size)+1;
                        }else{
                            pageIndex = (totalHits/size);
                        }
                    }
                    if(pageIndex>1){
                        for(int i = 1 ;i<=pageIndex;i++){
                            from = i*size;
                            hits = searchIndexDate(e,startTime,endTime,orRulesList,from,size);
                            dealResult(e,hits);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();

                }
            });
        }
        return flag;
    }


    /**
     *  查询es 数据
     * @param index
     * @param startTime
     * @param endTime
     * @param ruleList
     * @param from
     * @param size
     * @return
     * @throws IOException
     */
    public org.elasticsearch.search.SearchHits searchIndexDate(String index,String startTime,String endTime,List<FeatureData> ruleList,int from ,int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp").gte(startTime).lt(endTime);

        QueryBuilder queryBuilder1 = ruleOrQueryBuilder(ruleList);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
            .must(rangeQueryBuilder)
            .must(queryBuilder1);

        searchSourceBuilder.query(queryBuilder).from(from).size(size);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //处理返回结果
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        return hits;
    }

    /**
     *
     * @param featureDataList
     * @return
     */
    public  QueryBuilder ruleOrQueryBuilder(List<FeatureData> featureDataList){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for(FeatureData featureData: featureDataList){
            boolQueryBuilder.should(circleQueryBuilder(featureData));
        }
        return boolQueryBuilder;
    }

    /**
     * 查询拼接
     * @param featureData
     * @return
     */
    public  QueryBuilder circleQueryBuilder(FeatureData featureData){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(featureData.getList() != null){
            for(FeatureData featureData1: featureData.getList()){
                if(featureData.getOperate().equals("AND")){
                    boolQueryBuilder.must(circleQueryBuilder(featureData1));
                }else{
                    boolQueryBuilder.should(circleQueryBuilder(featureData1));
                }
            }
        }
        if(featureData.getData() != null){
            FieldData data = featureData.getData();
            if(featureData.getData().getOperator().equals("EQ")){
                return QueryBuilders.matchQuery(data.getFieldName(), data.getValue());
            }else if(featureData.getData().getOperator().equals("NOTIN")){
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.matchQuery(data.getFieldName(), data.getValue()));
            }else if(featureData.getData().getOperator().equals("LIKE")){
                return QueryBuilders.fuzzyQuery(data.getFieldName(), data.getValue());
            }else{
                return QueryBuilders.matchAllQuery();
            }
        }
        return boolQueryBuilder;
    }

    /**
     * 插入reids
     * @param indexName
     * @param hits
     * @return
     */
    private long dealResult(String indexName,org.elasticsearch.search.SearchHits hits){

        //BulkRequest bulkRequest = new BulkRequest();
        Date date= new Date();
        long aLong = 0 ;
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        for (org.elasticsearch.search.SearchHit hit : hits.getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            //bulkRequest.add(new IndexRequest("winlog_test").opType(DocWriteRequest.OpType.CREATE).source(map, XContentType.JSON));
            DefaultTypedTuple typedTuple = new DefaultTypedTuple(JSONObject.toJSON(map).toString(), Double.valueOf(date.getTime()));
            tuples.add(typedTuple);
        }
        if(tuples.size() > 0){
            aLong = redisTemplate.opsForZSet().add(RedisKeyConstants.ELASTIC_DATE + DateUtils.getDate() + ":" + indexName, tuples);
            redisTemplate.expire(RedisKeyConstants.ELASTIC_DATE + DateUtils.getDate() + ":" + indexName,24*3600, TimeUnit.SECONDS);
        }
        /*try {
            int size = bulkRequest.requests().size();
            if(size>0) restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return aLong;
    }

    /**
     * 根据uuid 查询es
     * @param indexName
     * @param uuidList
     */
    public org.elasticsearch.search.SearchHits searchESdateByUUID(String indexName,List<String> uuidList) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(BuilderList(uuidList)).from(0).size(uuidList.size());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //处理返回结果
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        return hits;
    }

    public BoolQueryBuilder BuilderList (List<String> uuidList){
        Objects.requireNonNull(uuidList,"UUID为空！");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        uuidList.forEach( e -> {
            boolQueryBuilder.should(QueryBuilders.termQuery("uuid.keyword", e));
        });
        return boolQueryBuilder;
    }

    @Override
    public void createAlert(List<String> alertList) throws IOException{

        List<Map<String, String>> orRulesList = attckService.createOrRulesList();

        if(alertList.size() > 0){
            alertList.forEach(e->{
                JSONObject jsonObject = JSONObject.parseObject(e);
                orRulesList.forEach(v->{
                    FeatureData featureData = null;
                    String attckType= null;
                    Iterator<Map.Entry<String, String>> iterator = v.entrySet().iterator();
                    if(iterator.hasNext()){
                        Map.Entry<String, String> next = iterator.next();
                        if(next.getKey().equals("rule")) featureData = JSONObject.parseObject(next.getValue(),FeatureData.class);
                        if(next.getKey().equals("attckType")) attckType = next.getValue();
                    }
                    String rule = parseRule(featureData, jsonObject, "");
                    boolean flag = NumberCalc.calc(rule);
                    if(flag){
                        jsonObject.put("attckType",attckType);
                        redisTemplate.opsForValue().set("alert:data:"+DateUtils.getDate()+":"+jsonObject.getString("uuid"),jsonObject.toString());
                    }
                });
            });
        }
    }


    /**
     * 解析规则去匹配数据 返回 计算公式 字符串
     * @param featureData
     * @param map
     * @param type
     * @return
     */
    public   String parseRule(FeatureData featureData , Map map,String type){

        String str = "";
        if(featureData.getList() != null){
            for(int i = 0;i<featureData.getList().size();i++){
                if(i == 0) str += "(";
                if(i == featureData.getList().size()-1)  str += parseRule(featureData.getList().get(i),map,"")+")";
                else{
                    if(featureData.getOperate().equals("AND")){
                        str += parseRule(featureData.getList().get(i),map,"*");
                    }else{
                        str += parseRule(featureData.getList().get(i),map,"+");
                    }
                }
            }
        }
        str += getTrueorFalse(featureData,map) + type;
        return  str ;
    }

    /**
     *  规则匹配数据
     * @param featureData
     * @param map
     * @return
     */
    public  String getTrueorFalse(FeatureData featureData,Map<String,String> map){
        String str = "";
        if(featureData.getData() != null){
            FieldData data = featureData.getData();
            String key = data.getFieldName();
            String val = field(key, map);
            String value = data.getValue();
            if(StringUtils.isEmpty(val)) return "0";
            if(data.getOperator().equals("EQ")){
                str =  val.equals(value)?"1":"0";
            }else if(data.getOperator().equals("NOTIN")){
                str = (!val.contains(value))?"1":"0";
            }else if(data.getOperator().equals("LIKE")){
                str = (val.indexOf(value)>-1)?"1":"0";
            }else{
                str = val.equals(value)?"1":"0";
            }
        }
        return str;
    }

    /**
     * 根据字段取得Map数据
     * @param str
     * @param map
     * @return
     */
    public String field(String str,Map map){
        String[] keys = str.split("\\.");
        return field0(keys,map,0);
    }

    /**
     * 根据字段取得Map数据
     * @param key
     * @param map
     * @param fool
     * @return
     */
    public  String field0(String[] key,Map map,int fool){
        String obj;
        Object o =map.get(key[fool]);
        if(o == null) return null;
        else{
            if(o instanceof  Map){
                if(fool<key.length-1) fool++;
                obj = field0(key,(Map)o,fool);
            }else{
                return o.toString();
            }
        }
        return obj;
    }
}
