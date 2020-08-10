package com.humuson.imc.admin.web.user.service;

import com.humuson.imc.admin.web.domain.user.ApiInfoRepository;
import com.humuson.imc.admin.web.domain.user.ApiInfo;
import com.humuson.imc.admin.web.user.dto.ApiInfoConverter;
import com.humuson.imc.admin.web.user.dto.ApiInfoResponse;
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
