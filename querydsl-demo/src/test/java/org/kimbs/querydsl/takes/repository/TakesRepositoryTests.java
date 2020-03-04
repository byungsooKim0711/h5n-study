package org.kimbs.querydsl.takes.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.kimbs.querydsl.takes.dto.StudentGradesByYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TakesRepositoryTests {

    @Autowired
    TakesRepository takesRepository;

    @Test
    void test() throws Exception {
        // arrange

        // act
        List<AverageGradeByStudent> actual = takesRepository.getAverageGradeByStudent();

        // assert
        assertThat(actual).hasSize(5);
    }

    @Test
    void test1() throws Exception {
        // arrange

        // act
        List<StudentGradesByYear> actual = takesRepository.getStudentGradesByYearAndConvertTakesGrade(2020);

        // assert
        assertThat(actual).hasSize(12);
    }
}
