package com.ruoyi.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.elasticsearch.domain.model.AttCkFeature;

import java.util.List;


/**
 * @author Jayden cxp
 * date 2022-03-09
 */
public interface IAttCkFeatureService extends IService<AttCkFeature> {

    /**
     * 查询规则列表
     * @param feature
     * @return
     */
    List<AttCkFeature> selectAttCkFeatureList(AttCkFeature feature);
}
