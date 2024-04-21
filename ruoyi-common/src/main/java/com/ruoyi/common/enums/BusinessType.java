package com.ruoyi.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务操作类型
 * 
 * @author ruoyi
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum BusinessType implements BaseEnum{

    OTHER(1,"其他"),
    INSERT(2, "新增"),
    UPDATE(3, "修改"),
    DELETE(4, "删除"),
    GRANT(5, "授权"),
    EXPORT(6, "导出"),
    IMPORT(7, "导入"),
    FORCE(8,"强退"),
    GENCODE(9, "生成代码"),
    CLEAN(10, "清空数据");


    private final Integer code;
    private final String info;
}
