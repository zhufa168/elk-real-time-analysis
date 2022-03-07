package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.service.IActTaskService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TaskController extends BaseController {

	private final IActTaskService actTaskService;

	@GetMapping(value = "/list")
	public TableDataInfo getTasks() {
		return actTaskService.selectPageProcessDefinitionList();
	}
}
