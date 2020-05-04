package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.MallUser;
import com.humuson.imc.crawler.repository.impl.MallUserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MallUserRepository extends JpaRepository<MallUser, Long>, MallUserCustomRepository {
}
