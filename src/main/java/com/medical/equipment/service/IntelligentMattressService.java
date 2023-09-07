package com.medical.equipment.service;


import com.medical.equipment.entity.AbnormalEntity;
import com.medical.equipment.entity.IntelligentMattressEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;

import java.util.List;
import java.util.Map;

/**
* @author 黄元龙
* @description 针对表【intelligent_mattress】的数据库操作Service
* @createDate 2022-02-16 10:26:39
*/
public interface IntelligentMattressService  {

    void add(IntelligentMattressEntity intelligentMattressEntity);

    PageResultUtils findAll(PageQuery pageQuery);

    R findLineChart(LineChartEntity lineChart);

    R findBreatheAndHR(Long userId ,Long timestamp,Long equipmentId);

    // 额外加的
    R findBreatheRateAndHeartRate(Long userId, Long equipmentId);

    R findEquipmentInfoAndUsrInfo(Long equipmentId,Long userId) ;

    R findBaseInfo( Long timestamp );

    Map findDataScreenInfo();


    List<AbnormalEntity> findAbnormalData();

    R pythonECGRecognition(Long userId, Long equipmentId);

}
