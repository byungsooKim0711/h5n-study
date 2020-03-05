package org.kimbs.querydsl.student.repository;

import org.kimbs.querydsl.student.domain.Student;

import java.util.List;

public interface StudentCustomRepository {

    List<Student> findStudentNameAndAddressAndYearDynamicQuery(String name, String address, Integer year);

    List<Student> findStudentByUnassignedDepartment();
}
