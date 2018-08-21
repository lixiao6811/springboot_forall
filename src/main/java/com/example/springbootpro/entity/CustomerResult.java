package com.example.springbootpro.entity;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.youzan.open.sdk.model.APIResult;

public class CustomerResult implements APIResult {
    @JsonProperty("name")
    private String name;
    @JsonProperty("gender")
    private Long gender;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("area")
    private JSONObject area;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("source_type")
    private Long sourceType;
    @JsonProperty("tag_names")
    private String tagNames;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("current_points")
    private Long currentPoints;
    @JsonProperty("trade_count")
    private Long tradeCount;
    @JsonProperty("last_trade_time")
    private String lastTradeTime;
    @JsonProperty("first_time")
    private String firstTime;

    public CustomerResult() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getGender() {
        return this.gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public JSONObject getArea() {
        return area;
    }

    public void setArea(JSONObject area) {
        this.area = area;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceType() {
        return this.sourceType;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getTagNames() {
        return this.tagNames;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCurrentPoints(Long currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Long getCurrentPoints() {
        return this.currentPoints;
    }

    public void setTradeCount(Long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Long getTradeCount() {
        return this.tradeCount;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getLastTradeTime() {
        return this.lastTradeTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getFirstTime() {
        return this.firstTime;
    }

}
