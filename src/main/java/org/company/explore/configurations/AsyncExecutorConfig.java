package org.company.explore.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@EnableAsync
@Configuration
public class AsyncExecutorConfig implements AsyncConfigurer {

    @Value("${org.company.explore.async.threads:50}")
    private Integer nThreads;

    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(nThreads);
    }
}