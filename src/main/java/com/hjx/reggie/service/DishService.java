package com.hjx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjx.reggie.Entity.Dish;
import com.hjx.reggie.dto.DishDto;

public interface DishService extends IService<Dish> {
    /**
     * 新增菜品
     */
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
