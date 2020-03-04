package org.kimbs.querydsl.clazz.repository;

import org.kimbs.querydsl.clazz.domain.Clazz;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ClazzCustomRepositoryImpl extends QuerydslRepositorySupport implements ClazzCustomRepository {

    public ClazzCustomRepositoryImpl() {
        super(Clazz.class);
    }
}
