package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Registry {
    private String hive;
    private String path;
    private String value;
    private String key;
}
