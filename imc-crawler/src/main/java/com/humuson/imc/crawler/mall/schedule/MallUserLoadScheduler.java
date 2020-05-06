package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.MallUser;
import com.humuson.imc.crawler.service.MallUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MallUserLoadScheduler {

    private final MallUserService mallUserService;
    private final ConcurrentHashMap<Long, MallUser> mallUserMap;

    @PostConstruct
    public void init() {
        this.schedule();
    }

    @Scheduled(cron = "${imc.crawler.scheduled.load-mall-user}")
    public void schedule() {
        List<MallUser> userList = mallUserService.selectMallUserByWelcomeFlag("N");
        log.info("[LOAD-MALL-USER] TOTAL: {}, TIME: {}", userList.size(), LocalDateTime.now());

        userList.forEach(user -> {
            mallUserMap.put(user.getId(), user);
        });

    }
}
