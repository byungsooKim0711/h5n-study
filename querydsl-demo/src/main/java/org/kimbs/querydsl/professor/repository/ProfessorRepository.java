package org.kimbs.querydsl.professor.repository;

import org.kimbs.querydsl.professor.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
