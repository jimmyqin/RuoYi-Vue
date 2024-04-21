package com.ruoyi.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author ruoyi
 */
public class UserNotExistsException extends UserException {

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
