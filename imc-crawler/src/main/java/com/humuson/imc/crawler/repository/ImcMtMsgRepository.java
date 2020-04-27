package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.ImcMtMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImcMtMsgRepository extends JpaRepository<ImcMtMsg, Long> {
}
