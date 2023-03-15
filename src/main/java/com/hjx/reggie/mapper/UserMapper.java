package com.hjx.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjx.reggie.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
