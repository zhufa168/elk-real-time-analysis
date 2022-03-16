package com.ruoyi.elasticsearch.service;

import com.ruoyi.elasticsearch.domain.model.TAlertData;

import java.util.List;

public interface IAlertDataService {

    int insertAll(List<TAlertData> list);

}
