package com.humuson.imc.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.security.ImcUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.humuson.imc.admin.security.ImcInvalidSessionStrategy;
import com.humuson.imc.admin.security.handler.LoginFailureHandler;
import com.humuson.imc.admin.security.handler.LoginSuccessHandler;
import com.humuson.imc.admin.security.handler.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // handler
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;

    // password encoder
    private final PasswordEncoder passwordEncoder;

    // user details service
    private final ImcUserDetailsService userDetailsService;

    // object mapper
    private final ObjectMapper mapper;

    // configuration
    private final ImcAdminConfig config;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers(config.getSecurity().getStaticResources());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers(config.getSecurity().getPermitAllUrl()).permitAll()
//                .antMatchers(config.getSecurity().getRoleManageUrl()).hasRole(ImcGrantedAuthority.MANAGE.name())
            .anyRequest().authenticated()
            ;

        http.formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginPage(config.getSecurity().getDefaultViewUrl())
            .loginProcessingUrl(config.getSecurity().getLoginUrl())
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
            .permitAll()
            ;

        http.logout()
            .logoutUrl(config.getSecurity().getLogoutUrl()).permitAll()
//                .deleteCookies("IMC-SESSION-ID")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .logoutSuccessHandler(logoutSuccessHandler)
            ;

        http.sessionManagement()
            .invalidSessionStrategy(new ImcInvalidSessionStrategy(mapper))
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
            .expiredUrl(config.getSecurity().getDefaultViewUrl())
            .and().invalidSessionUrl(config.getSecurity().getDefaultViewUrl());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher() {
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                super.sessionCreated(event);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                HttpSession session = event.getSession();
                SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

                if (context != null) {
                    Authentication authentication = context.getAuthentication();
                    log.info("Session destroyed. username: {}", ((ImcUserDetails)authentication.getPrincipal()).getUsername());
                }
                super.sessionDestroyed(event);
            }
        };
    }
}
