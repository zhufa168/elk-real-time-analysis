package com.ruoyi.leave.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.mybatisplus.core.BaseMapperPlus;
import com.ruoyi.leave.domain.WorkflowLeave;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2021-09-10
 */
public interface WorkflowLeaveMapper extends BaseMapperPlus<WorkflowLeave> {

    /**
     * 查询请假列表根据部门编号和WorkflowLeave
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveListByWorkflowLeaveAndDeptId(@Param("workflowLeave")WorkflowLeave workflowLeave, @Param("deptId") Long deptId);
    Page<WorkflowLeave> selectWorkflowLeaveListByWorkflowLeaveAndDeptId(Page<WorkflowLeave> page,@Param("workflowLeave")WorkflowLeave workflowLeave, @Param("deptId") Long deptId);

}
