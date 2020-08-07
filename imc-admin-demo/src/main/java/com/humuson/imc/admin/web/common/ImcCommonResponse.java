package com.humuson.imc.admin.web.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ImcCommonResponse {

    // 응답시간
    private final LocalDateTime timestamp;
    
    // 상태값
    private final int status;
    
    // 에러 내용
    private final String error;
    
    // 추가 메시지
    private final String message;
    
    // 응답 값
    private final Object data;
}
