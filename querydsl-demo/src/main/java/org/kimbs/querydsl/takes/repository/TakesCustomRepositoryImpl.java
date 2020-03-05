package org.kimbs.querydsl.takes.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import org.kimbs.querydsl.takes.domain.Takes;
import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.kimbs.querydsl.takes.dto.StudentGradesByYear;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static org.kimbs.querydsl.student.domain.QStudent.student;
import static org.kimbs.querydsl.takes.domain.QTakes.takes;
import static org.kimbs.querydsl.clazz.domain.QClazz.clazz;
import static org.kimbs.querydsl.clazz.domain.QCourse.course;

public class TakesCustomRepositoryImpl extends QuerydslRepositorySupport implements TakesCustomRepository {

    public TakesCustomRepositoryImpl() {
        super(Takes.class);
    }

    // 입력 년도의 학생들의 성적 리스트 반환
    @Override
    public List<StudentGradesByYear> getStudentGradesByYearAndConvertTakesGrade(int year) {
        return from(takes)
                .select(
                        Projections.fields(StudentGradesByYear.class,
                                takes.student.name.as("studentName"),
                                takes.clazz.course.title.as("courseTitle"),
                                takes.clazz.year.as("classOpenYear"),
                                new CaseBuilder()
                                        .when(takes.grade.goe(95)).then("A+")
                                        .when(takes.grade.goe(90)).then("A")
                                        .when(takes.grade.goe(85)).then("B+")
                                        .when(takes.grade.goe(80)).then("B")
                                        .when(takes.grade.goe(75)).then("C+")
                                        .when(takes.grade.goe(70)).then("C")
                                        .when(takes.grade.goe(65)).then("D+")
                                        .when(takes.grade.goe(60)).then("D")
                                        .otherwise("F").as("grade")
                        )
                )
                .innerJoin(clazz).on(clazz.id.eq(takes.clazz.id))
                .innerJoin(student).on(student.id.eq(takes.student.id))
                .innerJoin(course).on(clazz.course.id.eq(course.id))
                .where(clazz.year.eq(year))

                .fetch();
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
                        Projections.fields(AverageGradeByStudent.class,
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
