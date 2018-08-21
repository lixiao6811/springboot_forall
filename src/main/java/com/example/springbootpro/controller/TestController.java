package com.example.springbootpro.controller;

import com.example.springbootpro.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerSearch;
import com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerSearchParams;

@Controller
@RequestMapping(value = "/ty")
public class TestController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        logger.info("请求已经到达");
        logger.debug("debug日志显示");

        YouzanScrmCustomerSearchParams youzanScrmCustomerSearchParams = new YouzanScrmCustomerSearchParams();

        YouzanScrmCustomerSearch youzanScrmCustomerSearch = new YouzanScrmCustomerSearch();
        youzanScrmCustomerSearch.setAPIParams(youzanScrmCustomerSearchParams);





        return 1;
    }

}
