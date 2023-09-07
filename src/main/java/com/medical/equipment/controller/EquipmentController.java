package com.medical.equipment.controller;

import com.medical.equipment.annotation.AuthCheck2;
import com.medical.equipment.annotation.AuthCheck3;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.EquipmentEntity;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("equipment")
@Api(value = "设备控制层", tags = "设备管理相关的接口")
public class EquipmentController {


    @Resource
    private EquipmentService equipmentService;

    @PostMapping("add")
    @ApiOperation(value = "设备信息新增接口")
    @AuthCheck2
    public R addEquipment(@Validated({AddGroup.class}) @Valid @RequestBody EquipmentEntity equipment) {
        equipmentService.addEquipment(equipment);
        return R.ok("设备信息新增成功");
    }


    @DeleteMapping("del")
    @ApiOperation(value = "设备信息删除接口")
    @AuthCheck3
    public R delEquipment(@RequestParam Long id) {
        equipmentService.delEquipment(id);
        return R.ok("设备信息删除成功");
    }

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有设备信息接口")
    public R findAll() {
        PageResultUtils pageResultUtils = equipmentService.findAll();
        return R.ok().put(RConstant.data, pageResultUtils);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询单个设备信息详情接口")
    public R findById(@RequestParam Long id) {
        EquipmentEntity equipment = equipmentService.findEquipmentById(id);
        return R.ok().put(RConstant.data, equipment);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新设备信息接口")
    public R update(@Validated({UpdateGroup.class}) @Valid @RequestBody EquipmentEntity equipment) {
        equipmentService.update(equipment);
        return R.ok();
    }


}
