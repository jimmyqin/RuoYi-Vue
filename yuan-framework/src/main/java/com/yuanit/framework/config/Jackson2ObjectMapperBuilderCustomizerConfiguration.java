package com.yuanit.framework.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yuanit.common.config.serializer.LongToStringSerializer;
import com.yuanit.common.config.serializer.TrimDeserialize;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author qinrongjun
 * @description
 */
@AutoConfiguration
public class Jackson2ObjectMapperBuilderCustomizerConfiguration implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        //针对于Date类型，文本格式化
        jacksonObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //针对于JDK1.8新时间类。序列化时带有T的问题，自定义格式化字符串
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        jacksonObjectMapperBuilder.modules(javaTimeModule);
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        jacksonObjectMapperBuilder.serializerByType(Long.class, LongToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(Long.TYPE, LongToStringSerializer.instance);
        jacksonObjectMapperBuilder.deserializerByType(String.class, TrimDeserialize.instance); // 去除post requestBody实体中的空格

        // 解决enum不匹配问题
        jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        //枚举默认值注解@JsonEnumDefaultValue启用
        jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

    }
}
