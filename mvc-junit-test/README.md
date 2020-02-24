# h5n-study
사내 스터디

---
- H2 Memory DB
- Spring Data JPA
- Project Lombok (Lombok을 사용중이니, Ctrl + Alt + S -> plugins -> MarketPlace 에서 Lombok 플러그인 설치를 필수)
- Junit 5 (jupiter) 
- Mockito


---
- QueryDsl 연습
> https://docs.spring.io/spring-data/jpa/docs/2.2.1.RELEASE/reference/html/#repositories.custom-implementations
> The most important part of the class name that corresponds to the fragment interface is the Impl postfix.
>
- 문서대로 네이밍 규칙을 지켜서 사용.
- 복잡한 쿼리들을 MemberCustomRepositoryImpl 애 구현하고 MemberRepository 주입해서 사용.