package com.ruoyi.elasticsearch.service;

import java.io.IOException;
import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
public interface IElasticService {

    void createIndex();

    void deleteIndex(String index);

    List queryIndex(String name) throws IOException;

    void addEsContent();

    List queryList();

    boolean queryIndexsDataList(String index,String startTime,String endTime);

    void createAlert(List<String> alertList) throws IOException;
}
