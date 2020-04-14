package com.humuson.demo.domain.send.repository;

import com.humuson.demo.domain.send.SendProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendProfileRepository extends JpaRepository<SendProfile, Long> {
}
