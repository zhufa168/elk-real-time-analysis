package com.ruoyi.elasticsearch.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.elasticsearch.service.IAssetsService;
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
 * date 2022-04-06
 */
@Validated
@Api(value = "资产库", tags = {"资产管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/search/assets")
public class AssetsController extends BaseController {

    private final IAssetsService assetsService;

    /**
     * 查询资产列表
     * @param pageQuery
     * @return
     */
    @ApiOperation("查询资产列表")
    @GetMapping("list")
    public AjaxResult<List> list(PageQuery pageQuery){
        List list = assetsService.searchAllAssets();
        return AjaxResult.success(list);
    }
}
