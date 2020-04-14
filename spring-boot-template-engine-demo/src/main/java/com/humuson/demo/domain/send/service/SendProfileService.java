package com.humuson.demo.domain.send.service;

import com.humuson.demo.domain.send.SendProfile;
import com.humuson.demo.domain.send.repository.SendProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendProfileService {

    private final SendProfileRepository sendProfileRepository;

    public List<SendProfile> findAll() {
        return sendProfileRepository.findAll();
    }

}
