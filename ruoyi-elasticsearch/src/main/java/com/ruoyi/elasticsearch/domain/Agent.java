package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Agent {
    private String hostname;
    private String name;
    private String id;
    private String type;
    private String ephemeral_id;
    private String version;
}
