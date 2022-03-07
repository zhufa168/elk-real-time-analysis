package com.ruoyi.activiti.domain.dto;

import lombok.Data;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.Serializable;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Data
public class DefinitionIdDTO implements Serializable {

	private String deploymentID;

	private String resourceName;

	public DefinitionIdDTO(ProcessDefinition processDefinition) {
		this.deploymentID = processDefinition.getDeploymentId();
		this.resourceName = processDefinition.getResourceName();
	}
}
