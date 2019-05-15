package com.example.springbootpro.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 函数调用耗时日志记录
 *
 * @author xiao.li
 *         2016/9/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CostTime {

    /**
     * 打印耗时日志
     */
    boolean printSlowLog() default true;

    /**
     * 当请求耗时超过此值时，记录目录（printSlowLog=true 时才有效），单位：毫秒
     * 默认-1，一直显示
     */
    long slowLoadTime() default -1;

    /**
     * 耗时日志信息定义
     */
    String message() default "execute method costTime:{}ms";
}
