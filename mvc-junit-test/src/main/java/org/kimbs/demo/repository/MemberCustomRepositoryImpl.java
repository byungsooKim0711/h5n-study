package org.kimbs.demo.repository;

import org.kimbs.demo.model.Member;
import org.kimbs.demo.model.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

// 공식인가 봅니다. 신기하네...
// https://docs.spring.io/spring-data/jpa/docs/2.2.1.RELEASE/reference/html/#repositories.custom-implementations
// The most important part of the class name that corresponds to the fragment interface is the Impl postfix.
class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {

    public MemberCustomRepositoryImpl() {
        super(Member.class);
    }

    // 복잡한 쿼리들을 여기에 구현하고 MemberRepository 주입해서 사용.
    @Override
    public List<Member> findMemberByEmail(String email) {
        QMember qMember = QMember.member;
        return from(qMember)
            .where(qMember.email.eq(email))
            .fetch();
    }

    @Override
    public List<Member> findMemberByName(String name) {
        QMember qMember = QMember.member;
        return from(qMember)
            .where(qMember.name.eq(name))
            .fetch();
    }

    @Override
    public List<Member> findMemberByScoreGreaterThanEqual(int score) {
        QMember qMember = QMember.member;
        return from(qMember)
            .where(qMember.score.goe(score))
            .fetch();
    }

    @Override
    public Optional<Member> findMemberById(long id) {
        System.out.println("진짜 이게 실행이 되는건가요?! 신기...");
        QMember qMember = QMember.member;
        return Optional.ofNullable(
            from(qMember)
            .where(qMember.id.eq(id))
            .fetchOne());
    }

    @Override
    public List<Member> findMemberByNameContainingOrderByNameAscAndOrderByScoreDesc(String name) {
        QMember qMember = QMember.member;
        return from(qMember)
            .where(qMember.name.contains(name))
            .orderBy(qMember.name.asc(), qMember.score.desc())
            .fetch();
    }
}