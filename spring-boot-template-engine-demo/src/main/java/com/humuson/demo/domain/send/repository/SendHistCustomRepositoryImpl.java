package com.humuson.demo.domain.send.repository;

import com.humuson.demo.domain.send.SendHist;
import com.humuson.demo.domain.send.dto.BizHistDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.humuson.demo.domain.QSendHist.sendHist;
import static com.humuson.demo.domain.QSendProfile.sendProfile;


public class SendHistCustomRepositoryImpl extends QuerydslRepositorySupport implements SendHistCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SendHistCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(SendHist.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<BizHistDto> findSendHistByCompanyName(String companyName, Pageable pageable) {

        return jpaQueryFactory
                .select(
                        Projections.fields(BizHistDto.class,
                                sendHist.reqDate.as("reqDate"),
                                sendHist.sendProfile.name.as("companyName"),
                                sendHist.sendType.as("sendType"),
                                sendHist.contents.as("contents"),
                                sendHist.templateId.as("templateId")
                        )
                )
                .from(sendHist)
                .innerJoin(sendProfile)
                .on(sendHist.sendProfile.id.eq(sendProfile.id))
                .fetchJoin()
//                // 사용중인 프로필만 조건 추가
                .where(this.getSearchCondition(companyName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanBuilder getSearchCondition(String companyName) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!StringUtils.isEmpty(companyName)) {
            booleanBuilder.and(sendProfile.name.eq(companyName));
        }

        return booleanBuilder;
    }
}
