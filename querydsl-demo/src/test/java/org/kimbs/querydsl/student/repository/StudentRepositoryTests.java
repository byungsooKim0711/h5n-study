package org.kimbs.querydsl.student.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void test() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentNameAndAddressAndYearDynamicQuery("김현정", "", null);

        // assert
        assertThat(actual).hasSize(2).extracting(Student::getName).containsOnly("김현정");
    }

    @Test
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
    void test2() throws Exception {
        // arrange

        // act
        List<Student> actual = studentRepository.findStudentNameAndAddressAndYearDynamicQuery(null, null, 4);

        // assert
        assertEquals(2, actual.size());
    }
}
