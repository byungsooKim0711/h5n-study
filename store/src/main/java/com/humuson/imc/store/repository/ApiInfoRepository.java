package com.humuson.imc.store.repository;

import com.humuson.imc.store.domain.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiInfoRepository extends JpaRepository<ApiInfo, Long> {
}
