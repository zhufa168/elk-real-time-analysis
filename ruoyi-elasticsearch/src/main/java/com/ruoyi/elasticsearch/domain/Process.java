package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Process {
    private String args;
    private Parent parent;
    private Pe pe;
    private String name;
    private Integer pid;
    private String working_directory;
    private String entity_id;
    private String executable;
    private Thread thread;
    private Hash hash;
}
