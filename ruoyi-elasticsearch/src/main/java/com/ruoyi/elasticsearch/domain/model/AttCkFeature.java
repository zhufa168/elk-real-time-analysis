package com.ruoyi.elasticsearch.domain.model;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("att_ck_feature")
@ExcelIgnoreUnannotated
@ApiModel("规则表")
public class AttCkFeature extends BaseEntity {

    /**
     * ID
     * */
    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    /**
     * 特征级别，2表示att_ck_type 二级，3表示att_ck_sub_type
     * */
    @ApiModelProperty(value = "特征级别")
    @ExcelProperty(value = "特征级别")
    @TableField(value = "ck_type")
    private String type;

    /**
     * 特征级别ID
     * */
    @ApiModelProperty(value = "特征级别ID")
    @ExcelProperty(value = "特征级别ID")
    @TableField(value = "code")
    private Long typeId;

    /**
     *  状态（1正常 0失败）
     *  */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态 1=正常,0=失败")
    private String status;

    /**
     * 规则
     */
    @ApiModelProperty(value = "规则")
    @ExcelProperty(value = "规则")
    private String rule;

}
