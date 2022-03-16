package com.ruoyi.elasticsearch.domain.model;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_alert_data")
@ExcelIgnoreUnannotated
@ApiModel("告警表")
public class TAlertData {

   /* *//**
     * ID
     * *//*
    @ApiModelProperty(value = "id")
    @TableId(value = "id",type= IdType.AUTO)
    private long id;*/

    /** 规则code */
    @ApiModelProperty(value = "规则code")
    private String code;

    /**
     * 事件id
     * */
    @ApiModelProperty(value = "事件id")
    private String event_id;

    /**
     * es 索引名称
     * */
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    /**
     * 信息
     * */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 执行状态（1正常 0失败）
     * */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
