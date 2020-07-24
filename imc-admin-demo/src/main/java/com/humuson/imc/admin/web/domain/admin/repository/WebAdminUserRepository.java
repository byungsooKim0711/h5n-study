package com.humuson.imc.admin.web.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebAdminUserRepository extends JpaRepository<WebAdminUser, Long>, WebAdminUserCustomRepository {

    Optional<WebAdminUser> findByUserLogin(String userLogin);
}
