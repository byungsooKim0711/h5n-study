package com.humuson.imc.admin.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAdminUserRepository extends JpaRepository<WebAdminUser, Long>, WebAdminUserCustomRepository {

}
