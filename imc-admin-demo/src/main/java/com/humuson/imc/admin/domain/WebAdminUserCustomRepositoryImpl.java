package com.humuson.imc.admin.domain;

import com.kimbs.imc.admin.domain.QWebAdminUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.Optional;

public class WebAdminUserCustomRepositoryImpl extends QuerydslRepositorySupport implements WebAdminUserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public WebAdminUserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(WebAdminUser.class);
        this.queryFactory = queryFactory;
    }


    @Override
    public long updateWebAdminUser(WebAdminUser adminUser, Long id) {
        return queryFactory
                .update(QWebAdminUser.webAdminUser)
                    .set(QWebAdminUser.webAdminUser.activeYn, adminUser.getActiveYn())
                    .set(QWebAdminUser.webAdminUser.failCount, adminUser.getFailCount())
                    .set(QWebAdminUser.webAdminUser.infoCp, adminUser.getInfoCp())
                    .set(QWebAdminUser.webAdminUser.infoNa, adminUser.getInfoNa())
                    .set(QWebAdminUser.webAdminUser.infoEm, adminUser.getInfoEm())
                    .set(QWebAdminUser.webAdminUser.kakaoBizCenterId, adminUser.getKakaoBizCenterId())
                    .set(QWebAdminUser.webAdminUser.webUserAuthor.id, adminUser.getWebUserAuthor().getId())
                    .set(QWebAdminUser.webAdminUser.modifiedAt, LocalDateTime.now()) // TODO: Auditing 기능을 살릴 수 없을까?
                .where(QWebAdminUser.webAdminUser.id.eq(id))
                .execute();
    }

    @Override
    public Optional<WebAdminUser> findByUserLogin(String username) {
        return Optional.ofNullable(
                queryFactory
                        .select(QWebAdminUser.webAdminUser)
                        .from(QWebAdminUser.webAdminUser)
                        .where(QWebAdminUser.webAdminUser.userLogin.eq(username))
                        .fetchOne())
                ;
    }
}