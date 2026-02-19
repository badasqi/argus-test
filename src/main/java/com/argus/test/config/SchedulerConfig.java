package com.argus.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig {
    @Bean
    public ScheduledExecutorService taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
}
