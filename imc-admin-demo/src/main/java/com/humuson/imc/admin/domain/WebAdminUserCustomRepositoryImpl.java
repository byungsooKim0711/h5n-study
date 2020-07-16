package com.humuson.imc.admin.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.humuson.imc.admin.domain.QWebAdminUser.webAdminUser;

public class WebAdminUserCustomRepositoryImpl extends QuerydslRepositorySupport implements WebAdminUserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public WebAdminUserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(WebAdminUser.class);
        this.queryFactory = queryFactory;
    }


    @Override
    public long updateWebAdminUser(WebAdminUser adminUser, Long id) {
        return queryFactory
                .update(webAdminUser)
                    .set(webAdminUser.activeYn, adminUser.getActiveYn())
                    .set(webAdminUser.failCount, adminUser.getFailCount())
                    .set(webAdminUser.infoCp, adminUser.getInfoCp())
                    .set(webAdminUser.infoNa, adminUser.getInfoNa())
                    .set(webAdminUser.infoEm, adminUser.getInfoEm())
                    .set(webAdminUser.kakaoBizCenterId, adminUser.getKakaoBizCenterId())
                    .set(webAdminUser.webUserAuthor.id, adminUser.getWebUserAuthor().getId())
                    .set(webAdminUser.modifiedAt, LocalDateTime.now()) // TODO: Auditing 기능을 살릴 수 없을까?
                .where(webAdminUser.id.eq(id))
                .execute();
    }

    @Override
    public Optional<WebAdminUser> findByUserLogin(String username) {
        return Optional.ofNullable(
                queryFactory
                        .select(webAdminUser)
                        .from(webAdminUser)
                        .where(webAdminUser.userLogin.eq(username))
                        .fetchOne())
                ;
    }
}