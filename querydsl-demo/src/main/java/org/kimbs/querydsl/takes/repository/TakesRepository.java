package org.kimbs.querydsl.takes.repository;

import org.kimbs.querydsl.takes.domain.Takes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakesRepository extends JpaRepository<Takes, Long>, TakesCustomRepository {
}
