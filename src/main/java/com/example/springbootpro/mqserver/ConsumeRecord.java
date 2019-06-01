package com.example.springbootpro.mqserver;

import com.example.springbootpro.tools.Action1;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Created by 97434 on 2019/5/17.
 */
@Slf4j
@Component
public class ConsumeRecord {
    public void handle() {
        val message = "{\"Api\":\"Login\"}";
        val typeName = "Login";
        MessageHandler<String> handler = MessageHandlerFactory.getHandler(typeName);
        if (handler == null) {
            log.warn("数据所对应的消息类型还没有相应的处理器，将忽略之.消息类型:{},数据:{}", typeName, message);
            return;
        }
        log.debug("准备处理消息.typeName:{}, message:{}", typeName, message);
        doHandle(() -> handler.handle(message));
        log.debug("完成处理消息.typeName:{}, message:{}", typeName, message);
    }

    private void doHandle(Action1 action) {
        try {
            action.call();
        } catch (Exception e) {
            log.error("消息处理发生错误：{}", e.getMessage());
        }
    }
}
