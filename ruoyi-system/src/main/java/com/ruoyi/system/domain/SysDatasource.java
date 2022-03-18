package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_datasource
 *
 * @author ruoyi
 * @date 2022-03-17
 */
@Data
@Accessors(chain = true)
@TableName("sys_datasource")
public class SysDatasource extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "datasource_id")
    private Long datasourceId;
    /**
     * 别名
     */
    private String name;
    /**
     * jdbcurl
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库类型
     */
    private String dsType;
    /**
     * 配置类型
     */
    private String confType;
    /**
     * 数据库名称
     */
    private String dsName;
    /**
     * 实例
     */
    private String instance;
    /**
     * 端口
     */
    private Long post;
    /**
     * 主机
     */
    private String host;
    /**
     * 部门状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
