package com.ruoyi.elasticsearch.mapper;

import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.elasticsearch.domain.model.TAlertData;

import java.util.List;


public interface AttckAlertDataMapper extends BaseMapperPlus<TAlertData> {

    int insertAlartData(List<TAlertData> alertDataList);

}
