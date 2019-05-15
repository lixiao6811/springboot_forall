package com.example.springbootpro.entity;

public class User {
    private String userId;

    private String userName;

    private String password;

    private String phone;

    private String ff_age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFf_age() {
        return ff_age;
    }

    public void setFf_age(String ff_age) {
        this.ff_age = ff_age == null ? null : ff_age.trim();
    }
}