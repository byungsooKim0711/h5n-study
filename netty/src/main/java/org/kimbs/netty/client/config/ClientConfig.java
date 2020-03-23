package org.kimbs.netty.client.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "channel.client")
public class ClientConfig {

    @Min(value = 1000, message = "timeout-ms는 1000ms(1초) 보다 커야 합니다.")
    private int timeoutMs = 5 * 1000;

    @Min(value = 1000, message = "heartbeat-interval-ms는 1000ms(1초) 보다 커야 합니다.")
    private int heartbeatIntervalMs = 3 * 1000;

    private AuthServer authServer;

    public ClientConfig(int timeoutMs, int heartbeatIntervalMs, AuthServer authServer) {
        this.timeoutMs = timeoutMs;
        this.heartbeatIntervalMs = heartbeatIntervalMs;
        this.authServer = authServer;
    }

    @Getter
    @Validated
    @ConstructorBinding
    public static class AuthServer {

        @NotNull(message = "HOST IP 정보를 입력해주세요.")
        @Pattern(regexp = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b", message = "HOST IP 정보를 다시 확인해주세요.")
        private String host;

        @Min(value = 1, message = "PORT 번호를 확인해주세요.")
        @Max(value = 65536, message = "PORT 번호를 확인해주세요.")
        private int port;

        @NotNull(message = "CLIENT_ID 를 입력해주세요.")
        @NotBlank(message = "CLIENT_ID 를 입력해주세요.")
        private String id;

        @NotNull(message = "CLIENT_PASSWORD 를 입력해주세요.")
        @NotBlank(message = "CLIENT_PASSWORD 를 입력해주세요.")
        private String password;

        public AuthServer(String host, int port, String id, String password) {
            this.host = host;
            this.port = port;
            this.id = id;
            this.password = password;
        }
    }

}
