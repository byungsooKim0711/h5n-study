package org.kimbs.querydsl.takes.repository;

import org.kimbs.querydsl.takes.domain.Takes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakesRepository extends JpaRepository<Takes, Long> {
}
