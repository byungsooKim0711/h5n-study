package org.kimbs.netty.client.config;

import lombok.Getter;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "channel.client")
public class ClientConfig {

    private ImcAsAuthRes imcAsAuthRes;

    public void setImcAsAuthRes(ImcAsAuthRes imcAsAuthRes) {
        this.imcAsAuthRes = imcAsAuthRes;
    }

    private int timeoutMs = 5000;

    private AuthServer authServer;

    public ClientConfig(String id, String password, int timeoutMs, AuthServer authServer) {
        this.timeoutMs = timeoutMs;
        this.authServer = authServer;
    }

    @Getter
    @Validated
    @ConstructorBinding
    public static class AuthServer {
        @Pattern(regexp = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b", message = "HOST IP 정보를 다시 확인해주세요.")
        private String host;

        private int port;

        @NotBlank(message = "CLIENT_ID 를 입력해주세요.")
        private String id;

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
