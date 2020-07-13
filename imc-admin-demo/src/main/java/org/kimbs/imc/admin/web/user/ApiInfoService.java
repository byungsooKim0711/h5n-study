package org.kimbs.imc.admin.web.user;

import org.kimbs.imc.admin.domain.ApiInfo;
import org.kimbs.imc.admin.domain.ApiInfoRepository;
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
