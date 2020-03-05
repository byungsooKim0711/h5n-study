package org.kimbs.querydsl.student.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("이름이 김현정인 학생들 찾기")
    void test() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentNameAndAddressAndYearDynamicQuery("김현정", "", null);

        // assert
        assertThat(actual).hasSize(2).extracting(Student::getName).containsOnly("김현정");
    }

    @Test
    @DisplayName("이름이 김현정이고 대구사는 학생들 찾기")
    void test1() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentNameAndAddressAndYearDynamicQuery("김현정", "대구", null);

        // assert
        Student student = actual.get(0);

        assertEquals("김현정", student.getName());
        assertEquals("대구", student.getAddress());
        assertEquals(2, student.getYear());
    }

    @Test
    @DisplayName("4학년인 학생들 찾기")
    void test2() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentNameAndAddressAndYearDynamicQuery(null, null, 4);

        // assert
        assertEquals(3, actual.size());
    }

    @Test
    @DisplayName("학과가 아직 정해지지 않은 학생들 찾기")
    void test3() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentByUnassignedDepartment();

        // assert
        assertThat(actual).hasSize(1)
                .extracting("name", "address", "year", "department")
                .contains(tuple("김병수", "인천", 4, null));
    }
}
