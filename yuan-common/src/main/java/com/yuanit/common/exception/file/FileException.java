package com.yuanit.common.exception.file;

import com.yuanit.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author 
 */
public class FileException extends BaseException {

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
