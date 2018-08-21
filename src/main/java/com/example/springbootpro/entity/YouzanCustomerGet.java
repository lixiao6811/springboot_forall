package com.example.springbootpro.entity;

import com.youzan.open.sdk.api.AbstractAPI;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanScrmCustomerGetResult;

public class YouzanCustomerGet extends AbstractAPI {

    public YouzanCustomerGet() {
    }

    public YouzanCustomerGet(YouzanScrmCustomerGetParams apiParams) {
        this.params = apiParams;
    }

    public String getHttpMethod() {
        return "GET";
    }

    public String getVersion() {
        return "3.0.0";
    }

    public String getName() {
        return "youzan.scrm.customer.get";
    }

    public Class getResultModelClass() {
        return CustomerResult.class;
    }

    public Class getParamModelClass() {
        return YouzanScrmCustomerGetParams.class;
    }

    public boolean hasFiles() {
        return false;
    }
}
