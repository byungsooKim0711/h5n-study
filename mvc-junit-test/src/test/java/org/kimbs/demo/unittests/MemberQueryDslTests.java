package org.kimbs.demo.unittests;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Club;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.model.QMember;
import org.kimbs.demo.repository.ClubRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberQueryDslTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    private List<Member> memberTable;

    @BeforeEach
    public void setUp() throws Exception {
        /* arrange */
        /* ---[S] 탁구 --- */
        Club pingPong = new Club();
        pingPong.setName("탁구");
        pingPong.setOffice("카프카 탁구장");
        pingPong.setTelephoneNumber("032-123-4567");
        pingPong = clubRepository.save(pingPong);

        Member member1 = new Member();
        member1.setScore(100);
        member1.setName("김병수");
        member1.setEmail("kbs0711@humuson.com");
        member1.setClubId(pingPong.getId());

        Member member2 = new Member();
        member2.setScore(77);
        member2.setName("김민수");
        member2.setEmail("min@min.min");
        member2.setClubId(pingPong.getId());

        Member member3 = new Member();
        member3.setScore(3);
        member3.setName("홍길동");
        member3.setEmail("honb@hong.hong");
        member3.setClubId(pingPong.getId());
        /* ---[E] 탁구 --- */

        /* ---[S] 테니스 --- */
        Club tennis = new Club();
        tennis.setName("테니스");
        tennis.setOffice("스프링 테니스장");
        tennis.setTelephoneNumber("02-456-7890");
        tennis = clubRepository.save(tennis);

        Member member4 = new Member();
        member4.setScore(10);
        member4.setName("김병수");
        member4.setEmail("kim@kim.kim");
        member4.setClubId(tennis.getId());

        Member member5 = new Member();
        member5.setScore(99);
        member5.setName("박창선");
        member5.setEmail("park@park.park");
        member5.setClubId(tennis.getId());

        Member member6 = new Member();
        member6.setScore(42);
        member6.setName("양진우");
        member6.setEmail("yang@yang.yang");
        member6.setClubId(tennis.getId());

        Member member7 = new Member();
        member7.setScore(11);
        member7.setName("김성광");
        member7.setEmail("ksg@ksg.ksg");
        member7.setClubId(tennis.getId());

        Member member8 = new Member();
        member8.setScore(80);
        member8.setName("이요섭");
        member8.setEmail("lee@lee.lee");
        member8.setClubId(tennis.getId());
        /* ---[E] 테니스 --- */

        /* ---[S] 동아리 없음 --- */
        Member member9 = new Member();
        member9.setScore(25);
        member9.setName("최수빈");
        member9.setEmail("csb@csb.csb");

        Member member10 = new Member();
        member10.setScore(100);
        member10.setName("최민수");
        member10.setEmail("choi@choi.choi");
        /* ---[E] 동아리 없음 --- */

        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member4 = memberRepository.save(member4);
        member5 = memberRepository.save(member5);
        member6 = memberRepository.save(member6);
        member7 = memberRepository.save(member7);
        member8 = memberRepository.save(member8);
        member9= memberRepository.save(member9);
        member10 = memberRepository.save(member10);

        memberTable = Arrays.asList(member1, member2, member3, member4, member5, member6, member7, member8, member9, member10);
    }

    @Test
    public void testQueryDslExample() throws Exception {
        /* arrange */
        QMember qMember = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.name.like("%수%"));

        Pageable pageable = PageRequest
                .of(0, 10, Sort.by("name").ascending().and(Sort.by("score").descending()));

        /* act */
        Page<Member> memberPage = memberRepository.findAll(builder, pageable);

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

        List<Member> expectedSortedList = memberTable.stream()
                .filter(m -> m.getName().contains("수"))
                .sorted(Comparator.comparing(Member::getName)
                    .thenComparing(Member::getScore, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Assertions.assertIterableEquals(expectedSortedList, actualMemberList);
    }

    @Test
    public void testMemberCustomRepositoryFindMemberByEmail() throws Exception {
        // arrange

        // act
        List<Member> actual = memberRepository.findMemberByEmail("kbs0711@humuson.com");

        // assert
        assertThat(actual).hasSize(1).contains(memberTable.get(0));
    }

    @Test
    public void testMemberCustomRepositoryFindMemberByName() throws Exception {
        // arrange

        // act
        List<Member> actual = memberRepository.findMemberByName("김병수");

        // assert
        assertThat(actual).hasSize(2).contains(memberTable.get(0), memberTable.get(3));
    }

    @Test
    public void testMemberCustomRepositoryFindMemberByScoreGreaterThanEqual() throws Exception {
        // arrange

        // act
        List<Member> actual = memberRepository.findMemberByScoreGreaterThanEqual(50);

        // assert
        assertThat(actual).hasSize(5).contains(memberTable.get(0), memberTable.get(1), memberTable.get(4), memberTable.get(7), memberTable.get(9));
    }

    @Test
    public void testMemberCustomRepositoryFindMemberById() throws Exception {
        // arrange

        // act
        Optional<Member> actual = memberRepository.findMemberById(memberTable.get(0).getId());

        // assert
        Assertions.assertEquals(memberTable.get(0), actual.get());
    }

    @Test
    @DisplayName("이름에 수가 들어가는 인원들을 이름으로 오름처순 정렬, 같은 이름에 대해서는 점수로 내림차순 정렬")
    public void testContainingAndOrderBy() throws Exception {
        // arrange

        // act
        List<Member> actualMemberList = memberRepository.findMemberByNameContainingOrderByNameAscAndOrderByScoreDesc("수");

        // assert

        List<Member> expectedSortedList = memberTable.stream()
                .filter(m -> m.getName().contains("수"))
                .sorted(Comparator.comparing(Member::getName)
                        .thenComparing(Member::getScore, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        Assertions.assertIterableEquals(expectedSortedList, actualMemberList);
    }

    @Test
    @DisplayName("동아리에 가입된 인원들 추리기")
    public void testFindAllMemberInnerJoinClub() throws Exception {
        // arrange

        // act
        List<Member> actual = memberRepository.findAllMemberInnerJoinClub();

        // assert
        List<Member> expected = memberTable.stream().filter(m -> m.getClubId() != null).collect(Collectors.toList());

        assertThat(actual).hasSize(8);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testFindMemberByClubNameInnerJoinClub() throws Exception {
        // arrange

        // act
        List<Member> actual = memberRepository.findMemberByClubNameInnerJoinClub("탁구");

        // assert
        assertThat(actual).hasSize(3).contains(memberTable.get(0), memberTable.get(1), memberTable.get(2));
    }
}
