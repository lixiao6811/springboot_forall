package com.example.springbootpro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class User {
    private String userId;

    @NotNull(message = "userName can not be empty")
//    @JsonProperty("testName")
    private String userName;

    @NotNull(message = "password can not be empty")
    private String password;

//    @JsonIgnore
    public String getPassword() {
        return password;
    }

    private String phone;

    private String ff_age;

}