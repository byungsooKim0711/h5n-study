package org.kimbs.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import org.kimbs.demo.model.Club;
import org.kimbs.demo.model.QClub;
import org.kimbs.demo.model.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public class ClubCustomRepositoryImpl extends QuerydslRepositorySupport implements ClubCustomRepository {

    public ClubCustomRepositoryImpl() {
        super(Club.class);
    }

    @Override
    public Optional<Club> findAllByMemberId(long memberId) {
        QMember qMember = QMember.member;
        QClub qClub = QClub.club;

        return Optional.ofNullable(from(qClub)
                .where(qClub.id.in(
                        JPAExpressions
                        .select(qMember.clubId)
                        .from(qMember)
                        .where(qMember.id.eq(memberId))
                ))
                .fetchOne()
        );
    }

    public List<Club> dynamicQuery(String name, String office, String telephoneNumber) {
        QClub qClub = QClub.club;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(qClub.name.eq(name));
        }
        if (!StringUtils.isEmpty(office)) {
            booleanBuilder.and(qClub.office.eq(office));
        }
        if (!StringUtils.isEmpty(telephoneNumber)) {
            booleanBuilder.and(qClub.telephoneNumber.eq(telephoneNumber));
        }

        return from(qClub)
                .where(booleanBuilder)
                .fetch();
    }
}
