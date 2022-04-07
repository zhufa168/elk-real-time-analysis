package com.ruoyi.elasticsearch.service.impl;

import com.ruoyi.elasticsearch.service.IAssetsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jayden cxp
 * date 2022-04-06
 */
@Slf4j
@Service("assetsService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AssetsServiceImpl implements IAssetsService {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    private final RestHighLevelClient restHighLevelClient;

    /**
     * 查询所有的资产
     * @return
     */
    @Override
    public List searchAllAssets() {
        return getAllWinLogIndices();
    }



    @SneakyThrows
    private List getAllWinLogIndices(){
        GetIndexRequest getIndexRequest = new GetIndexRequest("winlog*");
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        Map<String, List<AliasMetadata>> map = getIndexResponse.getAliases();
        Set<String> keySet = map.keySet();
        Set<String> result = new HashSet<>();
        for (String s : keySet) {
            if (s.split("_").length==3){
                result.add(s.split("_")[2]);
            }
        }
        return Arrays.asList(result.toArray());
    }
}
