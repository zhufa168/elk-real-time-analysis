package com.ruoyi.leave.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.leave.domain.WorkflowLeave;

import java.util.List;

/**
 * 请假Service接口
 *
 * @author Jayden cxp
 * date 2021-09-10
 */
public interface IWorkflowLeaveService extends IService<WorkflowLeave> {
    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowLeave selectWorkflowLeaveById(String id);

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave);
    TableDataInfo<WorkflowLeave> selectPageWorkflowLeaveList(WorkflowLeave workflowLeave);

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave);
    TableDataInfo<WorkflowLeave> selectPageWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave);

    /**
     * 新增请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    public int insertWorkflowLeave(WorkflowLeave workflowLeave);

    /**
     * 修改请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    public int updateWorkflowLeave(WorkflowLeave workflowLeave);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowLeaveByIds(Long[] ids);

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowLeaveById(Long id);


    public WorkflowLeave selectWorkflowLeaveByInstanceId(String instanceId);
}
