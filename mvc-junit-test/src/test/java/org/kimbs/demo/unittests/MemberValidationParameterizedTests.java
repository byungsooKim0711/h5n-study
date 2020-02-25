package org.kimbs.demo.unittests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kimbs.demo.model.Member;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MemberValidationParameterizedTests {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("멤버 필드에 정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("normalMembers")
    void testNormalMembers(String name, String email, int score) {
        /* arrange */
        Member member = new Member();
        member.setId(Long.MAX_VALUE);
        member.setScore(score);
        member.setName(name);
        member.setEmail(email);

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(0, violations.size());
    }

    static Stream<Arguments> normalMembers() {
        return Stream.of(
                Arguments.of("Kim", "Kim@test.test", 100),
                Arguments.of("Lee", "Lee@test.test", 10),
                Arguments.of("Park", "Park@test.test", 77),
                Arguments.of("Choi", "Choi@test.test", 91),
                Arguments.of("Yang", "Yang@test.est", 11),
                Arguments.of("Test", "Test@test.est", 0)
        );
    }

    @DisplayName("멤버 필드에 비정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("abnormalMembers")
    void testAbnormalMembers(Member member) {
        /* arrange */

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertTrue(violations.size() > 0);
    }

    static Stream<Arguments> abnormalMembers() {
        Member m1 = new Member();
        m1.setId(1L);
        m1.setName("Kim");
        m1.setEmail("Kim");
        m1.setScore(111111);

        Member m2 = new Member();
        m2.setId(2L);
        m2.setName("Lee");
        m2.setEmail("Lee@test.test");
        m2.setScore(9999);

        Member m3 = new Member();
        m3.setId(3L);
        m3.setName("Park");
        m3.setEmail("Park");
        m3.setScore(111111);

        Member m4 = new Member();
        m4.setId(4L);
        m4.setName("Choi");
        m4.setEmail("Choi@test.test");
        m4.setScore(1234567890);

        Member m5 = new Member();
        m5.setId(5L);
        m5.setName("Yang");
        m5.setEmail("YangTest.est");
        m5.setScore(1234567890);

        Member m6 = new Member();
        m6.setId(6L);
        m6.setName("Test");
        m6.setEmail("Test@test,est");
        m6.setScore(777777777);

        return Stream.of(
                Arguments.of(m1),
                Arguments.of(m2),
                Arguments.of(m3),
                Arguments.of(m4),
                Arguments.of(m5),
                Arguments.of(m6)
        );
    }

    @DisplayName("멤버 이메일 필드에 비정상적인 값만 있을 경우 테스트")
    @ParameterizedTest(name = "#{index}: (name: {0}, email: {1}, score: {2})")
    @MethodSource("wrongEmailFormatMembers")
    void testWrongEmailFormatMembers(Member member) {
        /* arrange */

        /* act */
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        /* assert */
        assertEquals(1, violations.size());

        String actual = validator.validateProperty(member, "email").iterator().next().getMessage();
        String expected = "Wrong email format";
        assertEquals(expected, actual);
    }

    static Stream<Arguments> wrongEmailFormatMembers() {
        Member m1 = new Member();
        m1.setId(1L);
        m1.setName("Kim");
        m1.setEmail("Kim");
        m1.setScore(100);

        Member m2 = new Member();
        m2.setId(2L);
        m2.setName("Lee");
        m2.setEmail("Lee@test,test");
        m2.setScore(100);
        return Stream.of(
                Arguments.of(m1),
                Arguments.of(m2)
        );
    }
}
