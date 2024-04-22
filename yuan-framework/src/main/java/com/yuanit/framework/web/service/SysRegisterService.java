package com.yuanit.framework.web.service;

import com.yuanit.common.constant.CacheConstants;
import com.yuanit.common.constant.Constants;
import com.yuanit.common.constant.UserConstants;
import com.yuanit.common.core.domain.entity.SysUser;
import com.yuanit.common.core.domain.model.RegisterBody;
import com.yuanit.common.core.redis.RedisCache;
import com.yuanit.common.exception.user.CaptchaException;
import com.yuanit.common.exception.user.CaptchaExpireException;
import com.yuanit.common.utils.MessageUtils;
import com.yuanit.common.utils.StringUtils;
import com.yuanit.framework.manager.AsyncManager;
import com.yuanit.framework.manager.factory.AsyncFactory;
import com.yuanit.system.service.ISysConfigService;
import com.yuanit.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 注册校验方法
 * 
 * @author
 */
@RequiredArgsConstructor
@Component
public class SysRegisterService {

    private final ISysUserService userService;
    private final ISysConfigService configService;
    private final RedisCache redisCache;
    private final PasswordEncoder passwordEncoder;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            return  "用户名不能为空";
        }

        if (StringUtils.isEmpty(password)) {
            return  "用户密码不能为空";
        }

        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            return "账户长度必须在2到20个字符之间";
        }

        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            return "密码长度必须在5到20个字符之间";
        }

        if (!userService.checkUserNameUnique(sysUser)) {
            return "保存用户'" + username + "'失败，注册账号已存在";
        }

        sysUser.setNickName(username);
        sysUser.setPassword(passwordEncoder.encode(password));
        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            return "注册失败,请联系系统管理人员";
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
        return "";
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
