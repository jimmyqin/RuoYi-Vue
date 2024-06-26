package com.yuanit.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 * @author
 */
public class UserPasswordNotMatchException extends UserException {

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
