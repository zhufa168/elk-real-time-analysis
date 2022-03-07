package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Dns {
    private String status;
    private String resolved_ip;
    private Question question;
    private Answers answers;
}
