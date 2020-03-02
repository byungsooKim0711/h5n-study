package org.kimbs.querydsl.department.repository;

import org.kimbs.querydsl.department.domain.Department;

import java.util.List;

public interface DepartmentCustomRepository {

    List<Department> findDepartmentFetchJoinStudent();
}
