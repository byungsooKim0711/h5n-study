package com.humuson.imc.crawler.repository.impl;

import com.humuson.imc.crawler.model.MallUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MallUserCustomRepositoryImpl extends QuerydslRepositorySupport implements MallUserCustomRepository {

    public MallUserCustomRepositoryImpl() {
        super(MallUser.class);
    }
}
