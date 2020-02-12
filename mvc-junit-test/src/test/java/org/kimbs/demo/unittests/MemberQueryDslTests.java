package org.kimbs.demo.unittests;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.model.QMember;
import org.kimbs.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberQueryDslTests {

    @Autowired
    private MemberRepository repository;

    private List<Member> db;

    @BeforeEach
    public void setUp() throws Exception {
        /* arrange */
        Member member1 = new Member();
        member1.setScore(100);
        member1.setName("김병수");
        member1.setEmail("kbs0711@humuson.com");

        Member member2 = new Member();
        member2.setScore(77);
        member2.setName("김민수");
        member2.setEmail("min@min.min");

        Member member3 = new Member();
        member3.setScore(3);
        member3.setName("홍길동");
        member3.setEmail("honb@hong.hong");

        Member member4 = new Member();
        member4.setScore(10);
        member4.setName("김병수");
        member4.setEmail("kim@kim.kim");

        Member member5 = new Member();
        member5.setScore(99);
        member5.setName("박창선");
        member5.setEmail("park@park.park");

        Member member6 = new Member();
        member6.setScore(42);
        member6.setName("양진우");
        member6.setEmail("yang@yang.yang");

        Member member7 = new Member();
        member7.setScore(11);
        member7.setName("김성광");
        member7.setEmail("ksg@ksg.ksg");

        Member member8 = new Member();
        member8.setScore(80);
        member8.setName("이요섭");
        member8.setEmail("lee@lee.lee");

        Member member9 = new Member();
        member9.setScore(25);
        member9.setName("최수빈");
        member9.setEmail("csb@csb.csb");

        Member member10 = new Member();
        member10.setScore(100);
        member10.setName("최민수");
        member10.setEmail("choi@choi.choi");

        member1 = repository.save(member1);
        member2 = repository.save(member2);
        member3 = repository.save(member3);
        member4 = repository.save(member4);
        member5 = repository.save(member5);
        member6 = repository.save(member6);
        member7 = repository.save(member7);
        member8 = repository.save(member8);
        member9= repository.save(member9);
        member10 = repository.save(member10);

        db = Arrays.asList(member1, member2, member3, member4, member5, member6, member7, member8, member9, member10);
    }

    @Test
    public void test() throws Exception {
        /* arrange */
        QMember qMember = QMember.member;
        qMember.name.like("%수");

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.name.like("%수%"));

        Pageable pageable = PageRequest
                .of(0, 10, Sort.by("name").ascending().and(Sort.by("score").descending()));

        /* act */
        Page<Member> memberPage = repository.findAll(builder, pageable);

        /* assert */
        List<Member> actualMemberList = memberPage.getContent();

        assertThat(actualMemberList).hasSize(5)
            .extracting("name", "score", "email")
            .contains(
                    tuple("김민수", 77, "min@min.min"),
                    tuple("김민수", 77, "min@min.min"),
                    tuple("김병수", 100, "kbs0711@humuson.com"),
                    tuple("김병수", 10, "kim@kim.kim"),
                    tuple("최수빈", 25, "csb@csb.csb"),
                    tuple("최민수", 100, "choi@choi.choi")
            );

        List<Member> expectedSortedList = db.stream()
                .filter(m -> m.getName().contains("수"))
                .sorted(Comparator.comparing(Member::getName)
                    .thenComparing(Member::getScore, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Assertions.assertIterableEquals(expectedSortedList, actualMemberList);
    }
}
