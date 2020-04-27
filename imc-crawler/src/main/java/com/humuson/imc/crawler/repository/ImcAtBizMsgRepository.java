package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.ImcAtBizMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImcAtBizMsgRepository extends JpaRepository<ImcAtBizMsg, Long> {
}
