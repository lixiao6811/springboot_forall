package com.example.springbootpro.service.impl;

import com.example.springbootpro.entity.PltYouzanOrder;
import com.example.springbootpro.entity.PltYouzanOrderItem;
import com.example.springbootpro.mapper.PltYouzanOrderItemMapper;
import com.example.springbootpro.mapper.PltYouzanOrderMapper;
import com.example.springbootpro.service.SynFromYzOrderService;
import com.example.springbootpro.service.TokenService;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class SynFromYzOrderServiceImpl implements SynFromYzOrderService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PltYouzanOrderMapper pltYouzanOrderMapper;
    @Autowired
    private PltYouzanOrderItemMapper pltYouzanOrderItemMapper;
    private Logger logger = LoggerFactory.getLogger(SynFromYzOrderService.class);

    @Override
    public void SynOrder(String datestart,String dateend ) {
        YZClient client = new DefaultYZClient(new Token(tokenService.getToken())); //new Sign(appKey, appSecret)
        excuteSave(client,1l,datestart,dateend);
//        youzanTradesSoldGetParams.setPageSize(5L);

    }

    public void saveOrder(YouzanTradesSoldGetResult result,int i) {
        PltYouzanOrder pyo = new PltYouzanOrder();
        pyo.setTid(result.getTrades()[i].getTid());
        pyo.setCreated(result.getTrades()[i].getCreated());
        pyo.setStatus(result.getTrades()[i].getStatus());
        pyo.setType(result.getTrades()[i].getType());
        pyo.setPayTime(result.getTrades()[i].getPayTime());
        pyo.setTotalFee(BigDecimal.valueOf(result.getTrades()[i].getTotalFee()));
        pyo.setPostFee(BigDecimal.valueOf(result.getTrades()[i].getPostFee()));
        pyo.setPrice(BigDecimal.valueOf(result.getTrades()[i].getPrice()));
        pyo.setPayment(BigDecimal.valueOf(result.getTrades()[i].getPayment()));
        pyo.setDiscountFee(BigDecimal.valueOf(result.getTrades()[i].getDiscountFee()));
        pyo.setReceiverName(result.getTrades()[i].getReceiverName());
        pyo.setReceiverState(result.getTrades()[i].getReceiverState());
        pyo.setReceiverCity(result.getTrades()[i].getReceiverCity());
        pyo.setReceiverDistrict(result.getTrades()[i].getReceiverDistrict());
        pyo.setReceiverAddress(result.getTrades()[i].getReceiverAddress());
        pyo.setReceiverMobile(result.getTrades()[i].getReceiverMobile());
//        pyo.setSource(result.getTrades()[0].getSo());

//pyo.setMobile(result.getTrades()[0].get());
        pltYouzanOrderMapper.insert(pyo);

    }

    public void saveOrderItem(YouzanTradesSoldGetResult result,int i,int item) {
        PltYouzanOrderItem pyi = new PltYouzanOrderItem();
        pyi.setTid(result.getTrades()[i].getTid());
        pyi.setOid(result.getTrades()[i].getTid()+""+result.getTrades()[i].getOrders()[item].getItemId());
        pyi.setTotalFee(BigDecimal.valueOf(result.getTrades()[i].getOrders()[item].getTotalFee()));
        pyi.setDiscountFee(BigDecimal.valueOf(result.getTrades()[i].getOrders()[item].getDiscountFee()));
        pyi.setPrice(BigDecimal.valueOf(result.getTrades()[i].getOrders()[item].getPrice()));
        pyi.setPayment(BigDecimal.valueOf(result.getTrades()[i].getOrders()[item].getPayment()));
        pyi.setPayTime(result.getTrades()[i].getPayTime());
        pyi.setReceiverName(result.getTrades()[i].getReceiverName());
        pyi.setReceiverState(result.getTrades()[i].getReceiverState());
        pyi.setReceiverCity(result.getTrades()[i].getReceiverCity());
        pyi.setReceiverDistrict(result.getTrades()[i].getReceiverDistrict());
        pyi.setReceiverAddress(result.getTrades()[i].getReceiverAddress());
        pyi.setReceiverMobile(result.getTrades()[i].getReceiverMobile());
        pyi.setTitle(result.getTrades()[i].getOrders()[item].getTitle());
        pyi.setNum(Integer.valueOf(""+result.getTrades()[i].getOrders()[item].getNum()));
        pyi.setNumIid(""+result.getTrades()[i].getOrders()[item].getItemId());
        pyi.setCreated(result.getTrades()[i].getCreated());
        pltYouzanOrderItemMapper.insert(pyi);
    }

    public void excuteSave(YZClient client, Long page,String datestart,String dateend ) {
        YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

        Long pageSize = 50l;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
//            youzanTradesSoldGetParams.setStartCreated(sf.parse(datestart));
            youzanTradesSoldGetParams.setStartUpdate(sf.parse(datestart));
            youzanTradesSoldGetParams.setEndUpdate(sf.parse(dateend));
            youzanTradesSoldGetParams.setPageNo(page);
            youzanTradesSoldGetParams.setPageSize(pageSize);
//            youzanTradesSoldGetParams.setEndCreated(sf.parse(dateend));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
        youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
        YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
        Long total = result.getTotalResults();

        logger.info("当前会员数据保存到多少页=" + page);
        int totalPage = (int) (Math.ceil(total / pageSize));
//        String xo = result.getTrades()[0].getOrders()[0].;
//        result.getTrades()[1].;
        //

        for (int i = 0; i <result.getTrades().length ; i++) {
            saveOrder(result,i);
            for (int j = 0; j < result.getTrades()[i].getOrders().length; j++) {
                saveOrderItem(result,i,j);
            }
        }

        if (page < totalPage) {
            excuteSave(client, page + 1,datestart,dateend);
        }

    }

}
