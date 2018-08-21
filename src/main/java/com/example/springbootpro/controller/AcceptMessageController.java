package com.example.springbootpro.controller;

import com.example.springbootpro.service.SynCustomerService;
import com.example.springbootpro.service.SynFromYouZanService;
import com.example.springbootpro.service.SynFromYzOrderService;
import com.example.springbootpro.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/youzan")
public class AcceptMessageController {
    private static Logger logger = LoggerFactory.getLogger(AcceptMessageController.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    private SynFromYzOrderService synFromYzOrderService;

    @Autowired
    private SynFromYouZanService synFromYouZanService;
    @Autowired
    private SynCustomerService synCustomerService;


//    @RequestMapping(value = "/getToken", produces = {"application/json;charset=UTF-8"})
//    public String getTokenService() {
//        logger.info("开始获取token");
//        String token = tokenService.getToken();
//        return token;
//    }
//
//    @RequestMapping(value = "/member", produces = {"application/json;charset=UTF-8"})
//    public void getMenmber() {
//        logger.info("开始同步会员信息");
//        synFromYouZanService.synMember();
//
//    }

    @RequestMapping(value = "/order", produces = {"application/json;charset=UTF-8"})
    public void getOrder() {
        logger.info("开始同步订单信息");
        String datestart = "";
        String dateend = "";
        synFromYzOrderService.SynOrder(datestart,dateend);

    }
//
    @RequestMapping(value = "/customer", produces = {"application/json;charset=UTF-8"})
    public void getCustomer() {
        logger.info("开始同步会员信息");
        String datestart = "";
        String dateend = "";
        synCustomerService.synCustomer(datestart,dateend);

    }


}
