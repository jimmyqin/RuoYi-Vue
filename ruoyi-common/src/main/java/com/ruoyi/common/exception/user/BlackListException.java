package com.ruoyi.common.exception.user;

/**
 * 黑名单IP异常类
 * 
 * @author ruoyi
 */
public class BlackListException extends UserException {

    public BlackListException()
    {
        super("login.blocked", null);
    }
}
