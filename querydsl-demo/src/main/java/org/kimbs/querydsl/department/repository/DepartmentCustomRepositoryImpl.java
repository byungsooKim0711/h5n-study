package org.kimbs.querydsl.department.repository;

import org.kimbs.querydsl.department.domain.Department;
import org.kimbs.querydsl.department.domain.QDepartment;
import org.kimbs.querydsl.student.domain.QStudent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class DepartmentCustomRepositoryImpl extends QuerydslRepositorySupport implements DepartmentCustomRepository {

    public DepartmentCustomRepositoryImpl() {
        super(Department.class);
    }

    @Override
    public List<Department> findDepartmentFetchJoinStudent() {
        QDepartment department = QDepartment.department;
        QStudent student = QStudent.student;

        // fetch join 이라는 것을 이렇게 쓰는 것인가..?
        // 중복 데이터 노출은 어떻게 해결하는 것인가..?
        return from(department)
                .leftJoin(department.students, student)
                .fetchJoin()
                .fetch();
    }
}
