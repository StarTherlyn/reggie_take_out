package com.hjx.reggie.dto;

import com.hjx.reggie.Entity.Dish;
import com.hjx.reggie.Entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
