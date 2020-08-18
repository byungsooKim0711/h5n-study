package com.humuson.imc.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@Slf4j
//@Component
//public class GracefulShutdownConfig implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
//
//    private static final int TIMEOUT = 30;
//
//    private volatile Connector connector;
//
//    @Override
//    public void customize(Connector connector) {
//        this.connector = connector;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextClosedEvent event) {
//        this.connector.pause();
//        Executor executor = this.connector.getProtocolHandler().getExecutor();
//
//        if (executor instanceof ThreadPoolExecutor) {
//            try {
//                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
//                threadPoolExecutor.shutdown();
//
//                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
//                    log.warn("Tomcat thread pool did not shutdown gracefully within {} seconds. Proceeding with forceful shutdown.", TIMEOUT);
//                    threadPoolExecutor.shutdownNow();
//
//                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
//                        log.error("Tomcat thread pool did not terminated.");
//                    }
//                } else {
//                    log.info("Tomcat thread pool has been gracefully shutdown.");
//                }
//            } catch (InterruptedException e) {
//                log.error("Interrupted Exception: {}", e.getCause(), e);
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//
//    @Bean
//    public GracefulShutdownConfig gracefulShutdown() {
//        return new GracefulShutdownConfig();
//    }
//
//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdownConfig gracefulShutdown) {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(gracefulShutdown);
//        return factory;
//    }
//}
