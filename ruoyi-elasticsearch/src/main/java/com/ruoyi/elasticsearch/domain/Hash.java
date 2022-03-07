package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class Hash {
    private String sha1;
    private String imphash;
    private String sha256;
    private String md5;
}
