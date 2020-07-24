package com.humuson.imc.admin.web.domain.admin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class WebAdminUserCustomRepositoryImpl extends QuerydslRepositorySupport implements WebAdminUserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public WebAdminUserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(WebAdminUser.class);
        this.queryFactory = queryFactory;
    }
}