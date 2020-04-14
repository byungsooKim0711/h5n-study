package com.humuson.demo.domain.send.repository;

import com.humuson.demo.domain.send.SendHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendHistRepository extends JpaRepository<SendHist, Long>, SendHistCustomRepository {

}
