package com.bqua.fleetops.job.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "jobExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);    // 기본 스레드 수
        executor.setMaxPoolSize(10);     // 최대 스레드 수
        executor.setQueueCapacity(100);  // 큐 크기
        executor.setThreadNamePrefix("job-executor-");
        executor.initialize();
        return executor;
    }
}