package com.example.springbootpro.mapper;

import com.example.springbootpro.entity.PltYouzanOrder;

public interface PltYouzanOrderMapper {
    int deleteByPrimaryKey(String tid);

    int insert(PltYouzanOrder record);

    int insertSelective(PltYouzanOrder record);

    PltYouzanOrder selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(PltYouzanOrder record);

    int updateByPrimaryKey(PltYouzanOrder record);
}