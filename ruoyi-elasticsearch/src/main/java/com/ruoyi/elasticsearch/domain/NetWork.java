package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class NetWork {
    private String community_id;
    private String protocol;
    private String transport;
    private String type;
    private String direction;
}
