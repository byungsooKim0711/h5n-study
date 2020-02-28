package org.kimbs.querydsl.student.repository;

import org.junit.jupiter.api.Test;
import org.kimbs.querydsl.department.domain.Department;
import org.kimbs.querydsl.department.repository.DepartmentRepository;
import org.kimbs.querydsl.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Test
    void test() {
        Department d1 = Department.builder()
                .name("컴퓨터공학과")
                .office("201호")
                .build();
        Department d2 = Department.builder()
                .name("산업공학과")
                .office("207호")
                .build();
        Department d3 = Department.builder()
                .name("전자공학과")
                .office("308호")
                .build();

        Student s1 = Student.builder()
                .name("김광식")
                .address("서울")
                .year(3)
                .build();

        Student s2 = Student.builder()
                .name("김정현")
                .address("서울")
                .year(3)
                .build();

    }
}
