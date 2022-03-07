package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Winlog {
    private String computer_name;
    private Process process;
    private String channel;
    private EventData event_data;
    private String opcode;
    private Integer version;
    private Integer record_id;
    private String task;
    private String event_id;
    private String provider_guid;
    private String api;
    private String provider_name;
    private User user;
}
