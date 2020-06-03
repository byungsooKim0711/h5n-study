package org.kimbs.imc.admin.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebAdminUserRepository extends JpaRepository<WebAdminUser, Long> {
    Optional<WebAdminUser> findByUserLogin(String userLogin);
}
