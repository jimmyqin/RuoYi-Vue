package com.yuanit.common.convert;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author qinrongjun
 * @description 转换字段忽略
 */
@Retention(RetentionPolicy.CLASS)
@Mappings(value = {
        @Mapping(target = "createTime",ignore = true),
        @Mapping(target = "createBy",ignore = true),
        @Mapping(target = "updateTime",ignore = true),
        @Mapping(target = "updateBy",ignore = true),
})
public @interface MappingIgnore { }
