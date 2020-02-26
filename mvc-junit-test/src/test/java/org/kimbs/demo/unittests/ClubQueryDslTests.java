package org.kimbs.demo.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.demo.model.Club;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClubQueryDslTests {

    @Autowired
    private ClubRepository clubRepository;

    @BeforeEach
    public void setUp() throws Exception {
        Club pingPong = new Club();
        pingPong.setId(1L);
        pingPong.setName("탁구");
        pingPong.setTelephoneNumber("01-234-5678");
        pingPong.setOffice("탁구장");

        Member member1 = new Member();
        member1.setScore(1);
        member1.setEmail("1@1.1");
        member1.setName("1");
        member1.setClubId(pingPong.getId());

        Member member2 = new Member();
        member2.setScore(2);
        member2.setEmail("2@2.2");
        member2.setName("2");
        member2.setClubId(pingPong.getId());

        pingPong.setMembers(Arrays.asList(member1, member2));

        Club tennis = new Club();
        tennis.setId(2L);
        tennis.setOffice("테니스장");
        tennis.setTelephoneNumber("012-345-6789");
        tennis.setName("테니스");

        clubRepository.saveAll(Arrays.asList(pingPong, tennis));
    }

    @Test
    public void testFindAllByMemberId() throws Exception {
        // arrange

        // act
        Club actual = clubRepository.findAllByMemberId(1L).get();

        // assert
        assertEquals("탁구", actual.getName());
        assertEquals("01-234-5678", actual.getTelephoneNumber());
        assertEquals("탁구장", actual.getOffice());

        assertEquals(2, actual.getMembers().size());
    }

    @Test
    public void test() throws Exception {
        // arrange

        // act
        List<Club> a = clubRepository.findAll();
        for(Club c : a) {
            System.out.println(c);
        }

        // assert
    }
}
