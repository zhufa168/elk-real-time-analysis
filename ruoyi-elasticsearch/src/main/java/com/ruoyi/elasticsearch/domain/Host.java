package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Host {
    private String hostname;
    private OS os;
    private String ip;
    private String name;
    private String id;
    private String mac;
    private String architecture;
}
