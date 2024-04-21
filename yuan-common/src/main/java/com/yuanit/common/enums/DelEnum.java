package com.yuanit.common.enums;

/**
 * 数据库数据删除标识枚举
 * @author qinrongjun
 */

public enum DelEnum {
    DEL("2"),
    SHOW("0");
    private final String code;

    DelEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
