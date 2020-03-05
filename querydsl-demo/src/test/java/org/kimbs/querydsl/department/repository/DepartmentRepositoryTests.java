package org.kimbs.querydsl.department.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.department.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DepartmentRepositoryTests {

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void test() throws Exception {
        // arrange

        // act
        List<Department> actual = departmentRepository.findDepartmentFetchJoinStudent();

        // assert
        Department d1 = actual.get(0);
        assertEquals("컴퓨터공학과", d1.getName());
        assertEquals(3, d1.getStudents().size());

        Department d2 = actual.get(1);
        assertEquals("산업공학과", d2.getName());
        assertEquals(3, d2.getStudents().size());

        Department d3 = actual.get(2);
        assertEquals("전자공학과", d3.getName());
        assertEquals(2, d3.getStudents().size());

        Department d4 = actual.get(3);
        assertEquals("응용통계학과", d4.getName());
        assertEquals(0, d4.getStudents().size());

    }
}
