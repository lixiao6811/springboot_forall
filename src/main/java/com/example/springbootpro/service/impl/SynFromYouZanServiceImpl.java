package com.example.springbootpro.service.impl;

import com.example.springbootpro.entity.CustomerResult;
import com.example.springbootpro.entity.PltYouzanCustomer;
import com.example.springbootpro.entity.YouzanCustomerGet;
import com.example.springbootpro.mapper.PltYouzanCustomerMapper;
import com.example.springbootpro.service.SynFromYouZanService;
import com.example.springbootpro.service.TokenService;
import com.example.springbootpro.tools.EmojiFilter;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanScrmCardList;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanScrmCustomerSearch;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class SynFromYouZanServiceImpl implements SynFromYouZanService {
    private Logger logger = LoggerFactory.getLogger(SynFromYouZanServiceImpl.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PltYouzanCustomerMapper pltYouzanCustomerMapper;

    private Charset charset = Charset.forName("UTF-8");
//    //订单同步
//    @Override
//    public void synOrder() {
//
//    }

    boolean li=true;
    //会员同步
    @Override
    public void synMember() {
        YZClient client = new DefaultYZClient(new Token(tokenService.getToken())); //new Sign(appKey, appSecret)
        //获取会员卡列表
        List<YouzanScrmCardListResult.CardListItemDTO> cardList = getCardInfoByYouZan(client, 1L, 1);
        logger.info("get card from you zan,card size:" + cardList.size());
        if (CollectionUtils.isEmpty(cardList)) {
            return;
        }
        List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> userAddList = getUserInfoByCard(cardList);
        logger.info("添加的会员条数" + userAddList.size());
//        logger.info("其中一条会员数据示例" + userAddList.get(0).toString());
    }



    /**
     * 获取有赞会员卡
     *
     * @param client
     * @param page
     * @param totalPage
     * @return
     */
    public List<YouzanScrmCardListResult.CardListItemDTO> getCardInfoByYouZan(YZClient client, Long page, int totalPage) {
        List<YouzanScrmCardListResult.CardListItemDTO> resultList = new ArrayList<>();

        YouzanScrmCardListParams youzanScrmCardListParams = new YouzanScrmCardListParams();
        youzanScrmCardListParams.setPage(page);
        YouzanScrmCardList youzanScrmCardList = new YouzanScrmCardList();
        youzanScrmCardList.setAPIParams(youzanScrmCardListParams);
        YouzanScrmCardListResult result = client.invoke(youzanScrmCardList);

        Long total = result.getTotal();
        Long pageSize = result.getPageSize();
        if (result.getItems() != null) {
            List list = java.util.Arrays.asList(result.getItems());
            resultList.addAll(list);
        }
        if (page >= totalPage) {
            return resultList;
        } else {
            List<YouzanScrmCardListResult.CardListItemDTO> childeList = getCardInfoByYouZan(client, page + 1, totalPage);
            if (!CollectionUtils.isEmpty(childeList)) {
                resultList.addAll(childeList);
            }
        }
        return resultList;
    }

    public List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> getUserInfoByCard(List<YouzanScrmCardListResult.CardListItemDTO> cardList) {

        YZClient client = new DefaultYZClient(new Token(tokenService.getToken()));

        //会员信息项列表
        List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> userList = new ArrayList<>();
        //遍历会员卡，获取会员信息
        for (YouzanScrmCardListResult.CardListItemDTO cardInfo : cardList) {
            //商家会员卡唯一标识的列表
            List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> userByCardList = getUserInfoByCard(client, 1L, 1, cardInfo.getCardAlias());
            if (!CollectionUtils.isEmpty(userByCardList)) {
                userList.addAll(userByCardList);
            }
        }
        return userList;
    }

    /**
     * 获取有赞会员卡
     *
     * @param client
     * @param page
     * @param totalPage
     * @return
     */
    public List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> getUserInfoByCard(YZClient client, Long page, int totalPage, String cardAlias) {
        List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> resultList = new ArrayList<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO>();
        YouzanScrmCustomerSearchParams youzanScrmCustomerSearchParams = new YouzanScrmCustomerSearchParams();
        youzanScrmCustomerSearchParams.setPage(page);
        youzanScrmCustomerSearchParams.setCardAlias(cardAlias);
        YouzanScrmCustomerSearch youzanScrmCustomerSearch = new YouzanScrmCustomerSearch();
        youzanScrmCustomerSearch.setAPIParams(youzanScrmCustomerSearchParams);
        try {
            YouzanScrmCustomerSearchResult result = client.invoke(youzanScrmCustomerSearch);
            Long total = result.getTotal();
            Long pageSize = result.getPageSize();
            totalPage = (int) (Math.ceil(total / pageSize));
            if (result.getItems() != null) {
                List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> list = java.util.Arrays.asList(result.getItems());
                resultList.addAll(list);
            }
        } catch (Exception e) {
            logger.error("failed schedule job {}, error:{}", e);
            logger.error("会员卡里没有会员");
        }
        logger.info("当前会员数据保存到多少页=" + page);

        if (page >= totalPage) {
            return resultList;
        } else {
            //存储到数据库中
            for (int i = 0; i < resultList.size(); i++) {
//                if (!StringUtils.isEmpty(resultList.get(i).getMobile())) {
               PltYouzanCustomer pyc = getUserMessage(resultList.get(i), client);
                if (!ObjectUtils.isEmpty(pyc)) {
                    try {
                        pltYouzanCustomerMapper.insert(pyc);
                    } catch (Exception e) {
                        logger.info("名字里面有不能识别的");
                    }
                }

            }
            logger.info("总共有多少页="+totalPage);
            if(li){
                page=page+649;
                li=false;
            }
            List<YouzanScrmCustomerSearchResult.CardCustomerListItemDTO> childeList = getUserInfoByCard(client, page + 1, totalPage, cardAlias);
            if (!CollectionUtils.isEmpty(childeList)) {
                resultList.addAll(childeList);
            }
        }
        return resultList;
    }

    public PltYouzanCustomer getUserMessage(YouzanScrmCustomerSearchResult.CardCustomerListItemDTO cld, YZClient client) {
//        try {
        YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new YouzanScrmCustomerGetParams();
        if (StringUtils.isEmpty(cld.getMobile())&&!StringUtils.isEmpty(cld.getFansId())){
            youzanScrmCustomerGetParams.setMobile("");
            youzanScrmCustomerGetParams.setFansType(1L);
            youzanScrmCustomerGetParams.setFansId(cld.getFansId());
        }else if (!StringUtils.isEmpty(cld.getMobile()))
        {
            youzanScrmCustomerGetParams.setMobile(cld.getMobile());
            youzanScrmCustomerGetParams.setFansType(1L);
            youzanScrmCustomerGetParams.setFansId(cld.getFansId());
        }else {
            return null;
        }




        YouzanCustomerGet youzanScrmCustomerGet = new YouzanCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
//        YouzanScrmCustomerGetResult result = client.invoke(youzanScrmCustomerGet);
//        CustomerResult result = client.invoke(youzanScrmCustomerGet);

        CustomerResult result = client.invoke(youzanScrmCustomerGet);
//logger.info("接受到的对象:{}",result.toString());
        PltYouzanCustomer pyc = new PltYouzanCustomer();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        try {
//            EmojiFilter emojiFilter=new EmojiFilter();
            String nick=cld.getWxNickname();
            if (EmojiFilter.containsEmoji(nick)){
                nick=EmojiFilter.filterEmoji(nick);
            }
            nick=new String(nick.getBytes(),charset);
//            System.out.println("昵称是+"+nick);
            pyc.setNick(nick);
            if (!StringUtils.isEmpty(result.getBirthday())){
                pyc.setBirthday(sp.parse(result.getBirthday()));
            }
            if (!StringUtils.isEmpty(result.getFirstTime())){
                pyc.setCreated(sp.parse(result.getFirstTime()));
            }
            pyc.setFullName(cld.getName());
            pyc.setMobile(cld.getMobile());
            pyc.setCustomerno(String.valueOf(cld.getFansId()));
            pyc.setSex(String.valueOf(result.getGender()));
            pyc.setSource(String.valueOf(result.getSourceType()));
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


//            logger.info("结果展示" + result.getName());
//        } catch (Exception e) {
//            logger.info("区域信息异常");
//        }
//        CustomerResult rs=(CustomerResult)result;
//        PltYouzanCustomer pltYouzanCustomer = new PltYouzanCustomer();
//        pltYouzanCustomer.setFullName(rs.getName());


//        pltYouzanCustomer.setAddress(result.getArea());

//        try {
////            if (StringUtils.isEmpty(result.getBirthday()));
////            pltYouzanCustomer.setBirthday(sp.parse(result.getBirthday()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        pltYouzanCustomer.setNick(result.g);

    }

}


