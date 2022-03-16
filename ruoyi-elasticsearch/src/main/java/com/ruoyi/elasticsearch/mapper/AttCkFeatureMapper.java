package com.ruoyi.elasticsearch.mapper;

import com.ruoyi.elasticsearch.domain.model.AttCkFeature;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AttCkFeatureMapper{

    List<AttCkFeature> selectAttckFeatureList();

}
