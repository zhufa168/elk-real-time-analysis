package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 【请填写功能名称】视图对象 sys_datasource
 *
 * @author ruoyi
 * @date 2022-03-17
 */
@Data
@ApiModel("【请填写功能名称】视图对象")
@ExcelIgnoreUnannotated
public class SysDatasourceVo {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ExcelProperty(value = "主键")
	@ApiModelProperty("主键")
	private Long datasourceId;

    /**
     * 别名
     */
	@ExcelProperty(value = "别名")
	@ApiModelProperty("别名")
	private String name;

    /**
     * jdbcurl
     */
	@ExcelProperty(value = "jdbcurl")
	@ApiModelProperty("jdbcurl")
	private String url;

    /**
     * 用户名
     */
	@ExcelProperty(value = "用户名")
	@ApiModelProperty("用户名")
	private String username;

    /**
     * 密码
     */
	@ExcelProperty(value = "密码")
	@ApiModelProperty("密码")
	private String password;

    /**
     * 数据库类型
     */
	@ExcelProperty(value = "数据库类型")
	@ApiModelProperty("数据库类型")
	private String dsType;

    /**
     * 配置类型
     */
	@ExcelProperty(value = "配置类型")
	@ApiModelProperty("配置类型")
	private String confType;

    /**
     * 数据库名称
     */
	@ExcelProperty(value = "数据库名称")
	@ApiModelProperty("数据库名称")
	private String dsName;

    /**
     * 实例
     */
	@ExcelProperty(value = "实例")
	@ApiModelProperty("实例")
	private String instance;

    /**
     * 端口
     */
	@ExcelProperty(value = "端口")
	@ApiModelProperty("端口")
	private Long post;

    /**
     * 主机
     */
	@ExcelProperty(value = "主机")
	@ApiModelProperty("主机")
	private String host;

    /**
     * 部门状态（0正常 1停用）
     */
	@ExcelProperty(value = "部门状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
	@ApiModelProperty("部门状态（0正常 1停用）")
	private String status;


}
