package com.ruoyi.elasticsearch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.elasticsearch.domain.model.AttCkFeature;
import com.ruoyi.elasticsearch.mapper.AttCkFeatureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-03-09
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AttCkFeatureServiceImpl extends ServicePlusImpl<AttCkFeatureMapper, AttCkFeature,AttCkFeature> implements IAttCkFeatureService{

    /**
     * 查询规则列表
     * @param feature
     * @return
     */
    @Override
    public List<AttCkFeature> selectAttCkFeatureList(AttCkFeature feature) {
        return baseMapper.selectList(new LambdaQueryWrapper<AttCkFeature>().eq(AttCkFeature::getStatus,1));
    }
}
