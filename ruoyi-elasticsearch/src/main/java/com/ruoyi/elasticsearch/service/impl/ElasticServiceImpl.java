package com.ruoyi.elasticsearch.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.elasticsearch.domain.AttckFeature;
import com.ruoyi.elasticsearch.domain.SystemLog;
import com.ruoyi.elasticsearch.service.IElasticService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.ResourceNotFoundException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
@Service("elasticService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ElasticServiceImpl implements IElasticService {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    private  AttckServiceImpl attckService;

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
    public List queryIndex(String name) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(name);
        // 执行搜索,向ES发起http请求
        GetIndexResponse getIndexResponse = null;
        try {
            getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
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
    public List queryIndexsDataList(String index,String startTime,String endTime) {
        Map<String, String> orRules = attckService.createOrRules();
        List<String> list = queryIndex(index);
        if(list != null && list.size()>0){
            list.forEach(e ->{
                try {
                    searchIndexDate(e,startTime,endTime,orRules);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }
        return null;
    }

    public String searchIndexDate(String index,String startTime,String endTime,Map<String,String> ruleMap) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp").gte(startTime).lt(endTime);


       /* BoolQueryBuilder must1 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("message", "123").operator(Operator.AND))
            .must(QueryBuilders.matchQuery("message", "456").operator(Operator.AND));

        BoolQueryBuilder must2 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("message", "/etc/NetworkManager/dispatcher.d/11-dhclient ens192 dhcp4-change").operator(Operator.AND))
            .must(QueryBuilders.matchQuery("message", "localhost snoopy[2674]").operator(Operator.AND));

        BoolQueryBuilder should = QueryBuilders.boolQuery().should(must1).should(must2);*/
        BoolQueryBuilder boolQueryBuilder = ORrulesQueryBuilder(ruleMap);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
            .must(rangeQueryBuilder)
            .must(boolQueryBuilder);

        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //处理返回结果
        org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> listDate = dealResult(hits);
        return hits.toString();
    }

    /**
     *
     * @param ruleMap
     * @return
     */
    public BoolQueryBuilder ORrulesQueryBuilder(Map<String,String> ruleMap){
        Objects.requireNonNull(ruleMap,"规则列表为空！");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        ruleMap.forEach((k,v) -> {
            boolQueryBuilder.should(QueryBuilders.matchQuery(k, v));
        });

        return boolQueryBuilder;
    }


    private List<Map<String, Object>> dealResult(org.elasticsearch.search.SearchHits hits){
        List<Map<String, Object>> result = new ArrayList<>();
        for (org.elasticsearch.search.SearchHit hit : hits.getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            result.add(map);
        }
        return result;
    }

}
