package com.ruoyi.elasticsearch.service;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
public interface IElasticService {

    void createIndex();

    void deleteIndex(String index);

    List queryIndex(String name);

    void addEsContent();

    List queryList();
}
