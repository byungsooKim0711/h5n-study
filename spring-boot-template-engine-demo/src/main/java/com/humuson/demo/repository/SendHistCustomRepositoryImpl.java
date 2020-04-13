package com.humuson.demo.repository;

import com.humuson.demo.domain.SendHist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class SendHistCustomRepositoryImpl extends QuerydslRepositorySupport implements SendHistCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SendHistCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SendHist.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
