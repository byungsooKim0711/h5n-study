package org.kimbs.querydsl.professor.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.professor.domain.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProfessorRepositoryTests {

    @Autowired
    ProfessorRepository professorRepository;

    @Test
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
    void test1() throws Exception {
        // arrange

        // act
        List<Professor> actual = professorRepository.findProfessorByDepartmentName("응용통계학과");

        // assert
        Assertions.assertEquals(0, actual.size());
    }
}
