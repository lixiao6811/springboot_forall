package com.example.springbootpro.mapper;

import com.example.springbootpro.entity.PltYouzanCustomer;
import org.springframework.stereotype.Component;

public interface PltYouzanCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PltYouzanCustomer record);

    int insertSelective(PltYouzanCustomer record);

    PltYouzanCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PltYouzanCustomer record);

    int updateByPrimaryKey(PltYouzanCustomer record);
}