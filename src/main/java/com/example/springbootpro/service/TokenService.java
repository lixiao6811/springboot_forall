package com.example.springbootpro.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ying.tian on 2017/9/4.
 */
@Service
public class TokenService {
    @Value("${youzan.token.get.url}")
    private String youzanTokenUrl;
    @Value("${youzan.client_id}")
    private String youzanClientId;
    @Value("${youzan.client_secret}")
    private String youzanClientSecret;
    @Value("${youzan.grant_type}")
    private String youzanGrantType;
    @Value("${youzan.kdt_id}")
    private String youzanKdtId;
    private String token;
    private Logger logger = LoggerFactory.getLogger(TokenService.class);
    public  String getToken(){
        if(!StringUtils.isEmpty(token)){
            return  token;
        }
        HttpHeaders requestHeaders = createHttpHeader();
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("client_id", youzanClientId);
        paramMap.add("client_secret",youzanClientSecret);
        paramMap.add("grant_type", youzanGrantType);
        paramMap.add("kdt_id",youzanKdtId);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.postForObject(youzanTokenUrl, requestEntity, String.class);
            if (StringUtils.isNotBlank(result)&&result.contains("access_token")) {
                        token = JSON.parseObject(result).getString("access_token");
                        logger.info("token: " + token);
                        System.out.println(token);
                    return token;
            }
        } catch (RestClientException e) {
            logger.error("can not connect the url:" + youzanTokenUrl, e);
        }
        return "6e02eb7631e93a33b744c6446be0afc4";
    }

    public void setToken(String token){
        this.token=token;
    }

    private HttpHeaders createHttpHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return requestHeaders;
    }
}
