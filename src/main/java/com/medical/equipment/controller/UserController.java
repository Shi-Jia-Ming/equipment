package com.medical.equipment.controller;

import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.UserEntity;
import com.medical.equipment.service.UserService;
import com.medical.equipment.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    // 根据用户ID查找某个用户的图表
//    @PostMapping("findLineChart")
//    public R findLineChart(@RequestParam int id) {
//
//    }
}
