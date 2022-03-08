package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.MySimpleGrantedAuthority;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ISysUserService userService;

    private final SysPermissionService permissionService;

    private final ISysPostService sysPostService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new UserException("user.password.delete", username);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        Set<String> postCode = sysPostService.selectPostCodeByUserId(user.getUserId());
        postCode = postCode.parallelStream().map( s ->  "GROUP_" + s).collect(Collectors.toSet());
        postCode.add("ROLE_ACTIVITI_USER");
        List<MySimpleGrantedAuthority> collect = postCode.stream().map(MySimpleGrantedAuthority::new).collect(Collectors.toList());
        return new LoginUser()
            .setUserId(user.getUserId())
            .setDeptId(user.getDeptId())
            .setUsername(user.getUserName())
            .setPassword(user.getPassword())
            .setMenuPermissions(permissionService.getMenuPermission(user))
            .setRolePermissions(permissionService.getRolePermission(user))
            .setAuthorities(collect);
    }
}
