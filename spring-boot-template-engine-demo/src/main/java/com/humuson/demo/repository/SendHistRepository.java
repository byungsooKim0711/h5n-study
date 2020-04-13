package com.humuson.demo.repository;

import com.humuson.demo.domain.SendHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendHistRepository extends JpaRepository<SendHist, Long>, SendHistCustomRepository {

}
