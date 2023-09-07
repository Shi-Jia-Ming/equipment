package com.medical.equipment.service;


import com.medical.equipment.entity.EquipmentWarningEntity;

import java.util.List;

public interface EquipmentWarningService {
    List<EquipmentWarningEntity> findByTypeIdWarningId(Integer warningId, Integer typeId);
    List<EquipmentWarningEntity> findList();
}
