package org.kimbs.rsocket.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Configuration
public class SecurityConfig {

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {
        return security
                .authorizePayload(ap -> ap.anyExchange().authenticated())
                .simpleAuthentication(Customizer.withDefaults())
                // TODO: 나중엔 적용시켜보기.
//                .jwt()
                .build();
    }

    @Bean
    MapReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(User.withDefaultPasswordEncoder().username("kimbs").password("kimbs").roles("USER").build());
    }

    @Bean
    RSocketMessageHandler messageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.getArgumentResolverConfigurer().addCustomResolver(new AuthenticationPrincipalArgumentResolver());
        handler.setRSocketStrategies(strategies);
        return handler;
    }
}
