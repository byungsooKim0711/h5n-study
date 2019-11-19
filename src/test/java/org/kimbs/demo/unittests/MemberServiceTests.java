package org.kimbs.demo.unittests;

import org.junit.jupiter.api.*;
import org.kimbs.demo.exception.MemberNotFoundException;
import org.kimbs.demo.model.Member;
import org.kimbs.demo.repository.MemberRepository;
import org.kimbs.demo.service.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MemberServiceTests {

    @Mock
    private MemberRepository repository;

    @InjectMocks
    private MemberService service;

    @BeforeAll
    public static void setUp() {
        // TODO: 제일 처음 초기화 되어야 하는 내용 작성 ( 최초 1번 실행 )
    }

    @BeforeEach
    public void init() {
        // TODO: 테스트 메서드가 시작될 때 마다 초기화 해야하는 내용 작성 ( @Test 갯수만큼 실행 )
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        // TODO: 테스트 메서드가 종료될 때 마다 해제되어야 하는 내용 작성 ( @Test 갯수만큼 실행 )
    }

    @AfterAll
    public static void cleanUp() {
        // TODO: 모든 테스트 메서드가 종료되고 난 후에 자원 해제 ( 마지막 1번 실행 )
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회할 경우 예외 발생 테스트")
    public void testThrowMemberNotFoundException() {
        /* arrange */

        /* act */

        /* assert */
        assertThrows(MemberNotFoundException.class, () -> {
            service.findById(1234L);
        });
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 findAll() 테스트")
    public void testFindAllMember() {
        /* arrange */
        Member member = new Member();
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        List<Member> list = Arrays.asList(member);

        when(repository.findAll()).thenReturn(list);

        /* act */
        List<Member> actual = service.findAll();

        /* assert */
        assertEquals(1, actual.size());
        assertEquals("김병수", actual.get(0).getName());
        assertEquals("kbs0711@humuson.com", actual.get(0).getEmail());

        assertIterableEquals(list, actual);
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 findById(Long) 테스트")
    public void testFindById() {
        /* arrange */
        Member member = new Member();
        member.setId(1234L);
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        when(repository.findById(member.getId())).thenReturn(Optional.of(member));

        /* act */
        Member actual = service.findById(1234L);

        /* assert */
        assertEquals(member, actual);
    }

    @Test
    @DisplayName("멤버 서비스를 이용한 updateById(Long, Member) 테스트")
    public void testUpdateById() {
        /* arrange */
        Member member = new Member();
        member.setId(1234L);
        member.setScore(100);
        member.setName("김병수");
        member.setEmail("kbs0711@humuson.com");

        when(repository.saveAndFlush(member)).thenReturn(member);

        member.setEmail("modify@humuson.com");

        when(repository.findById(member.getId())).thenReturn(Optional.of(member));

        /* act */
        Member actual = service.updateById(member.getId(), member);

        /* assert */
        assertEquals(member, actual);
        assertEquals(member.getScore(), actual.getScore());
    }
}
