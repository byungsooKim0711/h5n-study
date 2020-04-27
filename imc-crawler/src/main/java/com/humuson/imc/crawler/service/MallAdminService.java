package com.humuson.imc.crawler.service;

import com.humuson.imc.crawler.model.MallAdmin;
import com.humuson.imc.crawler.repository.MallAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MallAdminService {

    private final MallAdminRepository mallAdminRepository;

    public List<MallAdmin> findAll() {
        return mallAdminRepository.findAll();
    }
}
