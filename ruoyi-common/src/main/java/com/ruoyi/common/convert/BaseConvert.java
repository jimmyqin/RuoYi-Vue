package com.ruoyi.common.convert;


import com.ruoyi.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author qinrongjun
 * @description
 */
public interface BaseConvert {

    /**
     * json字符串转集合对象
     * @param value json字符串
     * @return 转换后的List
     */
    default  <T> List<T> readValueList(String value, Class<T> clazz) {
        if (StringUtils.isBlank(value)) {
            return Collections.emptyList();
        }
        return JsonUtils.readValueList(value, clazz);
    }

    /**
     * json字符串转对象
     * @param value json字符串
     * @param clazz 目标对象类型
     * @return 转换后的对象
     * @param <T> 目标对象类型
     */
    default  <T> T readValue(String value, Class<T> clazz) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JsonUtils.readValue(value, clazz);
    }

}
