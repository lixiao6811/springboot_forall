package com.example.springbootpro.mqserver.impl;

import com.example.springbootpro.mqserver.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by 97434 on 2019/5/17.
 */
@Slf4j
@Component
public class PgCodeChkHandler implements MessageHandler<String> {
    @Override
    public void handle(String message) {
        log.info("这个消息由PgCodeChkHandler来处理");
    }
}
