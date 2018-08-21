package com.example.springbootpro.service.impl;

import com.example.springbootpro.entity.CustomerResult;
import com.example.springbootpro.entity.PltYouzanCustomer;
import com.example.springbootpro.entity.YouzanCustomerGet;
import com.example.springbootpro.mapper.PltYouzanCustomerMapper;
import com.example.springbootpro.service.SynCustomerService;
import com.example.springbootpro.service.TokenService;
import com.example.springbootpro.tools.EmojiFilter;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerGetParams;
import com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerSearch;
import com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerSearchParams;
import com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class SynCustomerServiceImpl implements SynCustomerService {
    private Charset charset = Charset.forName("UTF-8");
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PltYouzanCustomerMapper pltYouzanCustomerMapper;
    private Logger logger = LoggerFactory.getLogger(SynCustomerServiceImpl.class);

    boolean li=true;
    @Override
    public void synCustomer(String datestart,String dateend) {
        YZClient client = new DefaultYZClient(new Token(tokenService.getToken())); //new Sign(appKey, appSecret)
        excuteSave(client, 1l,datestart,dateend);
    }
    public void excuteSave(YZClient client, Long page,String datestart,String dateend) {
        if(li){
            page=page+0;
            li=false;
        }
        YouzanScrmCustomerSearchParams params = new YouzanScrmCustomerSearchParams();
//        String datestart = "2018-07-31 00:00:00";
//        String dateend = "2018-08-14 00:00:00";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
//            Date date=sf.parse(datestart);
//            Calendar c = Calendar.getInstance();
//            c.setTime(date);
//            c.add(Calendar.DAY_OF_MONTH, 1);

            params.setCreatedAtStart(sf.parse(datestart).getTime() / 1000);
            params.setCreatedAtEnd(sf.parse(dateend).getTime() / 1000);
            params.setPageNo(page);
            params.setPageSize(50l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        YouzanScrmCustomerSearch youzanScrmCustomerSearch = new YouzanScrmCustomerSearch();
        youzanScrmCustomerSearch.setAPIParams(params);
        YouzanScrmCustomerSearchResult result = client.invoke(youzanScrmCustomerSearch);


        Long total = result.getTotal();
        Long pageSize = 50l;
        logger.info("当前会员数据保存到多少页=" + page);
        int totalPage = (int) (Math.ceil(total / pageSize));
        for (int i = 0; i < result.getRecordList().length; i++) {
            String mobile = result.getRecordList()[i].getMobile();
            Long fansId = result.getRecordList()[i].getWeixinFansId();
            String name = result.getRecordList()[i].getName();
            if (EmojiFilter.containsEmoji(name)){
                name=EmojiFilter.filterEmoji(name);
            }
            name=new String(name.getBytes(),charset);

            PltYouzanCustomer pltYouzanCustomer= saveUserMessage(name,mobile,fansId,client);
            if (pltYouzanCustomer!=null){
                pltYouzanCustomerMapper.insert(pltYouzanCustomer);
            }

        }
        if (page < totalPage) {
            excuteSave(client, page + 1,datestart,dateend);
        }
    }


    public PltYouzanCustomer saveUserMessage(String name, String mobile, Long fansId, YZClient client) {
//        try {
        YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new YouzanScrmCustomerGetParams();
        if (StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(fansId)&& fansId!=0) {
            youzanScrmCustomerGetParams.setMobile("");
            youzanScrmCustomerGetParams.setFansType(1L);
            youzanScrmCustomerGetParams.setFansId(fansId);
        } else if (!StringUtils.isEmpty(mobile)&& !"0".equals(mobile)) {
            youzanScrmCustomerGetParams.setMobile(mobile);
            youzanScrmCustomerGetParams.setFansType(1L);
            youzanScrmCustomerGetParams.setFansId(fansId);
        } else {
            return null;
        }
        YouzanCustomerGet youzanScrmCustomerGet = new YouzanCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
//        YouzanScrmCustomerGetResult result = client.invoke(youzanScrmCustomerGet);
//        CustomerResult result = client.invoke(youzanScrmCustomerGet);

        CustomerResult result = null;
        try {
            result = client.invoke(youzanScrmCustomerGet);
        } catch (Exception e) {
            e.printStackTrace();
        }


//logger.info("接受到的对象:{}",result.toString());
        PltYouzanCustomer pyc = new PltYouzanCustomer();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        try {
//            EmojiFilter emojiFilter=new EmojiFilter();
//            String nick=cld.getWxNickname();
//            if (EmojiFilter.containsEmoji(nick)){
//                nick=EmojiFilter.filterEmoji(nick);
//            }
//            nick=new String(nick.getBytes(),charset);
////            System.out.println("昵称是+"+nick);
//            pyc.setNick(nick);
            if (!StringUtils.isEmpty(result.getBirthday())) {
                pyc.setBirthday(sp.parse(result.getBirthday()));
            }
            if (!StringUtils.isEmpty(result.getFirstTime())) {
                pyc.setCreated(sp.parse(result.getFirstTime()));
            }
            pyc.setFullName(name);
            pyc.setMobile(mobile);
            pyc.setCustomerno(String.valueOf(fansId));
            pyc.setSex(String.valueOf(result.getGender()));
//            pyc.setSource(String.valueOf(result.getSourceType()));
            if (!ObjectUtils.isEmpty(result.getArea())) {
                String state = result.getArea().getString("province_name");
                String city = result.getArea().getString("city_name");
                String address = result.getArea().getString("county_name");
                pyc.setState(state);
                pyc.setCity(city);
                pyc.setAddress(address);
            }
            return pyc;
        } catch (ParseException e) {
            e.printStackTrace();
            return pyc;
        }
    }


}
