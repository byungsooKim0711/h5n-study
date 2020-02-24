package org.kimbs.demo.repository;

import org.kimbs.demo.model.Member;
import org.kimbs.demo.model.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberCustomRepository {

    class MemberRepositoryQueryDsl extends QuerydslRepositorySupport implements MemberCustomRepository {

        public MemberRepositoryQueryDsl() {
            super(Member.class);
        }

        @Override
        public List<Member> findMemberByEmail(String email) {
            QMember qMember = QMember.member;
            return from(qMember).where(qMember.email.eq(email)).fetch();
        }

        @Override
        public List<Member> findMemberByName(String name) {
            QMember qMember = QMember.member;
            return from(qMember).where(qMember.name.eq(name)).fetch();
        }

        @Override
        public List<Member> findMemberByScoreGreaterThanEqual(int score) {
            QMember qMember = QMember.member;
            return from(qMember).where(qMember.score.goe(score)).fetch();
        }
    }
}
