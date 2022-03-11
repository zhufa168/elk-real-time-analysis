package com.ruoyi.elasticsearch.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-03-10
 */
@Data
public class FeatureData {

    private FieldData data;

    private String operate;

    private List<FeatureData> list;
}
