package com.humuson.imc.admin.web.domain.user.service;

import com.humuson.imc.admin.web.domain.user.repository.ApiInfoRepository;
import com.humuson.imc.admin.web.domain.user.dto.ApiInfoConverter;
import com.humuson.imc.admin.web.domain.user.dto.ApiInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiInfoService {

    private final ApiInfoRepository apiInfoRepository;
    private final ApiInfoConverter apiInfoConverter;

    public List<ApiInfoResponse> getApiInfoList() {
        return apiInfoConverter.toDto(apiInfoRepository.findAll());
    }
}
