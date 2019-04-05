package com.zilong;

import com.zilong.model.InputRecord;
import com.zilong.model.OutputRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "threadPoolExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("threadPoolExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "incomingQueue")
    public Queue<InputRecord> getIncomingQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Bean(name = "outgoingQueue")
    public Queue<OutputRecord> getOutgoingQueue() {
        return new ConcurrentLinkedQueue<>();
    }


}
