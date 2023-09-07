package com.medical.equipment.service;

import com.github.pagehelper.PageInfo;
import com.medical.equipment.entity.EquipmentEntity;
import com.medical.equipment.utils.PageResultUtils;

import java.util.List;

public interface EquipmentService {

    EquipmentEntity findEquipmentById(Long equipmentId);

    void addEquipment(EquipmentEntity equipment);

    void delEquipment(Long id);

    PageResultUtils findAll();

    void update(EquipmentEntity equipment);

    List<EquipmentEntity> findEquipmentByType(Integer type);
}
