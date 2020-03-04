package org.kimbs.querydsl.takes.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import org.kimbs.querydsl.takes.domain.Takes;
import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static org.kimbs.querydsl.student.domain.QStudent.student;
import static org.kimbs.querydsl.takes.domain.QTakes.takes;

public class TakesCustomRepositoryImpl extends QuerydslRepositorySupport implements TakesCustomRepository {

    public TakesCustomRepositoryImpl() {
        super(Takes.class);
    }

    @Override
    public List<Takes> convertTakesGrade() {
        return from(takes)
                .select(
                        Projections.fields(takes, new CaseBuilder()
                                .when(takes.grade.goe(95)).then("A+")
                                .when(takes.grade.goe(90)).then("A")
                                .when(takes.grade.goe(85)).then("B+")
                                .when(takes.grade.goe(80)).then("B")
                                .when(takes.grade.goe(75)).then("C+")
                                .when(takes.grade.goe(70)).then("C")
                                .when(takes.grade.goe(65)).then("D+")
                                .when(takes.grade.goe(60)).then("D")
                                .otherwise("F").as("takes"))
                ).fetch();
    }

    @Override
    public List<AverageGradeByStudent> getAverageGradeByStudent() {
//        return jpaQueryFactory
//                .select(Projections.bean(AverageGradeByStudent.class,
//                        ExpressionUtils.as(student.id, "studentId"),
//                        student.name.as("studentName"),
//                        takes.grade.avg().as("averageGrade")
//                ))
//                .from(student)
//                .join(takes)
//                .on(student.id.eq(takes.student.id))
//                .groupBy(takes.student.id)
//                .fetch();
        return from(takes)
                .select(
                        Projections.bean(AverageGradeByStudent.class,
                                ExpressionUtils.as(student.id, "studentId"),
                                ExpressionUtils.as(student.name, "studentName"),
                                ExpressionUtils.as(takes.grade.avg(), "averageGrade")
                        )
                )
                .join(student)
                .fetchJoin()
                .on(takes.student.id.eq(student.id))
                .groupBy(takes.student.id)
                .orderBy(takes.grade.avg().desc())
                .fetch();
    }
}
