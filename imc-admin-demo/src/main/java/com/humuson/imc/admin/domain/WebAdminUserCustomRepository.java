package com.humuson.imc.admin.domain;

import java.util.Optional;

public interface WebAdminUserCustomRepository {

    long updateWebAdminUser(WebAdminUser webAdminUser, Long id);
    Optional<WebAdminUser> findByUserLogin(String s);
}
