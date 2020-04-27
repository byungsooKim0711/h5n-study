package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.MallUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MallUserRepository extends JpaRepository<MallUser, Long> {
}
