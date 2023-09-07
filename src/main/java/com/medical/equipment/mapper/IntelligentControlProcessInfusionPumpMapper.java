package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.IntelligentControlProcessInfusionPumpEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.TimeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【intelligent_control_process_infusion_pump(智能控程输液泵设备)】的数据库操作Mapper
* @Entity generator.domain.IntelligentControlProcessInfusionPump
*/
public interface IntelligentControlProcessInfusionPumpMapper extends BaseMapper<IntelligentControlProcessInfusionPumpEntity> {


    Map findDataScreenInfo(@Param("date") Date date, @Param("equipmentId") Long equipmentId);

    IntelligentControlProcessInfusionPumpEntity findBaseInfo(@Param("equipmentId") Long equipmentId, @Param("timeEntity")TimeEntity timeEntity);

    IntelligentControlProcessInfusionPumpEntity findDetails(@Param("userId") Long userId, @Param("timeEntity") TimeEntity timeEntity, @Param("equipmentId") Long equipmentId);

    List<Map<String,String>> findLineChart(LineChartEntity lineChart);
}
