package org.kimbs.netty.client.config;

import lombok.Getter;
import lombok.Setter;
import org.kimbs.netty.packet.options.as.ImcAsAuthRes;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ClientConfig {

    private ImcAsAuthRes imcAsAuthRes;
}
