package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.MallAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MallAdminRepository extends JpaRepository<MallAdmin, Long> {
}
