package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 sys_datasource
 *
 * @author ruoyi
 * @date 2022-03-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("【请填写功能名称】业务对象")
public class SysDatasourceBo extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private Long datasourceId;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名", required = true)
    @NotBlank(message = "别名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * jdbcurl
     */
    @ApiModelProperty(value = "jdbcurl", required = true)
    @NotBlank(message = "jdbcurl不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String password;

    /**
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型", required = true)
    @NotBlank(message = "数据库类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dsType;

    /**
     * 配置类型
     */
    @ApiModelProperty(value = "配置类型", required = true)
    private String confType;

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", required = true)
    private String dsName;

    /**
     * 实例
     */
    @ApiModelProperty(value = "实例", required = true)
    private String instance;

    /**
     * 端口
     */
    @ApiModelProperty(value = "端口", required = true)
    private Long post;

    /**
     * 主机
     */
    @ApiModelProperty(value = "主机", required = true)
    private String host;

    /**
     * 部门状态（0正常 1停用）
     */
    @ApiModelProperty(value = "部门状态（0正常 1停用）", required = true)
    private String status;


}
