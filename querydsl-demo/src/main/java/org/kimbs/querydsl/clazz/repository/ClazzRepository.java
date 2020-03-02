package org.kimbs.querydsl.clazz.repository;

import org.kimbs.querydsl.clazz.domain.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {
}
