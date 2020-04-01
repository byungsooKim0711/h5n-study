package org.kimbs.reactor.netty.packet;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Command {

    // 인증 서버
    IMC_AS_AUTH_REQ(101002),
    IMC_AS_AUTH_RES(101102),

    // 메시지 서버
    IMC_RS_AUTH_REQ(201002),
    IMC_RS_AUTH_RES(201102),
    IMC_RS_AT_PUSH_REQ(202002),
    IMC_RS_AT_PUSH_RES(202102),
    IMC_RS_FT_PUSH_REQ(202202),
    IMC_RS_FT_PUSH_RES(202302),

    // 결과 서버
    IMC_RS_REPORT_REQ(2030),
    IMC_RS_REPORT_RES(2031)
    ;

    private int code;

    Command(int code) {
        this.code = code;
    }

    private static Map<Integer, Command> map = Arrays.stream(values()).collect(Collectors.toMap(Command::getCode, Function.identity()));

    public static Command fromValue(int code) {
        return Optional.ofNullable(map.get(code)).orElseThrow(() -> new IllegalArgumentException("Not exists Command : " + code));
    }
}