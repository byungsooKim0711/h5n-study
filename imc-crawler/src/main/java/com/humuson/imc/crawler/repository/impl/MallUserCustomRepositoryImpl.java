package com.humuson.imc.crawler.repository.impl;

import com.humuson.imc.crawler.model.MallUser;
import com.humuson.imc.crawler.model.QMallUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MallUserCustomRepositoryImpl extends QuerydslRepositorySupport implements MallUserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MallUserCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(MallUser.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<MallUser> selectMallUserByWelcomeFlag(String welcomeFlag) {
        return jpaQueryFactory.select(QMallUser.mallUser)
                .from(QMallUser.mallUser)
                // TODO: 어제 ~ 오늘 날짜 기간 선택 추가
                .where(QMallUser.mallUser.welcomeFlag.eq(welcomeFlag))
                .fetch();
    }
}
