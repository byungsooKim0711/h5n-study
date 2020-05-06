package com.humuson.imc.crawler.service;

import com.humuson.imc.crawler.model.MallUser;
import com.humuson.imc.crawler.repository.MallUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MallUserService {

    private final MallUserRepository mallUserRepository;

    public List<MallUser> selectMallUserByWelcomeFlag(String welcomeFlag) {
        return this.mallUserRepository.selectMallUserByWelcomeFlag(welcomeFlag);
    }
}
