package com.yuanit.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态
 * 
 * @author ruoyi
 *
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum BusinessStatus  implements BaseEnum{

    SUCCESS(1,"成功"),
    FAIL(2,"失败");

    private final Integer code;
    private final String info;
}
