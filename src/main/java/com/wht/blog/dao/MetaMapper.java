package com.wht.blog.dao;

import com.wht.blog.entity.Meta;

public interface MetaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Meta record);

    int insertSelective(Meta record);

    Meta selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Meta record);

    int updateByPrimaryKey(Meta record);
}