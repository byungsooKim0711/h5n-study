package com.humuson.imc.admin.web.domain.admin.repository;

import com.humuson.imc.admin.web.domain.user.QWebUserAuthor;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

import static com.humuson.imc.admin.web.domain.admin.repository.QWebAdminUser.webAdminUser;


public class WebAdminUserCustomRepositoryImpl extends QuerydslRepositorySupport implements WebAdminUserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public WebAdminUserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(WebAdminUser.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<WebAdminUser> findByUserLogin(String userLogin) {
        return Optional.ofNullable(queryFactory.select(webAdminUser)
            .from(webAdminUser)
            .join(QWebUserAuthor.webUserAuthor)
            .on(webAdminUser.webUserAuthor.id.eq(QWebUserAuthor.webUserAuthor.id))
            .fetchJoin()
            .where(webAdminUser.userLogin.eq(userLogin))
            .fetchOne());
    }
}