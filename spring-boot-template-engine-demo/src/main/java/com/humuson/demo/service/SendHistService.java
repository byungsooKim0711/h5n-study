package com.humuson.demo.service;

import com.humuson.demo.domain.SendHist;
import com.humuson.demo.repository.SendHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendHistService {

    private final SendHistRepository sendHistRepository;

    public Page<SendHist> findAll(Pageable pageable) {
        return sendHistRepository.findAll(pageable);
    }
}
