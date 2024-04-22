package com.yuanit.common.exception.user;

/**
 * 验证码失效异常类
 * 
 * @author 
 */
public class CaptchaExpireException extends UserException {

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}
