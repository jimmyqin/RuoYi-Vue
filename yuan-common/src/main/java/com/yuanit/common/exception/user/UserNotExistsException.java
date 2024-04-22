package com.yuanit.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author
 */
public class UserNotExistsException extends UserException {

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
