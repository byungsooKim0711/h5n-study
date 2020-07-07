package com.humuson.imc.store.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humuson.imc.store.domain.ApiInfo;
import com.humuson.imc.store.repository.ApiInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true
)
@ConditionalOnExpression(value = "${imc.relay.scheduled.enable-redis-store:false}")
public class RedisStoreApiInfoSchedule {

    private final ApiInfoRepository apiInfoRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper mapper;

    private static final String KEY = "ImcApiInfo";

    private final ConcurrentHashMap<String, ApiInfo> apiMap = new ConcurrentHashMap<>();

    public RedisStoreApiInfoSchedule(ApiInfoRepository apiInfoRepository, StringRedisTemplate stringRedisTemplate, ObjectMapper mapper) {
        this.apiInfoRepository = apiInfoRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.mapper = mapper;
    }

    @PostConstruct
    void init() {
        this.storeApiInfo();
    }

    @Scheduled(cron = "${imc.relay.scheduled.redis-store-api-info}")
    public void storeApiInfo() {
        long total = 0;

        LocalDateTime startDate = LocalDateTime.now();

        int pageSize = 500;
        int page = 0;

        while (true) {
            Page<ApiInfo> apiInfoPage = apiInfoRepository.findAll(PageRequest.of(page, pageSize));
            List<ApiInfo> apiInfoList = apiInfoPage.getContent();

            if (CollectionUtils.isEmpty(apiInfoList)) {
                break;
            }

            total += apiInfoList.size();

            for (ApiInfo apiInfo : apiInfoList) {
                apiMap.put(apiInfo.getApiKey(), apiInfo);
                save(apiInfo.getApiKey(), apiInfo);
            }

            page++;
        }

        log.info("[Redis-Store] api-info. total: {}, elapsed time: {}", total, Duration.between(startDate, LocalDateTime.now()));

        if (log.isDebugEnabled()) {
            int row = 0;
            Map<String, ApiInfo> apiMap = findAll();
            for (String hashKey : apiMap.keySet()) {
                log.debug("redis api-info. row: {}, key: {}, value: {}", ++row, hashKey, apiMap.get(hashKey));
            }
        }
    }

    public void save(String apiKey, ApiInfo apiInfo) {
        try {
            stringRedisTemplate.opsForHash().put(KEY, apiKey, mapper.writeValueAsString(apiInfo));
        } catch (Exception e) {
            log.warn("Redis save exception.", e);
        }
    }

    public long findApiId(String apiKey) {
        ApiInfo apiInfo = apiMap.get(apiKey);

        if (apiInfo != null) {
            return apiInfo.getId();
        }

        return -1;
    }

    public Map<String, ApiInfo> findAll() {
        Map<String, ApiInfo> map = new HashMap<>();
        Map<Object, Object> findMap = stringRedisTemplate.opsForHash().entries(KEY);

        if (CollectionUtils.isEmpty(findMap)) {
            for (Object apiKey : findMap.keySet()) {
                map.put((String) apiKey, convertToObject((String) findMap.get(apiKey)));
            }
        }

        return map;
    }

    public ApiInfo convertToObject(String value) {
        try {
            return mapper.readValue(value, ApiInfo.class);
        } catch (Exception e) {
            return null;
        }
    }
}
