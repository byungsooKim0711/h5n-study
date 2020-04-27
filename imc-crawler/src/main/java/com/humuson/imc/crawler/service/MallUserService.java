package com.humuson.imc.crawler.service;

import com.humuson.imc.crawler.repository.MallUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MallUserService {

    private final MallUserRepository mallUserRepository;
}
