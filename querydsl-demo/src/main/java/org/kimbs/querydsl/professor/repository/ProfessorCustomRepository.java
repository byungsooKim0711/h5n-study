package org.kimbs.querydsl.professor.repository;

import org.kimbs.querydsl.professor.domain.Professor;

import java.util.List;

public interface ProfessorCustomRepository {

    List<Professor> findProfessorByDepartmentName(String departmentName);

    public long updateProfessorPositionByYear(int yearEmp, String departmentName, String position);
}
