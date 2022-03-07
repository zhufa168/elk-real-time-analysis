package com.ruoyi.activiti.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("act_re_deployment")
public class ActReDeploymentVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 部署id
	 */
	@TableId(value = "ID_")
	private String id;

	/**
	 * 部署时间
	 */
	@TableField(value = "DEPLOY_TIME_")
	private Date deployTime;

}
