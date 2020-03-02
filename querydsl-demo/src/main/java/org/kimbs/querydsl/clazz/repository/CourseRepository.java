package org.kimbs.querydsl.clazz.repository;

import org.kimbs.querydsl.clazz.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
