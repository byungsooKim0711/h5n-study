package org.kimbs.querydsl.takes.repository;

import org.kimbs.querydsl.takes.domain.Takes;

import java.util.List;

public interface TakesCustomRepository {

    public List<Takes> convertTakesGrade();
}
