package com.example.springbootpro.service.impl;

import com.example.springbootpro.entity.User;
import com.example.springbootpro.mapper.UserMapper;
import com.example.springbootpro.service.UserService;
import com.example.springbootpro.tools.CostTime;
import com.example.springbootpro.tools.R;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响,警告级别问题

    @Override
    public R addUser(User user) {
        boolean result = false;
        try {
            log.info("接受到的用户信息数据：{}", user.toString());
            result = userMapper.insertSelective(user) > 0 ? true : false;
            if (result)
                return R.ok();
        } catch (Exception e) {
            log.warn("会员插入异常，错误信息:{}", e.getMessage());
            return R.error();
        }
        return R.error("会员插入异常，请检查");
    }

    @Override
    public R testApi(User user) {

        return R.ok();
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @CostTime(message = "分页查询方法，耗时：{}ms")
    @Override
    public R findAllUser(int pageNum, int pageSize) {
        List<User> list = null;
        try {
            //将参数传给这个方法就可以实现物理分页了，非常简单。
            PageHelper.startPage(pageNum, pageSize);
            list = userMapper.selectAllUser();
        } catch (Exception e) {
            return R.error();
        }
        return R.ok().put("jsonDate", list);
    }
}
