package com.ruoyi.common.convert;

import com.google.common.collect.Lists;
import com.ruoyi.common.utils.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

/**
 * @author qinrongjun
 * @description
 */
public interface CommonConvert {

    /**
     * 集合字符串转为逗号隔开的字符串
     * @param imageList 集合字符串
     * @return 逗号隔开的字符串
     */
    @Named("toStr")
    static String toStr(List<String> imageList) {
        if (CollectionUtils.isEmpty(imageList)) {
            return null;
        }
        return String.join(",", imageList);
    }

    /**
     * 逗号隔开的字符串转List
     * @param imageList 逗号隔开的字符串
     * @return 转换后的List
     */
    @Named("toList")
    static List<String> toList(String imageList) {
        if (StringUtils.isBlank(imageList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(imageList.split(","));
    }

    /**
     * json字符串转对象
     * @param value json字符串
     * @return 转换后的对象
     */
    @Named("readValue")
    static <T> T readValue(String value, Class<T> clazz) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JsonUtils.readValue(value, clazz);
    }

    /**
     * bool类型字段转换为是否,true转换为是,false或者null转换为否
     * @param bool 值
     * @return 是或者否
     */
    @Named("boolToCN")
    static String boolToCN(Boolean bool) {
        return BooleanUtils.isTrue(bool) ? "是" : "否";
    }

}
