package org.kimbs.imc.admin.web.exception.code;

import lombok.Getter;
import org.kimbs.imc.admin.web.exception.ImcErrorMessage;

import java.time.LocalDateTime;

@Getter
public enum ImcException {
    
    // 서버 에러는 500 번대
      SERVER_ERROR (500, "INTERVAL SERVER ERROR")
    
    // ADMIN USER 관련된 에러는 700 번대
    , USER_INVALID_SESSION (700, "INVALID SESSION")
    , USER_LOGIN_FAIL (701, "BAD CREDENTIALS")
    , USER_IS_NOT_LOGIN (707, "IS NOT LOGIN")
    
    // 나머지는 개발하면서 추가
    ;

    // 에러 코드
    private final int code;
    // 에러 메시지
    private final String message;

    ImcException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 에러 내용을 공통 무언가로 convert
    public Object toBody(LocalDateTime timestamp, Object data) {
        return ImcErrorMessage.builder()
                .status(code)
                .message(message)
                .timestamp(timestamp)
                .data(data)
                //.error()
                .build();
    }
}
