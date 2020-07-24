package com.humuson.imc.admin.web.domain.convertor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanYNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equalsIgnoreCase(dbData);
    }
}
