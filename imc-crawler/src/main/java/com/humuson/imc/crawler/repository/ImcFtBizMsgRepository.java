package com.humuson.imc.crawler.repository;

import com.humuson.imc.crawler.model.ImcFtBizMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImcFtBizMsgRepository extends JpaRepository<ImcFtBizMsg, Long> {
}
