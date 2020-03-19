package org.kimbs.netty.code;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ReturnCode {

    SUCCESS("1000"),
    AUTHORIZED_ID_NOT_FOUND("2000"),
    AUTHORIZED_PASSWORD_FAIL("2001"),
    AUTHORIZE_KEY_FAIL("2002"),
    NOT_ALLOWD_SERVICE("3000"),
    SERVER_ERROR("9000"),
    FILTER("9999"),
    ;

    private String code;

    ReturnCode(String code) {
        this.code = code;
    }

    private static Map<String, ReturnCode> map = Arrays.stream(values()).collect(Collectors.toMap(ReturnCode::getCode, Function.identity()));

    public static ReturnCode fromValue(String code) {
        return Optional.ofNullable(map.get(code)).orElseThrow(() -> new IllegalArgumentException("Not exists ReturnCode : " + code));
    }
}
