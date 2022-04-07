package com.ruoyi.elasticsearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.elasticsearch.domain.model.AttachHeader;
import com.ruoyi.elasticsearch.mapper.AttachHeaderMapper;
import com.ruoyi.elasticsearch.service.IAttachHeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Jayden cxp
 * date 2022-04-07
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class AttachHeaderServiceImpl extends ServicePlusImpl<AttachHeaderMapper, AttachHeader, AttachHeader> implements IAttachHeaderService {


    @Override
    public List<AttachHeader> queryList(AttachHeader header) {
        LambdaQueryWrapper<AttachHeader> lqw = buildQueryWrapper(header);
        return this.baseMapper.selectList(lqw);
    }

    private LambdaQueryWrapper<AttachHeader> buildQueryWrapper(AttachHeader header){
        Map<String, Object> params = header.getParams();
        LambdaQueryWrapper<AttachHeader> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(header.getAttName()),AttachHeader::getAttName, header.getAttName());
        lqw.like(StringUtils.isNotBlank(header.getAttNameEn()),AttachHeader::getAttNameEn, header.getAttNameEn());
        lqw.eq(StringUtils.isNotBlank(header.getAttCode()), AttachHeader::getAttCode, header.getAttCode());
        lqw.eq(StringUtils.isNotBlank(header.getStatus()), AttachHeader::getStatus, header.getStatus());
        return lqw;
    }
}
