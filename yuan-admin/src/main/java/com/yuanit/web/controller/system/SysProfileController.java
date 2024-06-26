package com.yuanit.web.controller.system;

import com.yuanit.common.annotation.Log;
import com.yuanit.common.config.SysConfig;
import com.yuanit.common.core.controller.BaseController;
import com.yuanit.common.core.domain.AjaxResult;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.domain.entity.SysUser;
import com.yuanit.common.core.domain.model.LoginUser;
import com.yuanit.common.enums.BusinessType;
import com.yuanit.common.utils.StringUtils;
import com.yuanit.common.utils.file.FileUploadUtils;
import com.yuanit.common.utils.file.MimeTypeUtils;
import com.yuanit.framework.web.service.TokenService;
import com.yuanit.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人信息 业务处理
 *
 * @author
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("system/user/profile")
public class SysProfileController extends BaseController {

    private final ISysUserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser currentUser = loginUser.getUser();
        currentUser.setNickName(user.getNickName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(currentUser)) {
            return R.fail("修改用户'" + loginUser.getUsername() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(currentUser)) {
            return R.fail("修改用户'" + loginUser.getUsername() + "'失败，邮箱账号已存在");
        }
        if (userService.updateUserProfile(currentUser) > 0) {
            // 更新缓存用户信息
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R updatePwd(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword")String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!passwordEncoder.matches(oldPassword, password)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPassword, password)) {
            return R.fail("新密码不能与旧密码相同");
        }
        newPassword = passwordEncoder.encode(newPassword);
        if (userService.resetUserPwd(userName, newPassword) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(newPassword);
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return error("上传图片异常，请联系管理员");
        }
        LoginUser loginUser = getLoginUser();
        String avatar = FileUploadUtils.upload(SysConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
        if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", avatar);
            // 更新缓存用户头像
            loginUser.getUser().setAvatar(avatar);
            tokenService.setLoginUser(loginUser);
            return ajax;
        }
        return error("上传图片异常，请联系管理员");
    }
}
