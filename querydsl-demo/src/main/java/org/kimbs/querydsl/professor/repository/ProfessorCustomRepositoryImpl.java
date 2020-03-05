package org.kimbs.querydsl.professor.repository;

import com.querydsl.jpa.JPAExpressions;
import org.kimbs.querydsl.professor.domain.Professor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static org.kimbs.querydsl.professor.domain.QProfessor.professor;
import static org.kimbs.querydsl.department.domain.QDepartment.department;

public class ProfessorCustomRepositoryImpl extends QuerydslRepositorySupport implements ProfessorCustomRepository {

    public ProfessorCustomRepositoryImpl() {
        super(Professor.class);
    }

    @Override
    public List<Professor> findProfessorByDepartmentName(String departmentName) {
        return from(professor)
                .where(professor.department.id.in(
                        JPAExpressions
                        .select(department.id)
                        .from(department)
                        .where(department.name.eq(departmentName))
                ))
                .fetch();
    }
}
