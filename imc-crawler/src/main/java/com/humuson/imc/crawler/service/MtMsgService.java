package com.humuson.imc.crawler.service;

import com.humuson.imc.crawler.model.ImcMtMsg;
import com.humuson.imc.crawler.repository.ImcMtMsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MtMsgService {

    private final ImcMtMsgRepository imcMtMsgRepository;

    @Transactional
    public ImcMtMsg save(ImcMtMsg message) {
        return imcMtMsgRepository.save(message);
    }

    @Transactional
    public List<ImcMtMsg> saveAll(Iterable<ImcMtMsg> messages) {
        return imcMtMsgRepository.saveAll(messages);
    }
}
