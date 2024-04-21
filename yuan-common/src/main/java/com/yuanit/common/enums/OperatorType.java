package com.yuanit.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作人类别
 * 
 * @author ruoyi
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum OperatorType implements BaseEnum{

    OTHER(1,"其它"),
    MANAGE(2, "后台用户"),
    MOBILE(3,"手机端用户");

    private final Integer code;
    private final String info;
}
