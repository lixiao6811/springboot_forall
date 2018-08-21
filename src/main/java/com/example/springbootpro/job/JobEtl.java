package com.example.springbootpro.job;

import com.example.springbootpro.service.SynCustomerService;
import com.example.springbootpro.service.SynFromYzOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class JobEtl {
    public static final Logger logger= LoggerFactory.getLogger(JobEtl.class);

    @Value("${card.name}")
    private String name;

    @Value("${card.id}")
    private int id;
    @Autowired
    private SynFromYzOrderService synFromYzOrderService;
    @Autowired
    private SynCustomerService synCustomerService;

    @Scheduled(cron = "0 0 01 * * ?")
    public void customerExcute(){
        logger.info("开始同步会员信息");
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar1.add(Calendar.DATE, -1);
        String currentDate=sdf1.format(new Date());
        String three_days_ago = sdf1.format(calendar1.getTime());
        System.out.println(three_days_ago);
        String datestart=three_days_ago;
        String dateend=currentDate;
        synCustomerService.synCustomer( datestart, dateend);
    }
    @Scheduled(cron = "0 0 02 * * ?")
    public void orderExcute(){
        logger.info("开始同步订单信息当前时间：{}",new Date());
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar1.add(Calendar.DATE, -3);
        String currentDate=sdf1.format(new Date());
        String three_days_ago = sdf1.format(calendar1.getTime());
        System.out.println(three_days_ago);
        String datestart=three_days_ago;
        String dateend=currentDate;
        synFromYzOrderService.SynOrder( datestart, dateend);
    }

}
