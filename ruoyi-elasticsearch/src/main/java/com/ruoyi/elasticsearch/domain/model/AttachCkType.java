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
@TableName("att_ck_type")
@ExcelIgnoreUnannotated
@ApiModel("二级分类表")
public class AttachCkType extends BaseEntity {

    /**
     * 二级id
     */
    @ApiModelProperty(value = "二级id")
    @ExcelProperty(value = "二级id")
    @TableId(value = "ck_id")
    private long ckId;

    /**
     * 头部id
     */
    @ApiModelProperty(value = "头部id")
    @ExcelProperty(value = "头部id")
    private Long attId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String ckName;

    /**
     * 英文名称
     * */
    @ApiModelProperty(value = "英文名称")
    @ExcelProperty(value = "英文名称")
    private String ckNameEn;

    /**
     * 编码
     * */
    @ApiModelProperty(value = "编码")
    @ExcelProperty(value = "编码")
    private String ckCode;

    /**
     * 执行状态（1正常 0失败）
     * */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态 1=正常,0=失败")
    private String status;

}
