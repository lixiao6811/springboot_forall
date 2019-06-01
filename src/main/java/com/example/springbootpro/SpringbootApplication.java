package com.example.springbootpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 2018/05/22
 * 1,@EnableScheduling 代表启动定时任务
 * 2，@ServletComponentScan  加载过滤器注解
 * 3，@MapperScan("com.example.springbootpro.mapper") 指定mapper路径
 */
@EnableScheduling
//@ServletComponentScan
@SpringBootApplication
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	@Bean
	public RestTemplate resTemplateHttp(RestTemplateBuilder builder) {
		return builder.build();
	}
}


