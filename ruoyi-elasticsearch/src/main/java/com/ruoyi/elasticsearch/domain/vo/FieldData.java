package com.ruoyi.elasticsearch.domain.vo;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-03-10
 */
@Data
public class FieldData {

    /**
     * 字段mingc
     */
    private String fieldName;

    /**
     * 值
     */
    private String value;

    /**
     * 条件
     */
    private String operator;
}
