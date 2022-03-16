package com.ruoyi.elasticsearch.service.impl;

import com.ruoyi.elasticsearch.domain.model.TAlertData;
import com.ruoyi.elasticsearch.mapper.AttckAlertDataMapper;
import com.ruoyi.elasticsearch.service.IAlertDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("alertDataService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AlertDataServiceImpl implements IAlertDataService {

    private final AttckAlertDataMapper mapper;

    @Override
    public int insertAll(List<TAlertData> list) {
        return mapper.insertAlartData(list);
    }

}
