package com.yuanit.common.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yuanit.common.convert.BaseEnumDeserializer;
import com.yuanit.common.exception.ServiceException;
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
    Integer getCode();

    /**
     * 标识中文
     * @return
     */
    String getInfo();



    /**
     * code转换为枚举,不存在抛出异常
     *
     * @param clazz
     * @param code
     * @param <T>
     * @return
     */
    @Named("codeToEnumNotNull")
    static <T extends BaseEnum> T codeToEnumNotNull(@TargetType Class<T> clazz, Integer code) {
        return codeToEnumOpt(clazz, code)
                .orElseThrow(() -> new ServiceException(String.format("枚举值[%s]不存在", code)));

    }


    /**
     * 通用转换对应的枚举实例
     *
     * @param clazz 枚举类型
     * @param code   枚举的key字段值
     * @return
     */
    @Named("codeToEnum")
    static <T extends BaseEnum> T codeToEnum(@TargetType Class<T> clazz, Integer code) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .orElse(null);

    }

    @Named("codeToEnumOpt")
    static <T extends BaseEnum> Optional<T> codeToEnumOpt(@TargetType Class<T> clazz, Integer code) {
        return Optional.ofNullable(codeToEnum(clazz, code));
    }

    @Named("codeToInfo")
    static String codeToInfo(@TargetType Class<? extends BaseEnum> clazz, Integer code) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getCode().equals(code))
                .findAny()
                .map(BaseEnum::getInfo)
                .orElse(null);

    }

    @Named("codeToInfoOpt")
    static Optional<String> codeToInfoOpt(@TargetType Class<? extends BaseEnum> clazz, Integer code) {
        return Optional.ofNullable(codeToInfo(clazz, code));

    }

    @Named("enumToCode")
    static Integer enumToCode(BaseEnum baseEnum) {
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getCode();
    }

    @Named("enumToInfo")
    static String enumToInfo(BaseEnum baseEnum) {
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getInfo();
    }

    @Named("infoToEnum")
    static <T extends BaseEnum> T infoToEnum(@TargetType Class<T> clazz, String info) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getInfo().equals(info))
                .findAny()
                .orElse(null);

    }

    @Named("infoToEnumDefault")
    static <T extends BaseEnum> T infoToEnumDefault(@TargetType Class<T> clazz, String info, BaseEnum defaultEnum) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return (T) defaultEnum;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getInfo().equals(info))
                .findAny()
                .orElse((T) defaultEnum);

    }

    @Named("infoToCode")
    static Integer infoToCode(@TargetType Class<? extends BaseEnum> clazz, String info) {
        if (!clazz.isEnum() | !BaseEnum.class.isAssignableFrom(clazz)) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(item -> item.getInfo().equals(info))
                .findAny()
                .map(BaseEnum::getCode)
                .orElse(null);

    }

    static <T extends BaseEnum> Optional<T> infoToEnumOpt(@TargetType Class<T> clazz, String info) {
        return Optional.ofNullable(infoToEnum(clazz, info));
    }
}
