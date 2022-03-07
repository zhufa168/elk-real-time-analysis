package com.ruoyi.elasticsearch.domain;

import lombok.Data;

/**
 * @author Jayden cxp
 * date 2022-02-14
 */
@Data
public class file {
    private String path;
    private String extension;
    private CodeSignature code_signature;
    private Pe pe;
    private String name;
    private String directory;
    private Hash hash;
    private Boolean archived;
    private Boolean is_executable;
}
