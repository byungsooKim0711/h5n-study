package com.humuson.imc.crawler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    private final int SCHEDULE_MAX_POOL_SIZE = 15;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("IMC-CRAWLER-");
        taskScheduler.setPoolSize(this.SCHEDULE_MAX_POOL_SIZE);
        taskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }
}
