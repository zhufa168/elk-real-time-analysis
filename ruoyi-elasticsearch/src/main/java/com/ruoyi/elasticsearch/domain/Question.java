package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Question {
    private String registered_domain;
    private String top_level_domain;
    private String name;
    private String subdomain;
}
