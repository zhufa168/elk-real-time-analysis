package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Parent {
    private String args;
    private String name;
    private Integer pid;
    private String entity_id;
    private String command_line;
    private String executable;
}
