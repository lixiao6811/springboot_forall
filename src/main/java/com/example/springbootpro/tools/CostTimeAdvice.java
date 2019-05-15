package com.example.springbootpro.tools;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截器，实现函数调用时间统计分析功能。
 *
 * @author xiao.li
 *         2016/9/18.
 */
@Slf4j
@Aspect
@Configuration
public class CostTimeAdvice {

    @Around("execution(* com.example.springbootpro.service.*.*(..) ) && @annotation(costTime)")
    public Object proceed(ProceedingJoinPoint aopProxyChain, CostTime costTime) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = aopProxyChain.proceed();
        long time = System.currentTimeMillis() - start;
        if(costTime.printSlowLog()) {
            if(costTime.slowLoadTime() == -1) {
                log.info(costTime.message(), time);
            } else if(costTime.slowLoadTime() < time) {
                log.warn(costTime.message(), time);
            }
        }
        return proceed;
    }
}
