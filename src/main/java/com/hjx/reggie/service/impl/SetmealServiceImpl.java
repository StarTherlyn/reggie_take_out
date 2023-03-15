package com.hjx.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjx.reggie.Entity.Setmeal;
import com.hjx.reggie.Entity.SetmealDish;
import com.hjx.reggie.common.CustomException;
import com.hjx.reggie.dto.SetmealDto;
import com.hjx.reggie.mapper.SetmealMapper;
import com.hjx.reggie.service.SetmealDishService;
import com.hjx.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional //事务注解
    public void saveWithDish(SetmealDto setmealDto) {
        //保存基本信息
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish:setmealDishes){
            setmealDish.setDishId(setmealDto.getId());
            setmealDish.setSetmealId(1234L);
        }
        //保存关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.in(Setmeal::getId,ids);
        lambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(lambdaQueryWrapper);
        if(count>0){
            //不能删除，抛出异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据
        this.removeByIds(ids);

        //再删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove((lambdaQueryWrapper1));
    }
}
