package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.convert.SysDatasourceConvert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.SysDatasourceBo;
import com.ruoyi.system.domain.vo.SysDatasourceVo;
import com.ruoyi.system.domain.SysDatasource;
import com.ruoyi.system.mapper.SysDatasourceMapper;
import com.ruoyi.system.service.ISysDatasourceService;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-03-17
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class SysDatasourceServiceImpl extends ServicePlusImpl<SysDatasourceMapper, SysDatasource, SysDatasourceVo> implements ISysDatasourceService {

    private final DynamicRoutingDataSource DynamicRoutingDataSource;

    private final DefaultDataSourceCreator dataSourceCreator;

    @Override
    public SysDatasourceVo queryById(Long datasourceId){
        return getVoById(datasourceId);
    }

    @Override
    public TableDataInfo<SysDatasourceVo> queryPageList(SysDatasourceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDatasource> lqw = buildQueryWrapper(bo);
        Page<SysDatasourceVo> result = pageVo(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<SysDatasourceVo> queryList(SysDatasourceBo bo) {
        LambdaQueryWrapper<SysDatasource> lqw = buildQueryWrapper(bo);
        return listVo(lqw);
    }

    private LambdaQueryWrapper<SysDatasource> buildQueryWrapper(SysDatasourceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysDatasource> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), SysDatasource::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), SysDatasource::getUrl, bo.getUrl());
        lqw.like(StringUtils.isNotBlank(bo.getUsername()), SysDatasource::getUsername, bo.getUsername());
        lqw.eq(StringUtils.isNotBlank(bo.getPassword()), SysDatasource::getPassword, bo.getPassword());
        lqw.eq(StringUtils.isNotBlank(bo.getDsType()), SysDatasource::getDsType, bo.getDsType());
        lqw.eq(StringUtils.isNotBlank(bo.getConfType()), SysDatasource::getConfType, bo.getConfType());
        lqw.like(StringUtils.isNotBlank(bo.getDsName()), SysDatasource::getDsName, bo.getDsName());
        lqw.eq(StringUtils.isNotBlank(bo.getInstance()), SysDatasource::getInstance, bo.getInstance());
        lqw.eq(bo.getPost() != null, SysDatasource::getPost, bo.getPost());
        lqw.eq(StringUtils.isNotBlank(bo.getHost()), SysDatasource::getHost, bo.getHost());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDatasource::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(SysDatasourceBo bo) {
        SysDatasource add = BeanUtil.toBean(bo, SysDatasource.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setDatasourceId(add.getDatasourceId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(SysDatasourceBo bo) {
        SysDatasource update = BeanUtil.toBean(bo, SysDatasource.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(SysDatasource entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public Boolean createDataSource(SysDatasourceBo bo){
        if(bo == null){
            return false;
        }
        DataSourceProperty dataSourceProperty = SysDatasourceConvert.INSTANCE.convert(bo);
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        DynamicRoutingDataSource.addDataSource(bo.getName(),dataSource);
        return true;
    }

    @Override
    public Boolean removeDataSource(String key){
        if(key == null){
            return false;
        }
        DynamicRoutingDataSource.removeDataSource(key);
        return true;
    }

    @Override
    public Boolean updateDataSource(SysDatasourceBo bo) {
        return removeDataSource(bo.getName()) &&  createDataSource(bo);
    }


    @Override
    public void loadingDataSource() {
        List<SysDatasourceVo> list = queryList(new SysDatasourceBo());
        for(SysDatasourceVo vo : list){
            SysDatasourceBo bo = SysDatasourceConvert.INSTANCE.convert(vo);
            createDataSource(bo);
        }
    }
}
