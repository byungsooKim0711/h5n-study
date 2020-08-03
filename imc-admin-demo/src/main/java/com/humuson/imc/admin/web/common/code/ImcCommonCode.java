package com.humuson.imc.admin.web.common.code;

import com.humuson.imc.admin.web.common.ImcCommonResponse;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public enum ImcCommonCode {
    
    // 서버 에러는 500 번대
      SERVER_ERROR (500, "INTERVAL SERVER ERROR")
    
    // ADMIN USER 관련된 에러는 700 번대
    , USER_INVALID_SESSION (700, "INVALID SESSION")
    , USER_EXPIRED_SESSION (701, "EXPIRED SESSION")
    , USER_LOGIN_FAIL (702, "BAD CREDENTIALS")
    , USER_IS_NOT_LOGIN (707, "IS NOT LOGIN")

    
    // 나머지는 개발하면서 추가
    ;

    // 코드
    private final int code;

    // 메시지
    private final String message;

    ImcCommonCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 에러 내용을 공통 무언가로 convert
    public ImcCommonResponse toBody(LocalDateTime timestamp, Object data) {
        return ImcCommonResponse.builder()
                .timestamp(timestamp)
                .status(code)
                .message(message)
                .data(data)
                .error(message)
                .build();
    }
}
