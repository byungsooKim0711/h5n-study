package org.kimbs.uracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.uracker.config.UrackerConfig;
import org.kimbs.uracker.model.UrackerTarget;
import org.kimbs.uracker.util.CommonGenerator;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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
        ObjectMapper mapper = new ObjectMapper();


        if (target.getTtl() <= 0 || target.getTtl() > config.getTtl()) {
            target.setTtl(config.getTtl());
        }

        target.setCreated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        target.setTinyId(this.shortUUID());

        String json = mapper.writeValueAsString(target);

        reactiveStringRedisTemplate.opsForValue().set(target.getTinyId(), json, Duration.ofSeconds(target.getTtl()));

        // hist
        sendToKafka(CommonGenerator.getRoundRobinListValue(CommonGenerator.GeneratorKey.TARGET, config.getTopics().getHistTarget()).orElse("DEFAULT"), target);

        return target.getTinyId();
    }

    public UrackerTarget getUrackerTarget(String id) throws JsonProcessingException, Exception {
        UrackerTarget target = null;

//        CommonReport commonReport = null;

        Map<String, Object> userMap = null;

        Mono<String> json = reactiveStringRedisTemplate.opsForValue().get(id);

//        if (!StringUtils.isEmpty(json)) {
//            target = mapper.readValue(json, UrackerTarget.class);
//            target.setClick(DateTime.now().toString(ImcDateUtils.DATE_TIME_FORMAT));
//        }

//        if (target != null) {
//            userMap = new HashMap<>();
//            userMap.put(ReportType.UT.name(), target);
//
//            commonReport = new CommonReport(target.getAgentId(), target.getSn());
//            commonReport.setReportType(ReportType.UT);
//            commonReport.setUserMap(userMap);
//
//            // report
//            sendToKafka(
//                    CommonGenerator.getRoundRobinListValue(GeneratorKey.REPORT, config.getTopics().getReport()),
//                    commonReport);
//
//            // hist
//            sendToKafka(
//                    CommonGenerator.getRoundRobinListValue(GeneratorKey.TARGET, config.getTopics().getHistTarget()),
//                    target);
//        }

        return target;
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
