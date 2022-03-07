package com.ruoyi.activiti.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.activiti.domain.vo.ActReDeploymentVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Data
@NoArgsConstructor
public class ProcessDefinitionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String key;

	private int version;

	private String deploymentId;

	private String resourceName;

	private Date deploymentTime;

	/** 流程实例状态 1 激活 2 挂起 */
	private Integer suspendState;

	public ProcessDefinitionDTO(ProcessDefinitionEntityImpl processDefinition, ActReDeploymentVO actReDeploymentVO) {
		this.id = processDefinition.getId();
		this.name = processDefinition.getName();
		this.key = processDefinition.getKey();
		this.version = processDefinition.getVersion();
		this.deploymentId = processDefinition.getDeploymentId();
		this.resourceName = processDefinition.getResourceName();
		this.deploymentTime = actReDeploymentVO.getDeployTime();
		this.suspendState = processDefinition.getSuspensionState();
	}
}
