package com.ruoyi.elasticsearch.domain;

import lombok.Data;


/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class SystemLog {
    private Registry registry;
    private Process process;
    private Agent agent;
    private Winlog winlog;
    private Log log;
    private Destination destination;
    private Dns dns;
    private Rule rule;
    private Source source;
    private Error error;
    private String message;
    private NetWork network;
    private String tags;
    private file file;
    private Ecs ecs;
    private Related related;
    private Host host;
    private Event event;
    private Fields fields;
    private User user;
    private Hash hash;
    private String uuid;
}
