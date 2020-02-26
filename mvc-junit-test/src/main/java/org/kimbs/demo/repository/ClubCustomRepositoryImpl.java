package org.kimbs.demo.repository;

import com.querydsl.jpa.JPAExpressions;
import org.kimbs.demo.model.Club;
import org.kimbs.demo.model.QClub;
import org.kimbs.demo.model.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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

    @Override
    public List<Club> findAllClubLeftOuterJoinMember() {
        QMember qMember = QMember.member;
        QClub qClub = QClub.club;

        return from(qClub)
                .leftJoin(qMember)
                .on(qMember.clubId.eq(qClub.id))
                .fetch();
    }
}
