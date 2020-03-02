package org.kimbs.querydsl.student.repository;

import org.junit.jupiter.api.Test;
import org.kimbs.querydsl.clazz.domain.Clazz;
import org.kimbs.querydsl.clazz.domain.Course;
import org.kimbs.querydsl.department.domain.Department;
import org.kimbs.querydsl.department.repository.DepartmentRepository;
import org.kimbs.querydsl.professor.domain.Professor;
import org.kimbs.querydsl.student.domain.Student;
import org.kimbs.querydsl.takes.domain.Takes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Test
    void test() {
        Department d1 = Department.builder().name("컴퓨터공학과").office("201호").build();
        Department d2 = Department.builder().name("산업공학과").office("207호").build();
        Department d3 = Department.builder().name("전자공학과").office("308호").build();

        departmentRepository.saveAll(Arrays.asList(d1, d2, d3));

        Student s1 = Student.builder().name("김광식").address("서울").year(3).build();
        Student s2 = Student.builder().name("김정현").address("서울").year(3).build();
        Student s3 = Student.builder().name("김현정").address("대전").year(4).build();
        Student s4 = Student.builder().name("김현정").address("대구").year(2).build();
        Student s5 = Student.builder().name("박광수").address("광주").year(3).build();
        Student s6 = Student.builder().name("김우주").address("부산").year(4).build();
        Student s7 = Student.builder().name("박철수").address("대전").year(3).build();
        Student s8 = Student.builder().name("백태성").address("서울").year(3).build();

        s1.setDepartment(d1);
        s2.setDepartment(d1);
        s3.setDepartment(d1);
        s4.setDepartment(d2);
        s5.setDepartment(d2);
        s6.setDepartment(d2);
        s7.setDepartment(d3);
        s8.setDepartment(d3);

        studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8));

        Professor p1 = Professor.builder().name("이태규").position("교수").yearEmp(1997).build();
        Professor p2 = Professor.builder().name("고희석").position("부교수").yearEmp(2003).build();
        Professor p3 = Professor.builder().name("최성희").position("부교수").yearEmp(2005).build();
        Professor p4 = Professor.builder().name("김태석").position("교수").yearEmp(1999).build();
        Professor p5 = Professor.builder().name("박철재").position("조교수").yearEmp(2007).build();
        Professor p6 = Professor.builder().name("장민석").position("부교수").yearEmp(2005).build();

        Course course1 = Course.builder().title("전산개론").credit(3).build();
        Course course2 = Course.builder().title("자료구조").credit(3).build();
        Course course3 = Course.builder().title("운영체제").credit(3).build();
        Course course4 = Course.builder().title("이산수학").credit(4).build();
        Course course5 = Course.builder().title("인공지능").credit(3).build();
        Course course6 = Course.builder().title("알고리즘").credit(2).build();
        Course course7 = Course.builder().title("데이타베이스").credit(4).build();
        Course course8 = Course.builder().title("컴퓨터구조").credit(3).build();
        Course course9 = Course.builder().title("객체지향언어").credit(4).build();

        Clazz clazz1 = Clazz.builder().semester(2012).semester(1).division("A").classRoom("301호").enroll(40).build();
        Clazz clazz2 = Clazz.builder().semester(2012).semester(1).division("A").classRoom("209호").enroll(30).build();
        Clazz clazz3 = Clazz.builder().semester(2012).semester(1).division("A").classRoom("208호").enroll(30).build();
        Clazz clazz4 = Clazz.builder().semester(2012).semester(1).division("B").classRoom("301호").enroll(30).build();
        Clazz clazz5 = Clazz.builder().semester(2012).semester(1).division("A").classRoom("103호").enroll(45).build();
        Clazz clazz6 = Clazz.builder().semester(2012).semester(2).division("B").classRoom("204호").enroll(25).build();
        Clazz clazz7 = Clazz.builder().semester(2012).semester(2).division("A").classRoom("301호").enroll(30).build();
        Clazz clazz8 = Clazz.builder().semester(2012).semester(2).division("A").classRoom("209호").enroll(45).build();
        Clazz clazz9 = Clazz.builder().semester(2012).semester(2).division("A").classRoom("209호").enroll(30).build();
        Clazz clazz10 = Clazz.builder().semester(2012).semester(2).division("B").classRoom("103호").enroll(26).build();

        Takes t1 = Takes.builder().build();
        Takes t2 = Takes.builder().build();
        Takes t3 = Takes.builder().build();
        Takes t4 = Takes.builder().build();
        Takes t5 = Takes.builder().build();
        Takes t6 = Takes.builder().build();
        Takes t7 = Takes.builder().build();
        Takes t8 = Takes.builder().build();
        Takes t9 = Takes.builder().build();
        Takes t10 = Takes.builder().build();
        Takes t11 = Takes.builder().build();
        Takes t12 = Takes.builder().build();
    }
}
