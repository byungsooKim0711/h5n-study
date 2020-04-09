## Logback `.log 파일` 자동 압축
##### `<fileNamePattern>/log/kimbs-server/demo/%d{yyyy-MM-dd}.log.gz</fileNamePattern>` 와 같이 사용하면, 00시 00분에 자동 압축해준다. 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%thread] %logger{35} : %msg%n</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <appender name="dailyRollingFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/log/kimbs-server/demo/%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%thread] %logger{35} : %msg%n</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <logger name="org.kimbs.demo" level="INFO"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="dailyRollingFileAppender" />
    </root>
</configuration>
```

###  필드 별로 설정 값 정리
`<appender name="${}" class="${}"> </appender>`
```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%thread] %logger{35} : %msg%n</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>
</configuration>
```
- appender

`<rollingPolicy class="${}"> </rollingPolicy>`
```xml
<configuration>
    <appender name="dailyRollingFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/log/kimbs-server/demo/%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%thread] %logger{35} : %msg%n</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>
</configuration>
```
`<logger name="${}" level="${}"/>`
```xml
<configuration>
    <logger name="org.kimbs.demo" level="INFO"/>
</configuration>
```
`<root level="${}"> </root>`
```xml
<configuration>
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="dailyRollingFileAppender" />
    </root>
</configuration>
```