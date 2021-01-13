package com.example.springbootpro.service;

import com.example.springbootpro.entity.User;
import com.example.springbootpro.tools.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    R addUser(User user);

    R testApi(User user);

    R findAllUser(int pageNum, int pageSize);
}