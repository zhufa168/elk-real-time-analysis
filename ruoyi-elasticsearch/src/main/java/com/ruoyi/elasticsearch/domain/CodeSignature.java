package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class CodeSignature {
    private Boolean valid;
    private String subject_name;
    private Boolean signed;
    private String status;
}
