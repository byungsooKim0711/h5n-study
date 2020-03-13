package org.kimbs.querydsl.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kimbs.querydsl.student.domain.Student;
import org.kimbs.querydsl.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BaseTimeEntityTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("JPA Audition Test")
    void test() throws Exception {
        // assert

        // act
        List<Student> actual = studentRepository.findAll();

        // assert
        assertThat(actual.get(0).getModifiedDate()).isNotNull();
    }
}
