package com.example.springbootpro.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobEtl {
    @Scheduled(cron = "${scheduled.timing}")
    public void customerExcute() {
        log.info("定时任务1正在执行---------------------------");
    }
}
