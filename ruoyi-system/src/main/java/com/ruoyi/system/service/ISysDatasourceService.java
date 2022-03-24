package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDatasource;
import com.ruoyi.system.domain.vo.SysDatasourceVo;
import com.ruoyi.system.domain.bo.SysDatasourceBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2022-03-17
 */
public interface ISysDatasourceService extends IServicePlus<SysDatasource, SysDatasourceVo> {
	/**
	 * 查询单个
	 * @return
	 */
	SysDatasourceVo queryById(Long datasourceId);

	/**
	 * 查询列表
	 */
    TableDataInfo<SysDatasourceVo> queryPageList(SysDatasourceBo bo, PageQuery pageQuery);

	/**
	 * 查询列表
	 */
	List<SysDatasourceVo> queryList(SysDatasourceBo bo);

	/**
	 * 根据新增业务对象插入【请填写功能名称】
	 * @param bo 【请填写功能名称】新增业务对象
	 * @return
	 */
	Boolean insertByBo(SysDatasourceBo bo);

	/**
	 * 根据编辑业务对象修改【请填写功能名称】
	 * @param bo 【请填写功能名称】编辑业务对象
	 * @return
	 */
	Boolean updateByBo(SysDatasourceBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 创建数据源链接
     * @param bo 数据源实体类
     * @return
     */
	Boolean createDataSource(SysDatasourceBo bo);


	Boolean removeDataSource(String key);


	Boolean updateDataSource(SysDatasourceBo bo);

	void loadingDataSource();
}
