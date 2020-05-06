package com.humuson.imc.crawler.repository.impl;

import com.humuson.imc.crawler.model.MallUser;

import java.util.List;

public interface MallUserCustomRepository {

    public List<MallUser> selectMallUserByWelcomeFlag(String welcomeFlag);
}
