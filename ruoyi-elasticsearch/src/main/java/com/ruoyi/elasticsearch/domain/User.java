package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class User {
    private String identifier;
    private String domain;
    private String name;
    private String type;
}
