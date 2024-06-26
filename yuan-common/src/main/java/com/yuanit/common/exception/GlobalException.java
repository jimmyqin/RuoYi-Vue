package com.yuanit.common.exception;

/**
 * 全局异常
 * 
 * @author 
 */
public class GlobalException extends RuntimeException {

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public GlobalException() {}

    public GlobalException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public GlobalException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public GlobalException setMessage(String message) {
        this.message = message;
        return this;
    }
}