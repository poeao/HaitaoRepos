package com.anhry.app.manage.sample.dao;

import com.anhry.app.manage.sample.bean.Clazz;

public interface ClazzMapper {
    int deleteByPrimaryKey(String id);

    int insert(Clazz record);

    int insertSelective(Clazz record);

    Clazz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clazz record);

    int updateByPrimaryKey(Clazz record);
}