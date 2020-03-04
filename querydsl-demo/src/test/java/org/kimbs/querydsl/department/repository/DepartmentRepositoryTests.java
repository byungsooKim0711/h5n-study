package org.kimbs.querydsl.department.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.department.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

        actual.forEach(d -> System.out.println("ID=" + d.getId() + " NAME=" + d.getName() + " OFFICE=" + d.getOffice() + " STUDENTS=" + d.getStudents()));

        // assert
    }
}
