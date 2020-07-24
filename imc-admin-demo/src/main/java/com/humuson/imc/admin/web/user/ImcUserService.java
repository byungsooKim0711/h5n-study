package com.humuson.imc.admin.web.user;

import com.humuson.imc.admin.web.domain.user.WebUser;
import com.humuson.imc.admin.web.domain.user.WebUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImcUserService {

    private final WebUserRepository webUserRepository;

    public ImcUserService(WebUserRepository webUserRepository) {
        this.webUserRepository = webUserRepository;
    }

    public List<WebUser> getWebUserList() {
        return webUserRepository.findAll();
    }
}
