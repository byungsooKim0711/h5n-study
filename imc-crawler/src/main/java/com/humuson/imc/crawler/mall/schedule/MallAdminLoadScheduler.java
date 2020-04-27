package com.humuson.imc.crawler.mall.schedule;

import com.humuson.imc.crawler.model.MallAdmin;
import com.humuson.imc.crawler.service.MallAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    @Scheduled(cron = "10 * 0/1 * * *")
    public void schedule() {
        List<MallAdmin> adminList = mallAdminService.findAll();

        adminList.forEach(admin -> {
            mallAdminMap.put(admin.getId(), admin);
        });
    }

}
