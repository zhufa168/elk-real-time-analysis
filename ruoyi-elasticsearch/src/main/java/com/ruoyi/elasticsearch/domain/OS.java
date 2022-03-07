package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class OS {
    private String build;
    private String kernel;
    private String name;
    private String family;
    private String type;
    private String version;
    private String platform;
}
