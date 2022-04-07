package com.ruoyi.elasticsearch.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.elasticsearch.domain.model.AttachHeader;
import com.ruoyi.elasticsearch.service.IAttachHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-04-07
 */
@Validated
@Api(value = "attck标题", tags = {"attck标题管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/search/attachHeader")
public class AttachHeaderController extends BaseController {

    private final IAttachHeaderService attachHeaderService;

    @ApiOperation("attck列表头部")
    @GetMapping("/list")
    public AjaxResult<List<AttachHeader>> List(AttachHeader attachHeader){
        List<AttachHeader> attachHeaders = attachHeaderService.queryList(attachHeader);
        return AjaxResult.success(attachHeaders);
    }
}
