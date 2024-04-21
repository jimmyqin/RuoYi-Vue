package com.yuanit.common.exception.user;

import com.yuanit.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author ruoyi
 */
public class UserException extends BaseException {

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
