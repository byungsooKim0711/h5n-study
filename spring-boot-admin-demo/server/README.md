# Spring Boot Admin Server

- Enable Spring boot admin server
```java
@SpringBootApplication
@EnableAdminServer
public class ServerApplication {
    /** 생략... */
}
```
- application.yml
```yaml
#######################################
# SPRING BOOT ADMIN SERVER TEST
#######################################

# Boot Admin Port#
server:
  port: 49000

# Logging 설정
logging:
  config: file:${user.dir}/logback-spring.xml

spring:
  boot:
    admin:
      ui:
        brand: Kim-Byung-Soo
        title: Kim-Byung-Soo
        remember-me-enabled: true
      notify:
        mail:
          enabled: true
            from: {example@example.com}
          to:
            - {example@example.com}

  mail:
      host: {smtp.example.com}
    username: {smtp_username}
    password: {smtp_password}

  security:
    user:
      name: admin
      password: admin
```
