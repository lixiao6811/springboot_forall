package com.example.springbootpro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 2018/05/22
 * 1,@EnableScheduling 代表启动定时任务
 * 2，@ServletComponentScan  加载过滤器注解
 * 3，@MapperScan("com.example.springbootpro.mapper") 指定mapper路径
 */
@EnableScheduling
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.example.springbootpro.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class SpringbootproApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootproApplication.class, args);
	}
}
