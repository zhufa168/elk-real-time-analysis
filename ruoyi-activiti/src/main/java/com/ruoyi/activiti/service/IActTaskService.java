package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.dto.ActTaskDTO;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
public interface IActTaskService {

	/**
	 * 获取任务
	 * @return
	 */
	TableDataInfo<ActTaskDTO> selectPageProcessDefinitionList();
}
