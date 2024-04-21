package com.yuanit.common.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author qinrongjun
 * @description
 * @date 2023/7/6 18:29 星期四
 */
public class TrimDeserialize extends StdScalarDeserializer<Object> {

    public static final TrimDeserialize instance = new TrimDeserialize(Object.class);

    protected TrimDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
        return StringUtils.trimWhitespace(jsonParser.getValueAsString());
    }
}
