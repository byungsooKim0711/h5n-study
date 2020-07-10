package org.kimbs.imc.admin.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.security.ImcUserDetailsService;
import org.kimbs.imc.admin.security.handler.LoginFailureHandler;
import org.kimbs.imc.admin.security.handler.LoginSuccessHandler;
import org.kimbs.imc.admin.security.handler.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final LogoutSuccessHandlerImpl logoutSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    private final ImcUserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/static/css/**", "/static/fonts/**", "/static/img/**", "/static/js/**", "./favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/admin/**").hasRole("MANAGE")
                .anyRequest().authenticated();
                ;

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/index.html")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                ;

        http.logout()
                .logoutUrl("/logout").permitAll()
                .deleteCookies("IMC-SESSION-ID")
                .logoutSuccessHandler(logoutSuccessHandler)
                ;

        http.sessionManagement()
//                .invalidSessionStrategy(new ImcInvalidSessionStrategy())
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/index.html")
                .and().invalidSessionUrl("/index.html");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher() {
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                HttpSession session = event.getSession();
                System.out.println(session.getId());
                Enumeration<String> names = session.getAttributeNames();
                while(names.hasMoreElements()) {
                    System.out.println(names.nextElement());
                }

                log.info("{}", session.getCreationTime());
                log.info("{}", session.getLastAccessedTime());
                log.info("{}", session.getMaxInactiveInterval());
                log.info("{}", session.getServletContext());
                log.info("{}", session.isNew());
                log.info("{}", session);
                super.sessionCreated(event);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                event.getSession().removeAttribute("account");
                super.sessionDestroyed(event);
            }
        };
    }
}
