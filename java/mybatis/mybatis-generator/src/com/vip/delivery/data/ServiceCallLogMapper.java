package com.vip.delivery.data;

import com.vip.delivery.bean.ServiceCallLog;

public interface ServiceCallLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServiceCallLog record);

    int insertSelective(ServiceCallLog record);

    ServiceCallLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServiceCallLog record);

    int updateByPrimaryKey(ServiceCallLog record);
}