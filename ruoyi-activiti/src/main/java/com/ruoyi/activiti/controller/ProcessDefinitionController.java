package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.dto.ProcessDefinitionDTO;
import com.ruoyi.activiti.service.IProcessDefinitionService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/processDefinition")
public class ProcessDefinitionController extends BaseController {

	private final IProcessDefinitionService processDefinitionService;

	/**
	 * 获取流程定义集合
	 *
	 * @param processDefinition
	 * @return
	 */
	@GetMapping(value = "/list")
	public TableDataInfo list(ProcessDefinitionDTO processDefinition) {
		return processDefinitionService.selectPageProcessDefinitionList(processDefinition);
	}

	/**
	 *
	 * @return
	 */
	@GetMapping(value = "/getDefinitions/{instanceId}")
	public AjaxResult getDefinitionsByInstanceId(@PathVariable("instanceId") String instanceId){
		return AjaxResult.success(processDefinitionService.getDefinitionsByInstanceId(instanceId));
	}

	/**
	 * 删除流程定义
	 *
	 * @param deploymentId
	 * @return
	 */
	@Log(title = "流程定义管理", businessType = BusinessType.DELETE)
	@DeleteMapping(value = "/remove/{deploymentId}")
	public AjaxResult delDefinition(@PathVariable("deploymentId") String deploymentId) {
		return toAjax(processDefinitionService.deleteProcessDefinitionById(deploymentId));
	}

	/**
	 * 上传并部署流程定义
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Log(title = "流程定义管理", businessType = BusinessType.IMPORT)
	@PostMapping(value = "/uploadStreamAndDeployment")
	public AjaxResult uploadStreamAndDeployment(@RequestParam("file") MultipartFile file) throws IOException {
		processDefinitionService.uploadStreamAndDeployment(file);
		return AjaxResult.success();

	}

	/**
	 * 启动挂起流程流程定义
	 *
	 * @param processDefinition
	 * @return
	 */
	@Log(title = "流程定义管理", businessType = BusinessType.UPDATE)
	@PostMapping("/suspendOrActiveApply")
	@ResponseBody
	public AjaxResult suspendOrActiveApply(@RequestBody ProcessDefinitionDTO processDefinition) {
		processDefinitionService.suspendOrActiveApply(processDefinition.getId(), processDefinition.getSuspendState());
		return AjaxResult.success();
	}


	/**
	 * 上传流程流程定义
	 *
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	@Log(title = "流程定义管理", businessType = BusinessType.IMPORT)
	@PostMapping(value = "/upload")
	public AjaxResult upload(@RequestParam("processFile") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = processDefinitionService.upload(multipartFile);
			return AjaxResult.success("操作成功", fileName);

		}
		return AjaxResult.error("不允许上传空文件！");
	}

	/**
	 * 通过stringBPMN添加流程定义
	 *
	 * @param
	 * @return
	 */
	@PostMapping(value = "/addDeploymentByString")
	public AjaxResult addDeploymentByString(@RequestBody Map o) {
	    String stringBPMN = (String) o.get("stringBPMN");
		processDefinitionService.addDeploymentByString(stringBPMN);
		return AjaxResult.success();

	}

	/**
	 * 获取流程定义XML
	 *
	 * @param response
	 * @param deploymentId
	 * @param resourceName
	 */
	@GetMapping(value = "/getDefinitionXML")
	public void getProcessDefineXML(HttpServletResponse response,
									@RequestParam("deploymentId") String deploymentId,
									@RequestParam("resourceName") String resourceName) throws IOException {

		processDefinitionService.getProcessDefineXML(response, deploymentId, resourceName);
	}
}
