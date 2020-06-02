package org.kimbs.imc.admin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebAdminUserRepository extends JpaRepository<WebAdminUser, Long> {
    Optional<WebAdminUser> findByUserLogin(String userLogin);
}
