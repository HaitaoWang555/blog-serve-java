package com.wht.blog.dao;

import com.wht.blog.entity.Middle;

public interface MiddleMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByMiddle(Integer articleId, Integer metaId);
    int insert(Middle record);

    int insertSelective(Middle record);

    Middle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Middle record);

    int updateByPrimaryKey(Middle record);
}