package com.ruoyi.elasticsearch.mapper;

import com.ruoyi.elasticsearch.domain.AttckFeature;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AttckFeatureMapper {

    List<AttckFeature> selectAttckFeatureList();

}
