package org.kimbs.imc.admin.web.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ImcErrorMessage {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
//    private String path;
    private final Object data;
}
