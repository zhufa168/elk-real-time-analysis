package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.ruoyi.system.convert.SysDatasourceConvert;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.SysDatasourceVo;
import com.ruoyi.system.domain.bo.SysDatasourceBo;
import com.ruoyi.system.service.ISysDatasourceService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 数据源 Controller
 *
 * @author ruoyi
 * @date 2022-03-17
 */
@Validated
@Api(value = "数据源控制器", tags = {"数据源管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/datasource")
public class SysDatasourceController extends BaseController {

    private final ISysDatasourceService iSysDatasourceService;

    private final DataSource dataSource;

    private final DefaultDataSourceCreator dataSourceCreator;

    private final DruidDataSourceCreator druidDataSourceCreator;

    private final HikariDataSourceCreator hikariDataSourceCreator;

    /**
     * 查询数据源列表
     */
    @ApiOperation("查询数据源列表")
    @PreAuthorize("@ss.hasPermi('system:datasource:list')")
    @GetMapping("/list")
    public TableDataInfo<SysDatasourceVo> list(@Validated(QueryGroup.class) SysDatasourceBo bo, PageQuery pageQuery) {
//        List<SysDatasourceVo> list = new ArrayList<>();
//        TableDataInfo dataSourceList = iSysDatasourceService.queryPageList(bo, pageQuery);
//        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
//        Map<String, DataSource> dataSources = ds.getDataSources();
//        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
//            SysDatasourceVo vo = new SysDatasourceVo();
//            ItemDataSource source = (ItemDataSource)entry.getValue();
//            DruidDataSource druidDataSource = (DruidDataSource) source.getRealDataSource();
//            vo.setName(entry.getKey());
//            vo.setUrl(druidDataSource.getUrl());
//            vo.setUsername(druidDataSource.getUsername());
//            vo.setPassword(druidDataSource.getPassword());
//            vo.setDsType(druidDataSource.getDriverClassName());
//            vo.setConfType(druidDataSource.getDbType());
//            list.add(vo);
//        }
        return iSysDatasourceService.queryPageList(bo, pageQuery);
//        return iSysDatasourceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出数据源列表
     */
    @ApiOperation("导出数据源列表")
    @PreAuthorize("@ss.hasPermi('system:datasource:export')")
    @Log(title = "数据源导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated SysDatasourceBo bo, HttpServletResponse response) {
        List<SysDatasourceVo> list = iSysDatasourceService.queryList(bo);
        ExcelUtil.exportExcel(list, "数据源", SysDatasourceVo.class, response);
    }

    /**
     * 获取数据源详细信息
     */
    @ApiOperation("获取数据源详细信息")
    @PreAuthorize("@ss.hasPermi('system:datasource:query')")
    @GetMapping("/{datasourceId}")
    public AjaxResult<SysDatasourceVo> getInfo(@ApiParam("主键")
                                               @NotNull(message = "主键不能为空")
                                               @PathVariable("datasourceId") Long datasourceId) {
        return AjaxResult.success(iSysDatasourceService.queryById(datasourceId));
    }

    /**
     * 新增数据源
     */
    @ApiOperation("新增数据源")
    @PreAuthorize("@ss.hasPermi('system:datasource:add')")
    @Log(title = "数据源新增", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody SysDatasourceBo bo) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        if (ds.getDataSources().keySet().contains(bo.getName())) {
            return error("数据源已经存在！");
        }
        iSysDatasourceService.createDataSource(bo);
//        DataSourceProperty dataSourceProperty = SysDatasourceConvert.INSTANCE.convert(bo);
//        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
//        ds.addDataSource(bo.getName(),dataSource);
        return toAjax(iSysDatasourceService.insertByBo(bo));
//        return toAjax(iSysDatasourceService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改数据源
     */
    @ApiOperation("修改数据源")
    @PreAuthorize("@ss.hasPermi('system:datasource:edit')")
    @Log(title = "数据源修改", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody SysDatasourceBo bo) {
        iSysDatasourceService.updateDataSource(bo);
        return toAjax(iSysDatasourceService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除数据源
     */
    @ApiOperation("删除数据源")
    @PreAuthorize("@ss.hasPermi('system:datasource:remove')")
    @Log(title = "数据源删除" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{datasourceIds}")
    public AjaxResult<Void> remove(@ApiParam("主键串")
                                   @NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] datasourceIds) {
        for (Long datasourceId : datasourceIds){
            SysDatasourceVo vo = iSysDatasourceService.queryById(datasourceId);
            iSysDatasourceService.removeDataSource(vo.getName());
        }
        return toAjax(iSysDatasourceService.deleteWithValidByIds(Arrays.asList(datasourceIds), true) ? 1 : 0);
    }
}
