package com.humuson.imc.admin.web.domain.revision;

import javax.persistence.AttributeConverter;


// xxx_history 테이블 조회시 쓰려고 했더니.....
public final class ImcRevisionTypeConverter implements AttributeConverter<ImcRevisionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ImcRevisionType attribute) {
        return attribute.getCode();
    }

    @Override
    public ImcRevisionType convertToEntityAttribute(Integer dbData) {
        return ImcRevisionType.fromValue(dbData);
    }
}
