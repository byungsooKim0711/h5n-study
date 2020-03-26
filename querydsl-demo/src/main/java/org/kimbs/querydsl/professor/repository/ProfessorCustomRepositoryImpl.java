package org.kimbs.querydsl.professor.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
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

    @Override
    public long updateProfessorPositionByYear(int yearEmp, String departmentName, String position) {
        return update(professor)
                .set(professor.position, new CaseBuilder().when(professor.yearEmp.loe(yearEmp)).then(position).otherwise(professor.position))
                .where(professor.id.in(
                        JPAExpressions
                                .select(professor.id)
                                .from(professor)
                                .join(department)
                                .on(professor.department.id.eq(department.id))
                                .where(department.name.eq(departmentName))
                        )
                )
                .execute();

    }
}
