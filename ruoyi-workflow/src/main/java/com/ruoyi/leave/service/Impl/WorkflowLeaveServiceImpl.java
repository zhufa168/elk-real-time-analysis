package com.ruoyi.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.mapper.WorkflowLeaveMapper;
import com.ruoyi.leave.service.IWorkflowLeaveService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请假Service业务层处理
 *
 * @author Jayden cxp
 * date 2021-09-11
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WorkflowLeaveServiceImpl extends ServicePlusImpl<WorkflowLeaveMapper, WorkflowLeave,WorkflowLeave> implements IWorkflowLeaveService {

    private final ProcessRuntime processRuntime;

    private final ISysUserService sysUserService;

    private final TaskService taskService;

    private LambdaQueryWrapper<WorkflowLeave> buildQueryWrapper(WorkflowLeave workflowLeave){
        LambdaQueryWrapper<WorkflowLeave> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getType()),WorkflowLeave::getType,workflowLeave.getType());
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getTitle()),WorkflowLeave::getTitle,workflowLeave.getTitle());
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getReason()),WorkflowLeave::getReason,workflowLeave.getReason());
        lqw.eq(workflowLeave.getLeaveStartTime()!=null,WorkflowLeave::getLeaveStartTime,workflowLeave.getLeaveStartTime());
        lqw.eq(workflowLeave.getLeaveEndTime()!=null,WorkflowLeave::getLeaveEndTime,workflowLeave.getLeaveEndTime());
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getInstanceId()),WorkflowLeave::getInstanceId,workflowLeave.getInstanceId());
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getState()),WorkflowLeave::getState,workflowLeave.getState());
        lqw.eq(StringUtils.isNotBlank(workflowLeave.getCreateBy()),WorkflowLeave::getCreateBy,workflowLeave.getCreateBy());
        return lqw;
    }

    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    @Override
    public WorkflowLeave selectWorkflowLeaveById(String id) {
        return getById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假
     */
    @Override
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave) {
        return baseMapper.selectWorkflowLeaveListByWorkflowLeaveAndDeptId(workflowLeave, SecurityUtils.getLoginUser().getDeptId());
    }

    @Override
    public TableDataInfo<WorkflowLeave> selectPageWorkflowLeaveList(WorkflowLeave workflowLeave) {
        return PageUtils.buildDataInfo(baseMapper.selectWorkflowLeaveListByWorkflowLeaveAndDeptId(PageUtils.buildPage(),workflowLeave,SecurityUtils.getLoginUser().getDeptId()));
    }

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    @Override
    public List<WorkflowLeave> selectWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave) {
        List<WorkflowLeave> workflowLeaves = baseMapper.selectList(buildQueryWrapper(workflowLeave));
        List<String> collect = workflowLeaves.parallelStream().map(WorkflowLeave::getInstanceId).collect(Collectors.toList());
        if (collect!=null&&!collect.isEmpty()){
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowLeaves.forEach(
                wl ->{
                    Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                    if (task != null){
                        wl.setTaskName(task.getName());
                    }
                }
            );
        }
        return workflowLeaves;
    }

    @Override
    public TableDataInfo<WorkflowLeave> selectPageWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave) {
        Page<WorkflowLeave> workflowLeavePage = baseMapper.selectPage(PageUtils.buildPage(),buildQueryWrapper(workflowLeave));
        List<WorkflowLeave> workflowLeaves = workflowLeavePage.getRecords();
        List<String> collect = workflowLeaves.parallelStream().map(WorkflowLeave::getInstanceId).collect(Collectors.toList());
        if (collect!=null&&!collect.isEmpty()){
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowLeaves.forEach(
                wl -> {
                    Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                    if (task != null){
                        wl.setTaskName(task.getName());
                    }
                }
            );
        }
        workflowLeavePage.setRecords(workflowLeaves);
        return PageUtils.buildDataInfo(workflowLeavePage);
    }

    /**
     * 新增请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowLeave(WorkflowLeave workflowLeave) {
        workflowLeave.setState("0");
        int count = baseMapper.insert(workflowLeave);
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se",SecurityUtils.getDeptId()),",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
            .start()
            .withProcessDefinitionKey("leave")
            .withName(workflowLeave.getTitle())
            .withBusinessKey(String.valueOf(workflowLeave.getId()))
            .withVariable("deptLeader",join)
            .build()
        );
        baseMapper.update(null,Wrappers.<WorkflowLeave>lambdaUpdate().eq(WorkflowLeave::getId,workflowLeave.getId()).set(WorkflowLeave::getTitle,workflowLeave.getTitle()));
        return count;
    }

    @Override
    public int updateWorkflowLeave(WorkflowLeave workflowLeave) {
        return baseMapper.updateById(workflowLeave);
    }

    @Override
    public int deleteWorkflowLeaveByIds(Long[] ids) {
        return baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public int deleteWorkflowLeaveById(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public WorkflowLeave selectWorkflowLeaveByInstanceId(String instanceId) {
        return baseMapper.selectOne(Wrappers.<WorkflowLeave>lambdaQuery().eq(WorkflowLeave::getInstanceId,instanceId));
    }
}
