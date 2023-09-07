package com.medical.equipment.service;

import com.medical.equipment.entity.AbnormalEntity;
import com.medical.equipment.entity.EarTemperatureSensorEquipmentEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;

import java.util.List;
import java.util.Map;

/**
* @author 黄元龙
* @description 针对表【ear_temperature_sensor_equipment(耳温传感器设备)】的数据库操作Service
* @createDate 2022-02-18 13:34:12
*/
public interface EarTemperatureSensorEquipmentService{

    PageResultUtils findAll(PageQuery pageQuery);

    void add(EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity);

    R findBaseInfo(Long timestamp);

    R findTemperature(Long userId,Long timestamp,String equipmentId);

    R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId);

    R findLineChart(LineChartEntity lineChart);

    Map findDataScreenInfo();

    List<AbnormalEntity> findAbnormalData();

}
