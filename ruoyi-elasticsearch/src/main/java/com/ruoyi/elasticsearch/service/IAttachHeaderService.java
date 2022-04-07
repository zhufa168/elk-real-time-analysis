package com.ruoyi.elasticsearch.service;

import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.elasticsearch.domain.model.AttachHeader;

import java.util.List;

/**
 * @author Jayden cxp
 * date 2022-04-07
 */
public interface IAttachHeaderService extends IServicePlus<AttachHeader, AttachHeader> {

    /**
     * 查询attck 列表头
     * @param header
     * @return
     */
    List<AttachHeader> queryList(AttachHeader header);
}
