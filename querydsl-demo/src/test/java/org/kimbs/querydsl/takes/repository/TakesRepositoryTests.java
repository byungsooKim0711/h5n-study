package org.kimbs.querydsl.takes.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

        System.out.println(actual);

        // assert
    }
}
