package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 岗位信息 服务层处理
 *
 * @author Lion Li
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysPostServiceImpl extends ServicePlusImpl<SysPostMapper, SysPost, SysPost> implements ISysPostService {

    private final SysUserPostMapper userPostMapper;

    @Override
    public TableDataInfo<SysPost> selectPagePostList(SysPost post, PageQuery pageQuery) {
        LambdaQueryWrapper<SysPost> lqw = new LambdaQueryWrapper<SysPost>()
                .like(StringUtils.isNotBlank(post.getPostCode()), SysPost::getPostCode, post.getPostCode())
                .eq(StringUtils.isNotBlank(post.getStatus()), SysPost::getStatus, post.getStatus())
                .like(StringUtils.isNotBlank(post.getPostName()), SysPost::getPostName, post.getPostName());
        Page<SysPost> page = page(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return list(new LambdaQueryWrapper<SysPost>()
                .like(StringUtils.isNotBlank(post.getPostCode()), SysPost::getPostCode, post.getPostCode())
                .eq(StringUtils.isNotBlank(post.getStatus()), SysPost::getStatus, post.getStatus())
                .like(StringUtils.isNotBlank(post.getPostName()), SysPost::getPostName, post.getPostName()));
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return list();
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        return getById(postId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 根据用户ID获取PostCode
     *
     * @param userId 用户ID
     * @return 选中岗位PostCode列表
     */
    @Override
    public Set<String> selectPostCodeByUserId(Long userId) {
        Set<Long> pIds = userPostMapper
            .selectList(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getUserId,userId))
            .stream().map(SysUserPost::getPostId).collect(Collectors.toSet());
        return baseMapper.selectBatchIds(pIds).stream().map(SysPost::getPostCode).collect(Collectors.toSet());
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        long count = count(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostName, post.getPostName())
                .ne(SysPost::getPostId, postId));
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        long count = count(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostCode, post.getPostCode())
                .ne(SysPost::getPostId, postId));
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public long countUserPostById(Long postId) {
        return userPostMapper.selectCount(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getPostId, postId));
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int deletePostById(Long postId) {
        return baseMapper.deleteById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return baseMapper.deleteBatchIds(Arrays.asList(postIds));
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post) {
        return baseMapper.insert(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post) {
        return baseMapper.updateById(post);
    }
}
