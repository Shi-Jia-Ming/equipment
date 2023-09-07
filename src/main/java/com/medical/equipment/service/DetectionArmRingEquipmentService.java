package com.medical.equipment.service;

import com.medical.equipment.entity.AbnormalEntity;
import com.medical.equipment.entity.DetectionArmRingEquipmentEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;

import java.util.List;
import java.util.Map;

/**
* @author 黄元龙
* @description 针对表【detection_arm_ring_equipment】的数据库操作Service
* @createDate 2022-02-17 13:24:46
*/
public interface DetectionArmRingEquipmentService {

    PageResultUtils findAll(PageQuery pageQuery);

    void add(DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity);

    R findBaseInfo( Long timestamp);


    R findDetailsInfo(Long userId,Long timestamp,Long equipmentId);

    R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId);

    R findLineChart(LineChartEntity lineChart);

    Map findDataScreenInfo();

    List<AbnormalEntity> findAbnormalData();


}
