package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.dto.ActivitiHighLineDTO;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
public interface IActivitiHistoryService {
	public ActivitiHighLineDTO gethighLine(String instanceId);
}
