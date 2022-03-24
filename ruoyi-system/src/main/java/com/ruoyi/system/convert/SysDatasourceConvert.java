package com.ruoyi.system.convert;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.ruoyi.system.domain.bo.SysDatasourceBo;
import com.ruoyi.system.domain.vo.SysDatasourceVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Jayden cxp
 * date 2022-03-18
 */
@Mapper
public interface SysDatasourceConvert {
    SysDatasourceConvert INSTANCE = Mappers.getMapper(SysDatasourceConvert.class);

    @Mappings({
        @Mapping(target = "name", source = "poolName"),
        @Mapping(target = "dsType", source = "driverClassName"),
        @Mapping(target = "url", source = "url"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "password", source = "password")
    })
    SysDatasourceVo convert(DataSourceProperty dataSourceProperty);

    @Mappings({
        @Mapping(target = "poolName", source = "name"),
        @Mapping(target = "driverClassName", source = "dsType"),
        @Mapping(target = "url", source = "url"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "password", source = "password")
    })
    DataSourceProperty convert(SysDatasourceBo bo);

    SysDatasourceBo convert(SysDatasourceVo vo);
}
