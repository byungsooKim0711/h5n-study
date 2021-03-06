package com.humuson.imc.admin;

import com.humuson.imc.admin.config.ImcAdminConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableConfigurationProperties({ImcAdminConfig.class})
@SpringBootApplication
public class ImcAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImcAdminApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }

}