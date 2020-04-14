package com.humuson.demo.domain.send.repository;

import com.humuson.demo.domain.send.dto.BizHistDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SendHistCustomRepository {

    // Custom methods..
    List<BizHistDto> findSendHistByCompanyName(String companyName, Pageable pageable);
}
