package com.example.springbootpro.mqserver;

/**
 * Created by 97434 on 2019/5/17.
 */
public interface MessageHandler<T extends Object> {

    void handle(T message);

}
