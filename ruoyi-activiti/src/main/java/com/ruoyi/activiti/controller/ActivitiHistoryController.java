package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.dto.ActivitiHighLineDTO;
import com.ruoyi.activiti.service.IActivitiHistoryService;
import com.ruoyi.common.core.domain.AjaxResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/activitiHistory")
public class ActivitiHistoryController {

	private final IActivitiHistoryService activitiHistoryService;

	//流程图高亮
	@GetMapping("/gethighLine")
	public AjaxResult gethighLine(@RequestParam("instanceId") String instanceId) {

		ActivitiHighLineDTO activitiHighLineDTO = activitiHistoryService.gethighLine(instanceId);
		return AjaxResult.success(activitiHighLineDTO);


	}
}
