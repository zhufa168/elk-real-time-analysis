package com.ruoyi.elasticsearch.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.elasticsearch.service.IElasticService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jayden cxp
 * date 2022-02-23
 */
@Api(value = "测试", tags = {"测试管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class DemoController {
    private final IElasticService elasticService;

    @GetMapping("/createIndex")
    public AjaxResult<Map<String, Object>> createIndex() {
        List list = elasticService.queryList();
        Map<String, Object> ajax = new HashMap<>();
        return AjaxResult.success(ajax);
    }

    @GetMapping("/queryAllIndex")
    public AjaxResult<Map<String, Object>> queryAllIndex() {
        List list = elasticService.queryIndex("winlog*");
        Map<String, Object> ajax = new HashMap<>();
        return AjaxResult.success(ajax);
    }

    @GetMapping("/queryDataList")
    public  AjaxResult queryDataList(){
        List list = null; //elasticService.queryIndexsDataList();
        return AjaxResult.success(list);
    }
}
