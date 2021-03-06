package com.ruoyi.elasticsearch.domain.model;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Jayden cxp
 * date 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("att_header")
@ExcelIgnoreUnannotated
@ApiModel("att头部")
public class AttachHeader extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键")
    @ExcelProperty(value = "主键")
    @TableId(value = "att_id")
    private Long attId;

    /**
     * 头部名称
     */
    @ApiModelProperty(value = "头部名称")
    @ExcelProperty(value = "头部名称")
    private String attName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    @ExcelProperty(value = "英文名称")
    private String attNameEn;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    @ExcelProperty(value = "编码")
    private String attCode;

    /**
     * 状态（1正常 0失败）
     */
    @ApiModelProperty(value = "状态（1正常 0失败）")
    @ExcelProperty(value = "状态（1正常 0失败）")
    private String status;

}
