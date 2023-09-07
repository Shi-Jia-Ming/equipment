package com.medical.equipment.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.IntelligentMattressEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.TimeEntity;
import com.medical.equipment.utils.Sql;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【intelligent_mattress】的数据库操作Mapper
* @Entity generator.domain.IntelligentMattress
*/
public interface IntelligentMattressMapper extends BaseMapper<IntelligentMattressEntity> {


    @Sql
    List<Map<String,Object>> findLineChart(LineChartEntity lineChart);

    IntelligentMattressEntity findBreatheAndHR(@Param("userId") Long userId, @Param("timeEntity") TimeEntity timeEntity, @Param("equipmentId") Long equipmentId);

    IntelligentMattressEntity findBreatheRateAndHeartRate(@Param("userId")Long userId, @Param("equipmentId")Long equipmentId);

    Long findEquipmentId(Long userId);

    IntelligentMattressEntity findBaseInfo(@Param("equipmentId") Long equipmentId, @Param("timeEntity")TimeEntity timeEntity);

    Map findDataScreenInfo(@Param("date") Date date, @Param("equipmentId") Long equipmentId);



}
