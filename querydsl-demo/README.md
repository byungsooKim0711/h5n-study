# SpringBoot, JPA, QueryDSL

---
**Todo List**
```
- QueryDsl
  > 복잡한 쿼리 연습
  > delete, update ...
  > ...
- JPA
  > 관계 옵션들 자세히 공부
  > EntityManager와 PersistenceContext 자세히 공부
  > Paging 쿼리 공부
  > ...

- 기타
  > JPAQueryFactory를 꼭 이용해야 하는가? QueryDslRepositorySupport랑은 무슨 연관관계가?
  > 애그리거트 공부
  > Query Hint 사용해보기
```

---
**JPA**
```
- FetchType은 전부 LAZY로 설정
- 1 + N 쿼리 문제는 fetch join으로 해결
- 양방향 관계 설정을 헀을 경우 옵션 설정
-
```

---
**QueryDSL**
```
- customRepository 인터페이스 를 만들고, customRepositoryImpl 에서 customRepository 를 구현하여 사용 (규칙)
- QueryDsl을 사용하다 보니, DBMS의 Optimizer 실행 순서와 상관없이 작성해도 동작함....(?)
- QDomain 생성은 maven compile하면 지정한 위치에 생김
```
---
**설정**
```yaml
spring:
  datasource:
    # mysql version >= 6.x.x driver
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/querydsl?autoReconnect=true&useSSL=false&serverTimezone=UTC
    username: 
    password: 

  jpa:
    database: mysql
    show-sql: true
    # mysql 8 버전 방언에 맞추고 innodb 사용
    database-platform: org.hibernate.dialect.MySQL8InnoDBDialect
    # 운영시에는 ddl-auto 옵션을 none
    hibernate:
      ddl-auto: 'create-drop'
    generate-ddl: true
    # Hibernate: 쿼리를 보기좋게 보여줌
    properties:
      hibernate.format_sql: true

# hibernate logging level을 trace로 설정하면 바인딩(binding) 되는 값과 추출(extracted)되는 값을 볼 수 있음.
logging:
  level:
    org:
      hibernate:
        type: trace

```

---
```java
/*********************************************************************************************************/
    /* Configuration */
    @Bean
    public com.querydsl.sql.Configuration configuration() {
        SQLTemplates templates = MySQLTemplates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory(DataSource dataSource) {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource);
        return new SQLQueryFactory(configuration(), provider);
    }

    /* query example */
    @Transactional
    @Override
    public List<Tuple> fromClauseSubQueryExampleWithJoin() {
        StringExpression query1 = Expressions.stringPath("dname");
        StringExpression query2 = Expressions.stringPath("sname");

        return sqlQueryFactory.select(query1, query2)
                .from(
                        SQLExpressions
                        .select(department.name.as("dname"), student.name.as("sname"))
                        .from(department)
                        .innerJoin(student)
                        .on(department.id.eq(student.department.id))
                        .orderBy(student.name.desc()).as("dual")
                )
                .fetch();
    }

    @Transactional
    @Override
    public List<Tuple> fromClauseSubQueryExample() {
        StringExpression sName = Expressions.stringPath("STUDENT_NAME");
        StringExpression sAddress = Expressions.stringPath("STUDENT_ADDRESS");

        return sqlQueryFactory.select(sName, sAddress)
                .from(
                        SQLExpressions
                        .select(student.name.as("STUDENT_NAME"), student.address.as("STUDENT_ADDRESS"))
                        .from(student)
                        .orderBy(student.name.desc()).as("dual")
                )
                .fetch();
    }

/*********************************************************************************************************/
```