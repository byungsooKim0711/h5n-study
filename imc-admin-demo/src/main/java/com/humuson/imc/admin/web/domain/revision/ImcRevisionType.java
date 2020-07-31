package com.humuson.imc.admin.web.domain.revision;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ImcRevisionType {

    INSERT(0),
    UPDATE(1),
    DELETE(2),

    UNKNOWN(-9999)
    ;

    int code;

    ImcRevisionType(int code) {
        this.code = code;
    }

    private static final Map<Integer, ImcRevisionType> typeMap = Arrays.stream(values()).collect(Collectors.toMap(ImcRevisionType::getCode, Function.identity()));

    public static ImcRevisionType fromValue(Integer code) {
        return Optional.ofNullable(typeMap.get(code)).orElse(UNKNOWN);
    }
}
