package com.hjx.reggie.dto;

import com.hjx.reggie.Entity.Setmeal;
import com.hjx.reggie.Entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
