package com.ruoyi.common.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.convert.BaseEnumDeserializer;
import com.ruoyi.common.exception.ServiceException;
import org.apache.commons.lang3.math.NumberUtils;
import org.mapstruct.Named;
import org.mapstruct.TargetType;

import java.util.Arrays;
import java.util.Optional;

/**
 * @description: 枚举基类
 * @author: qinrongjun
 **/
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum {

    /**
     * 标识
     * @return
     */
    String getKey();

    /**
     * 标识中文
     * @return
     */
    String getValue();


    @JsonIgnore
    default Integer getIntKey() {
        return Optional.ofNullable(getKey())
                .filter(NumberUtils::isCreatable)
                .map(Integer::valueOf)
                .orElse(null);
    }

    /**
     * key转换为枚举,不存在抛出异常
     *
     * @param clazz 枚举类型
     * @param key   枚举的key字段值
     * @return
     */
    @Named("keyToEnumNotNull")
    static <T extends BaseEnum> T keyToEnumNotNull(@TargetType Class<T> clazz, String key) {
        return keyToEnumOpt(clazz, key)
                .orElseThrow(() -> new ServiceException(String.format("枚举值[%s]不存在", key)));
    }

    /**
     * int类型key转换为枚举,不存在抛出异常
     *
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    @Named("intKeyToEnumNotNull")
    static <T extends BaseEnum> T intKeyToEnumNotNull(@TargetType Class<T> clazz, Integer key) {
        return intKeyToEnumOpt(clazz, key)
                .orElseThrow(() -> new ServiceException(String.format("枚举值[%s]不存在", key)));

    }


    /**
     * 通用转换对应的枚举实例
     *
     * @param clazz 枚举类型
     * @param key   枚举的key字段值
     * @return
     */
    @Named("keyToEnum")
    static <T extends BaseEnum> T keyToEnum(@TargetType Class<T> clazz, String key) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getKey().equals(key))
                .findAny()
                .orElse(null);

    }

    @Named("intKeyToEnum")
    static <T extends BaseEnum> T intKeyToEnum(@TargetType Class<T> clazz, Integer key) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getIntKey().equals(key))
                .findAny()
                .orElse(null);

    }

    @Named("intKeyToEnumOpt")
    static <T extends BaseEnum> Optional<T> intKeyToEnumOpt(@TargetType Class<T> clazz, Integer key) {
        return Optional.ofNullable(intKeyToEnum(clazz, key));
    }

    @Named("intKeyToValue")
    static String intKeyToValue(@TargetType Class<? extends BaseEnum> clazz, Integer key) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getIntKey().equals(key))
                .findAny()
                .map(BaseEnum::getValue)
                .orElse(null);

    }

    @Named("intKeyToValueOpt")
    static Optional<String> intKeyToValueOpt(@TargetType Class<? extends BaseEnum> clazz, Integer key) {
        return Optional.ofNullable(intKeyToValue(clazz, key));

    }

    @Named("keyToValue")
    static String keyToValue(@TargetType Class<? extends BaseEnum> clazz, String key) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getKey().equals(key))
                .findAny()
                .map(BaseEnum::getValue)
                .orElse(null);

    }

    @Named("keyToValueOpt")
    static Optional<String> keyToValueOpt(@TargetType Class<? extends BaseEnum> clazz, String key) {
        return Optional.ofNullable(keyToValue(clazz, key));

    }

    @Named("keyToEnumOpt")
    static <T extends BaseEnum> Optional<T> keyToEnumOpt(@TargetType Class<T> clazz, String key) {
        return Optional.ofNullable(keyToEnum(clazz, key));
    }

    @Named("enumToKey")
    static String enumToKey(BaseEnum baseEnum) {
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getKey();
    }

    @Named("enumToValue")
    static String enumToValue(BaseEnum baseEnum) {
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getValue();
    }

    @Named("enumToIntKey")
    static Integer enumToIntKey(BaseEnum baseEnum) {
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getIntKey();
    }

    @Named("valueToEnum")
    static <T extends BaseEnum> T valueToEnum(@TargetType Class<T> clazz, String value) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findAny()
                .orElse(null);

    }

    @Named("valueToEnum")
    static <T extends BaseEnum> T valueToEnumDefault(@TargetType Class<T> clazz, String value, BaseEnum defaultEnum) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return (T) defaultEnum;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findAny()
                .orElse((T) defaultEnum);

    }

    @Named("valueToKey")
    static String valueToKey(@TargetType Class<? extends BaseEnum> clazz, String value) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findAny()
                .map(BaseEnum::getKey)
                .orElse(null);

    }

    @Named("valueToIntKey")
    static Integer valueToIntKey(@TargetType Class<? extends BaseEnum> clazz, String value) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findAny()
                .map(BaseEnum::getIntKey)
                .orElse(null);

    }

    static <T extends BaseEnum> Optional<T> valueToEnumOpt(@TargetType Class<T> clazz, String value) {
        return Optional.ofNullable(valueToEnum(clazz, value));
    }
}
