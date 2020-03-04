# SpringBoot, JPA, QueryDSL

---
**Todo List**
```
- QueryDsl
  > 복잡한 쿼리 연습
  > ...
- JPA
  > 관계 옵션들 자세히 공부
  > EntityManager와 PersistenceContext 자세히 공부
  > ...
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
```
---
**설정**
```yaml
jpa:
  database: mysql
  show-sql: true
  # DBMS 방언 설정
  database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
  # 운영에서는 ddl-auto 옵션을 none으로
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