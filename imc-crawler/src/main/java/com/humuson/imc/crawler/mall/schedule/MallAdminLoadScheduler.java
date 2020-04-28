package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.MallAdmin;
import com.humuson.imc.crawler.service.MallAdminService;
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
public class MallAdminLoadScheduler {

    private final MallAdminService mallAdminService;
    private final ConcurrentHashMap<Long, MallAdmin> mallAdminMap;

    @PostConstruct
    public void init() {
        this.schedule();
    }


    @Scheduled(cron = "${imc.crawler.scheduled.load-mall-admin}")
    public void schedule() {
        List<MallAdmin> adminList = mallAdminService.findAll();
        log.info("[LOAD-MALL-ADMIN] TOTAL: {}, TIME: {}", adminList.size(), LocalDateTime.now());

        adminList.forEach(admin -> {
            mallAdminMap.put(admin.getId(), admin);
        });
    }

}
