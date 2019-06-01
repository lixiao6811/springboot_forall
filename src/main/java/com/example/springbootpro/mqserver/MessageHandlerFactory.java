package com.example.springbootpro.mqserver;


import com.example.springbootpro.tools.ApplicationContextProvider;
import org.springframework.util.ObjectUtils;

/**
 * 处理器构建工厂
 * Created by flym on 8/24/2016.
 */
public class MessageHandlerFactory {
    /**
     * 根据相应的处理器名字获取相应的处理器
     */
    public static <X extends Object, T extends MessageHandler<X>> T getHandler(String handlerName) {
        if (ObjectUtils.isEmpty(handlerName))
            return null;
        handlerName = (handlerName + "Handler").replaceFirst(handlerName, handlerName.toLowerCase());
//        Class handlerClass= (Class) Class.forName(handlerName).newInstance();
        return (T) ApplicationContextProvider.getBean(handlerName);
    }

}