package org.kimbs.imc.admin.web.user;

import org.kimbs.imc.admin.domain.WebUser;
import org.kimbs.imc.admin.domain.WebUserRepository;
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
