package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonUtils  implements BeanFactoryPostProcessor {
    private static ObjectMapper objectMapper;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (objectMapper == null) {
            objectMapper = beanFactory.getBean(ObjectMapper.class);
        }
    }

    /**
     * 转换成json字符串
     * @param value 需要转换的对象
     * @return json字符串
     */
    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON处理异常", e);
        }
    }

    /**
     * 转成T类型
     * @param value json字符串
     * @param clazz 转换成的目标类型
     * @return 获得类型T对象实例
     * @param <T>
     */
    public static <T> T readValue(String value, Class<T> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON处理异常", e);
        }
    }

    /**
     * 转换成List
     * @param value json字符串
     * @param clazz List元素的Class
     * @return 获得List实例对象
     * @param <T> List的元素类型
     */
    public static <T> List<T> readValueList(String value, Class<T> clazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(value, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON处理异常", e);
        }
    }

    /**
     * 转换成Map
     * @param value json字符串
     * @param keyClazz Map的key的Class
     * @param valueClazz Map的value的Class
     * @return 获得Map实例对象
     * @param <K> Map的key类型
     * @param <V> Map的value类型
     */
    public static <K, V> Map<K, V> readValueMap(String value, Class<K> keyClazz, Class<V> valueClazz) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, keyClazz,valueClazz);
            return objectMapper.readValue(value, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON处理异常", e);
        }
    }

    /**
     * 转换成Map
     * @param value json字符串
     * @return 获得Map实例对象, key和value都是String类型
     */
    public static Map<String, String> readValueMap(String value) {
        return readValueMap(value, String.class, String.class);
    }
}
