package com.example.springbootpro.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobEtl {
    public static final Logger logger = LoggerFactory.getLogger(JobEtl.class);

    @Scheduled(cron = "${scheduled.timing}")
    public void customerExcute() {
        logger.info("定时任务1正在执行---------------------------");
    }
}
