package org.kimbs.uracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.uracker.config.UrackerConfig;
import org.kimbs.uracker.model.UrackerTarget;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.ByteBuffer;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrackerService {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final UrackerConfig config;

    private final ObjectMapper mapper;

    public String setTinyId(UrackerTarget target) throws JsonProcessingException, Exception {
        return null;
    }

    public UrackerTarget getUrackerTarget(String id) throws JsonProcessingException, Exception {
        return null;
    }

    private void sendToKafka(String topic, Object message) throws JsonProcessingException, Exception {
        final String data;

        try {
            data = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw e;
        }

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("Kafka send failed. topic: {}, data: {}", topic, data, e.getCause());
            }
        });
    }

    private String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }
}
