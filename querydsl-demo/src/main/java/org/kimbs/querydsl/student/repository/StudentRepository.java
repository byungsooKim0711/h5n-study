package org.kimbs.querydsl.student.repository;

import org.kimbs.querydsl.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentCustomRepository {
}
