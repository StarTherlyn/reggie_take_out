package com.hjx.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjx.reggie.Entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}