package com.yuanit.common.exception.user;

/**
 * 黑名单IP异常类
 * 
 * @author
 */
public class BlackListException extends UserException {

    public BlackListException()
    {
        super("login.blocked", null);
    }
}
