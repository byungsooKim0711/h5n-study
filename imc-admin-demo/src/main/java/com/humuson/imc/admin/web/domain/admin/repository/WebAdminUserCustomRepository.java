package com.humuson.imc.admin.web.domain.admin.repository;

import java.util.Optional;

public interface WebAdminUserCustomRepository {

    Optional<WebAdminUser> findByUserLogin(String userLogin);

}
