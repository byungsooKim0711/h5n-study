package org.kimbs.querydsl.department.repository;

import org.kimbs.querydsl.department.domain.Department;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static org.kimbs.querydsl.department.domain.QDepartment.department;
import static org.kimbs.querydsl.student.domain.QStudent.student;

public class DepartmentCustomRepositoryImpl extends QuerydslRepositorySupport implements DepartmentCustomRepository {

    public DepartmentCustomRepositoryImpl() {
        super(Department.class);
    }

    @Override
    public List<Department> findDepartmentFetchJoinStudent() {

        /*
            TODO: fetch join 을 했을 경우 중복으로 노출되는 데이터는 어쩔 수 없는 것인가?
         */
        return from(department)
                .leftJoin(department.students, student)
                .fetchJoin()
                .distinct()
                .fetch();
    }
}
