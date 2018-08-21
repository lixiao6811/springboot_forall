package com.example.springbootpro.mapper;

import com.example.springbootpro.entity.PltHsShop;

public interface PltHsShopMapper {
    int deleteByPrimaryKey(String orgcode);

    int insert(PltHsShop record);

    int insertSelective(PltHsShop record);

    PltHsShop selectByPrimaryKey(String orgcode);

    int updateByPrimaryKeySelective(PltHsShop record);

    int updateByPrimaryKey(PltHsShop record);
}