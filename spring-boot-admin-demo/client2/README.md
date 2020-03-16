# Spring Boot Admin Client-2

- Enable Spring boot admin client

- application.yml
```yaml
################################################################################################################
# SPRING BOOT ADMIN SERVER TEST - 2
################################################################################################################

server:
  port: 49002

spring:
  boot:
    admin:
      client:
        url: http://127.0.0.1:49000
        username: admin
        password: admin
        instance:
          name: CLIENT-2

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: when_authorized
```
