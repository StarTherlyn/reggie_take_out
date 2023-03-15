package com.hjx.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjx.reggie.Entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
