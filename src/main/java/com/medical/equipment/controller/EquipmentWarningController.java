package com.medical.equipment.controller;

import com.medical.equipment.constant.FileConstant;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.EquipmentWarningService;
import com.medical.equipment.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("warning")
public class EquipmentWarningController {
    @Autowired
    EquipmentWarningService equipmentWarningService;


    //通过设备类型和预警类型查找
    @GetMapping("findByUserId")
    public R findByTypeIdWarningId(Integer warningId,Integer typeId) {
        HashMap<Integer, HashMap<Integer, List<EquipmentWarningEntity>>> objectObjectHashMap = FileConstant.objectObjectHashMap;
        HashMap<Integer, List<EquipmentWarningEntity>> integerListHashMap1 = objectObjectHashMap.get(typeId);
        List<EquipmentWarningEntity> equipmentWarningEntities = integerListHashMap1.get(warningId);
        return R.ok().put(RConstant.data, equipmentWarningEntities);
    }


}
