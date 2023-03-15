package com.hjx.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjx.reggie.Entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
