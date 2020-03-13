package org.kimbs.querydsl.professor.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kimbs.querydsl.professor.domain.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
public class ProfessorRepositoryTests {

    @Autowired
    ProfessorRepository professorRepository;

    @Test
    @DisplayName("특정 학과에 속한 교수들 찾기 (교수배정이 1명 이상 되어있는 경우)")
    void test() throws Exception {
        // arrange

        // act
        List<Professor> actual = professorRepository.findProfessorByDepartmentName("전자공학과");

        // assert
        assertThat(actual).hasSize(2)
                .extracting("name", "position", "yearEmp")
                .contains(
                        tuple("박철재", "조교수", 2007),
                        tuple("장민석", "부교수", 2005)
                );
    }

    @Test
    @DisplayName("특정 학과에 속한 교수들 찾기 (신설학과여서 교수배정이 안되었을 경우)")
    void test1() throws Exception {
        // arrange

        // act
        List<Professor> actual = professorRepository.findProfessorByDepartmentName("응용통계학과");

        // assert
        Assertions.assertEquals(0, actual.size());
    }
}
