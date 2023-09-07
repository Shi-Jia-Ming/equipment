package com.medical.equipment.service;

import com.medical.equipment.entity.AbnormalEntity;
import com.medical.equipment.entity.FiberTemperatureDetectionEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;

import java.util.List;
import java.util.Map;

public interface FiberService  {
    List<FiberTemperatureDetectionEntity> findByUserId(Long userId);

    PageResultUtils findAll(PageQuery pageQuery);

    void add(FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity);

    R findBaseInfo(Long timestamp);

    R findChannelInfo(Long userId,Long timestamp,String equipmentId);

    R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId);

    R findLineChart(LineChartEntity lineChart);

    Map findDataScreenInfo();

    List<AbnormalEntity> findAbnormalData();


}
