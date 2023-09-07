package com.medical.equipment.service;

import com.medical.equipment.entity.AbnormalEntity;
import com.medical.equipment.entity.IntelligentControlProcessInfusionPumpEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;

import java.util.List;
import java.util.Map;

/**
 * @author 黄元龙
 * @description 针对表【intelligent_control_process_infusion_pump(智能控程输液泵设备)】的数据库操作Service
 * @createDate 2022-02-18 14:04:14
 */
public interface IntelligentControlProcessInfusionPumpService {

    PageResultUtils findAll(PageQuery pageQuery);

    void add(IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity);

    R findBaseInfo(Long timestamp);

    Map findDataScreenInfo();


    R findDetails(Long userId, Long timestamp, Long equipmentId);

    R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId);

    R findLineChart(LineChartEntity lineChart);


    List<AbnormalEntity> findAbnormalData();


}
