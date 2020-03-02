package org.kimbs.querydsl.student.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kimbs.querydsl.department.domain.Department;
import org.kimbs.querydsl.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void test() {
        // arrange

        // act
        List<Department> actual = departmentRepository.findDepartmentFetchJoinStudent();

        actual.forEach(d -> System.out.println("ID=" + d.getId() + " NAME=" + d.getName() + " OFFICE=" + d.getOffice() + " STUDENTS=" + d.getStudents()));

        // assert
    }
}
