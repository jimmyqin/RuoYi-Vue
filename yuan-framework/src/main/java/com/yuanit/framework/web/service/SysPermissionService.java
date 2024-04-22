package com.yuanit.framework.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.yuanit.common.core.domain.entity.SysRole;
import com.yuanit.common.core.domain.entity.SysUser;
import com.yuanit.system.service.ISysMenuService;
import com.yuanit.system.service.ISysRoleService;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Component
public class SysPermissionService {

    private final ISysRoleService roleService;
    private final ISysMenuService menuService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            return Sets.newHashSet("*:*:*");
        }

        List<SysRole> roles = user.getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            Set<String> perms = Sets.newHashSet();
            // 多角色设置permissions属性，以便数据权限匹配权限
            for (SysRole role : roles) {
                Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                role.setPermissions(rolePerms);
                perms.addAll(rolePerms);
            }
            return perms;
        }
        return Sets.newHashSet(menuService.selectMenuPermsByUserId(user.getUserId()));
    }
}
