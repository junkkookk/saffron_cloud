package com.w.saffron.common.enumrate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author w
 * @since 2023/5/20
 */
public class CodeToEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    public BaseEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
        final Integer param = jsonParser.getIntValue();
        final JsonStreamContext parsingContext = jsonParser.getParsingContext();
        final String currentName = parsingContext.getCurrentName();
        final Object currentValue = parsingContext.getCurrentValue();
        try {
            final Field declaredField = currentValue.getClass().getDeclaredField(currentName);
            final Class<?> targetType = declaredField.getType();
            final Method valuesMethod = targetType.getDeclaredMethod("values");
            BaseEnum[] enums = (BaseEnum[]) valuesMethod.invoke(null);
            for (BaseEnum anEnum : enums) {
                if(anEnum.getCode().equals(param)){
                    return anEnum;
                }
            }
            throw new RuntimeException("不匹配");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("不匹配",e);
        }
    }
}
