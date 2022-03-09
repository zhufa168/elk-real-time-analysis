package com.ruoyi.elasticsearch.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.elasticsearch.domain.model.AttCkFeature;
import com.ruoyi.elasticsearch.service.IAttCkFeatureService;
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
 * date 2022-03-09
 */
@Validated
@Api(value = "规则管理", tags = {"规则管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/attck/feature")
public class AttCkFeatureController extends BaseController {

    private final IAttCkFeatureService attCkFeatureService;

    @ApiOperation("查询规则列表")
    @GetMapping("/list")
    public AjaxResult<List<AttCkFeature>> list(AttCkFeature feature){
        List<AttCkFeature> featureList = attCkFeatureService.selectAttCkFeatureList(new AttCkFeature());
        return AjaxResult.success(featureList);
    }
}
