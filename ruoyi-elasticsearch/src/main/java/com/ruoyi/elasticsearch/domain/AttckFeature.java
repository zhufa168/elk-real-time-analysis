package com.ruoyi.elasticsearch.domain;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @author fansy
 *
 * 表 att_ck_feature
 */
public class AttckFeature extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 特征级别 */
    private String ckType;

    /** 编码 */
    private Long code;

    /** 状态（1正常 0失败） */
    private String status;
    private String rule;

    /** 备注 */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
