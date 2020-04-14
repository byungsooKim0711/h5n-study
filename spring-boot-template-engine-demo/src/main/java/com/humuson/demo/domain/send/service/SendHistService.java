package com.humuson.demo.domain.send.service;

import com.humuson.demo.domain.send.dto.BizHistDto;
import com.humuson.demo.domain.send.repository.SendHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendHistService {

    private final SendHistRepository sendHistRepository;

    public List<BizHistDto> findSendHistByCompanyName(String companyName, Pageable pageable) {
        return sendHistRepository.findSendHistByCompanyName(companyName, pageable);
    }
}
