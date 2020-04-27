package com.humuson.imc.crawler.service;

import com.humuson.imc.crawler.repository.MallAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MallAdminService {

    private final MallAdminRepository mallAdminRepository;
}
