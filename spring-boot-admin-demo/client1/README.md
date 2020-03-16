# Spring Boot Admin Server

- Enable Spring boot admin client

- application.yml
```yaml
################################################################################################################
# SPRING BOOT ADMIN SERVER TEST - 1
################################################################################################################

server:
  port: 49001

spring:
  boot:
    admin:
      client:
        url: http://127.0.0.1:49000
        username: admin
        password: admin
        instance:
          name: CLIENT-1
```
