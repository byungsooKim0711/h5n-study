package org.kimbs.querydsl.takes.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.kimbs.querydsl.takes.dto.StudentGradesByYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TakesRepositoryTests {

    @Autowired
    TakesRepository takesRepository;

    @Test
    @DisplayName("수강생들의 학점 평균 구하기")
    void test() throws Exception {
        // arrange

        // act
        List<AverageGradeByStudent> actual = takesRepository.getAverageGradeByStudent();

        // assert
        assertThat(actual).hasSize(5);
    }

    @Test
    @DisplayName("2020년도 수강 성적을 학생이름, 과목이름, 개설년도, 변환된 학점으로 조회하기")
    void test1() throws Exception {
        // arrange

        // act
        List<StudentGradesByYear> actual = takesRepository.getStudentGradesByYearAndConvertTakesGrade(2020);

        // assert
        assertThat(actual).hasSize(12);
    }
}
