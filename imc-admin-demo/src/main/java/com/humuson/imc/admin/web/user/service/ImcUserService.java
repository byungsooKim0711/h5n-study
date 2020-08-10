package com.humuson.imc.admin.web.user.service;

import com.humuson.imc.admin.web.domain.user.WebUser;
import com.humuson.imc.admin.web.domain.user.WebUserRepository;
import com.humuson.imc.admin.web.user.dto.WebUserConverter;
import com.humuson.imc.admin.web.user.dto.WebUserDetailConverter;
import com.humuson.imc.admin.web.user.dto.WebUserDetailResponse;
import com.humuson.imc.admin.web.user.dto.WebUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImcUserService {

    private final WebUserRepository webUserRepository;
    private final WebUserConverter converter;
    private final WebUserDetailConverter detailConverter;

    public List<WebUserResponse> getWebUserList() {
        return converter.toDto(webUserRepository.findAll());
    }

    public WebUserDetailResponse getWebUserDetail(Long id) throws Exception {
        WebUser webUser = webUserRepository.findById(id).orElseThrow(() -> new Exception("Unknown web user id : " + id));

        return detailConverter.toDto(webUser);
    }
}
