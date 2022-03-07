package com.ruoyi.leave.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.service.IWorkflowLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请假Controller
 *
 * @author Jayden cxp
 * date 2021-09-11
 */
@RestController
@RequestMapping("/workflow/leave")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WorkflowLeaveController extends BaseController {

    private final IWorkflowLeaveService workflowLeaveService;

    /**
     * 查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkflowLeave workflowLeave) {
        return workflowLeaveService.selectPageWorkflowLeaveAndTaskNameList(workflowLeave);
    }
    /**
     * 查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowLeave workflowLeave) {
        return workflowLeaveService.selectPageWorkflowLeaveList(workflowLeave);
    }

    /**
     * 导出请假列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(WorkflowLeave workflowLeave, HttpServletResponse response) {
        List<WorkflowLeave> list = workflowLeaveService.selectWorkflowLeaveList(workflowLeave);
        ExcelUtil.exportExcel(list,"请假列表",WorkflowLeave.class,response);
    }

    /**
     * 获取请假详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowLeaveService.selectWorkflowLeaveById(id));
    }   /**
     * 获取请假详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:query')")
    @GetMapping(value = "ByInstanceId/{instanceId}")
    public AjaxResult getInfoByInstanceId(@PathVariable("instanceId") String instanceId) {
        return AjaxResult.success(workflowLeaveService.selectWorkflowLeaveByInstanceId(instanceId));
    }

    /**
     * 新增请假
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:add')")
    @Log(title = "请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkflowLeave workflowLeave) {
        return toAjax(workflowLeaveService.insertWorkflowLeave(workflowLeave));
    }

    /**
     * 修改请假
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:edit')")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkflowLeave workflowLeave) {
        return toAjax(workflowLeaveService.insertWorkflowLeave(workflowLeave));
    }

    /**
     * 删除请假
     */
    @PreAuthorize("@ss.hasPermi('workflow:leave:remove')")
    @Log(title = "请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workflowLeaveService.deleteWorkflowLeaveByIds(ids));
    }
}