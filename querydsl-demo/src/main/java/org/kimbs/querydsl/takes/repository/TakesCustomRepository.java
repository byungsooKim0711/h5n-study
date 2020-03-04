package org.kimbs.querydsl.takes.repository;

import org.kimbs.querydsl.takes.dto.AverageGradeByStudent;
import org.kimbs.querydsl.takes.dto.StudentGradesByYear;

import java.util.List;

public interface TakesCustomRepository {

    public List<StudentGradesByYear> getStudentGradesByYearAndConvertTakesGrade(int year);

    public List<AverageGradeByStudent> getAverageGradeByStudent();
}
