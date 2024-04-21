package com.ruoyi.common.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.ruoyi.common.enums.BaseEnum;

import java.io.IOException;

/**
 * @author qinrongjun
 * @description
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> implements ContextualDeserializer {

    // 记录枚举字段的类，用于获取其定义的所有枚举值
    private Class<? extends BaseEnum> propertyClass;

    public BaseEnumDeserializer() {}

    public BaseEnumDeserializer(Class<? extends BaseEnum> propertyClass) {
        this.propertyClass = propertyClass;
    }

    @Override
    public BaseEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        Integer code = p.getValueAsInt();
        return BaseEnum.codeToEnum(propertyClass, code);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) {
        if (property == null) {
            return new BaseEnumDeserializer((Class<? extends BaseEnum>) ctx.getContextualType().getRawClass());
        }
        if (property.getType().isCollectionLikeType()) {
            return new BaseEnumDeserializer((Class<? extends BaseEnum>) property.getType().containedType(0).getRawClass());
        }
        return new BaseEnumDeserializer((Class<? extends BaseEnum>) property.getType().getRawClass());
    }

}
