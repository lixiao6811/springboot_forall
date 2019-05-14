package com.example.springbootpro.mapper;

import com.example.springbootpro.entity.User;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    List<User> selectAllUser();

    int insertSelective(User record);
}