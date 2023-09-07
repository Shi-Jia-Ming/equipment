package com.medical.equipment.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.medical.equipment.entity.EquipmentWarningEntity;
import com.medical.equipment.mapper.EquipmentWarningMapper;
import com.medical.equipment.service.EquipmentWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EquipmentWarningServiceImpl implements EquipmentWarningService {

    @Autowired
    EquipmentWarningMapper equipmentWarningMapper;

    @Override
    public List<EquipmentWarningEntity> findByTypeIdWarningId(Integer warningId, Integer typeId) {
        List<EquipmentWarningEntity> list = new LambdaQueryChainWrapper<>(equipmentWarningMapper)
                .eq(EquipmentWarningEntity::getEquipmentWarningId, warningId)
                .eq(EquipmentWarningEntity::getEquipmentTypeId, typeId)
                .list();
        return list;
    }

    @Override
    public List<EquipmentWarningEntity> findList() {
        List<EquipmentWarningEntity> list = new LambdaQueryChainWrapper<>(equipmentWarningMapper)
                .list();
        return list;
    }
}
