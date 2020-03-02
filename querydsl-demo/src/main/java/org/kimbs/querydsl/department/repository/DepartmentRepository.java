package org.kimbs.querydsl.department.repository;

import org.kimbs.querydsl.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentCustomRepository {
}
