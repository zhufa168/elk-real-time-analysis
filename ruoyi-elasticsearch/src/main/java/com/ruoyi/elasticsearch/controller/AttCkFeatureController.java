package com.ruoyi.elasticsearch.controller;

import com.ruoyi.common.core.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

   /* private final IAttCkFeatureService attCkFeatureService;

    @ApiOperation("查询规则列表")
    @GetMapping("/list")
    public AjaxResult<List<AttckFeature>> list(AttckFeature feature){
        List<AttckFeature> featureList = attCkFeatureService.selectAttCkFeatureList(new AttckFeature());
        return AjaxResult.success(featureList);
    }*/
}
