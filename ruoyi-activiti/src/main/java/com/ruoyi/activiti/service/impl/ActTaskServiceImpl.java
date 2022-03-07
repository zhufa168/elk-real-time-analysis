package com.ruoyi.activiti.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.activiti.domain.dto.ActTaskDTO;
import com.ruoyi.activiti.service.IActTaskService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jayden cxp
 * date 2021-08-28
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ActTaskServiceImpl implements IActTaskService {

	private final RepositoryService repositoryService;

	private final TaskRuntime taskRuntime;

	private final RuntimeService runtimeService;

	@Override
	public TableDataInfo<ActTaskDTO> selectPageProcessDefinitionList() {
		Page<ActTaskDTO> page = new Page<ActTaskDTO>();
		org.activiti.api.runtime.shared.query.Page<Task> pageTasks = taskRuntime.tasks(Pageable.of(((int)PageUtils.buildPage().getCurrent() - 1) * (int)PageUtils.buildPage().getSize(), (int)PageUtils.buildPage().getSize()));
		List<Task> tasks = pageTasks.getContent();
		int totalItems = pageTasks.getTotalItems();
		page.setTotal(totalItems);
		if (totalItems != 0) {
			Set<String> processInstanceIdIds = tasks.parallelStream().map(Task::getProcessInstanceId).collect(Collectors.toSet());
			List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIdIds).list();
			List<ActTaskDTO> actTaskDTOS = tasks.stream()
				.map(t -> new ActTaskDTO(t, processInstanceList.parallelStream().filter(pi -> t.getProcessInstanceId().equals(pi.getId())).findAny().get()))
				.collect(Collectors.toList());
			page.setRecords(actTaskDTOS);
		}
		return PageUtils.buildDataInfo(page);
	}
}
