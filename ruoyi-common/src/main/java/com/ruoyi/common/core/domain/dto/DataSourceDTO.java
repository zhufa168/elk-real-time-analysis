package com.ruoyi.common.core.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Jayden cxp
 * date 2022-03-16
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DataSourceDTO implements Serializable {

    /**
     * 连接池名称
     */
    private String poolName;

    /**
     * 数据库驱动
     */
    private String driverClassName;

    /**
     * 数据库链接
     */
    private String url;

    /**
     * 数据库名称
     */
    private String username;

    /**
     * 数控密码
     */
    private String password;

}
