package com.ruoyi.activiti.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Data
public class ActivitiHighLineDTO implements Serializable {
	private Set<String> highPoint;
	private Set<String> highLine;
	private Set<String> waitingToDo;
	private  Set<String>  iDo;
}
