package com.w.saffron.common.enumrate;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.AttributeConverter;

/**
 * @author w
 * @since 2023/5/20
 */
public interface BaseEnum {

    @JsonValue
    Integer getCode();

    String getDesc();

    abstract class ConvertBase<T extends Enum<T> & BaseEnum>
        implements AttributeConverter<T, java.lang.Integer>{
        private final Class<T> clazz;

        protected ConvertBase(Class<T> clazz){
            this.clazz = clazz;
        }

        @Override
        public java.lang.Integer convertToDatabaseColumn(T attr) {
            return attr!=null?attr.getCode():null;
        }

        @Override
        public T convertToEntityAttribute(java.lang.Integer dbData) {
            T[] enumConstants = clazz.getEnumConstants();
            for (T constant : enumConstants) {
                if (constant.getCode().equals(dbData)){
                    return constant;
                }
            }
            return null;
        }
    }


}
