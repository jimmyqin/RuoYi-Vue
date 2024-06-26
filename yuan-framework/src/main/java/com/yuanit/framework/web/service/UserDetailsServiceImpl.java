package com.yuanit.framework.web.service;

import com.yuanit.common.core.domain.entity.SysUser;
import com.yuanit.common.core.domain.model.LoginUser;
import com.yuanit.common.utils.MessageUtils;
import com.yuanit.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户验证处理
 *
 * @author 
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ISysUserService userService;
    private final SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        return Optional.ofNullable(user)
                .map(this::createLoginUser)
                .orElseThrow(() -> new UsernameNotFoundException(MessageUtils.message("user.not.exists")));

    }

    private UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
