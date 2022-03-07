package com.ruoyi.leave.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jayden cxp
 * date 2021-09-10
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("workflow_leave")
@ExcelIgnoreUnannotated
public class WorkflowLeave implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    @ExcelProperty(value = "参数主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 请假类型 */
    @ExcelProperty(value = "请假类型")
    private String type;

    /** 标题 */
    @ExcelProperty(value = "标题")
    private String title;

    /** 原因 */
    @ExcelProperty(value = "原因")
    private String reason;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColumnWidth(30)
    @DateTimeFormat(value = "yyyy-MM-dd")
    @ExcelProperty(value = "开始时间")
    private Date leaveStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColumnWidth(30)
    @DateTimeFormat(value = "yyyy-MM-dd")
    @ExcelProperty(value = "开始时间")
    private Date leaveEndTime;

    /** 实例id **/
    private String instanceId;

    /** 任务名称 **/
    private String taskName;

    /** 状态 */
    @ExcelProperty(value = "状态")
    private String state;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
