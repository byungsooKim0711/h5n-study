package com.humuson.imc.admin.web.user;

import com.humuson.imc.admin.domain.ApiInfoRepository;
import com.humuson.imc.admin.domain.ApiInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiInfoService {

    private final ApiInfoRepository apiInfoRepository;

    public ApiInfoService(ApiInfoRepository apiInfoRepository) {
        this.apiInfoRepository = apiInfoRepository;
    }

    public List<ApiInfo> getApiInfoList() {
        return apiInfoRepository.findAll();
    }
}
