package com.ruoyi.elasticsearch.service.impl;

import com.ruoyi.elasticsearch.domain.SystemLog;
import com.ruoyi.elasticsearch.service.IElasticService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.ResourceNotFoundException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.QueryBuilders;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
@Service("elasticService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ElasticServiceImpl implements IElasticService {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    private final RestHighLevelClient restHighLevelClient;


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
        String index = elasticsearchTemplate.index(indexQuery, IndexCoordinates.of("winlog_aaaa"));
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
}
