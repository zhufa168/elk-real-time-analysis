package com.ruoyi.activiti.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.activiti.api.task.model.Task;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ActTaskDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String status;

	private Date createdDate;

	private String instanceName;

	private String definitionKey;

	private String businessKey;

	public ActTaskDTO(Task task, ProcessInstance processInstance) {
		this.id = task.getId();
		this.name = task.getName();
		this.status = task.getStatus().toString();
		this.createdDate = task.getCreatedDate();
		this.instanceName = processInstance.getName();
		this.definitionKey=processInstance.getProcessDefinitionKey();
		this.businessKey=processInstance.getBusinessKey();
	}

}
