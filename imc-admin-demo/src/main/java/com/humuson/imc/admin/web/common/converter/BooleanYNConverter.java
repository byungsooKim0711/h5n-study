package com.humuson.imc.admin.web.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * [APP] <-> [DB]
 */
@Converter
public final class BooleanYNConverter implements AttributeConverter<Boolean, String> {

    private static final String TRUE = "Y";
    private static final String FALSE = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? TRUE : FALSE;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return TRUE.equalsIgnoreCase(dbData);
    }
}
