package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.EarTemperatureSensorEquipmentEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.TimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【ear_temperature_sensor_equipment(耳温传感器设备)】的数据库操作Mapper
* @Entity generator.domain.EarTemperatureSensorEquipment
*/
@Mapper
public interface EarTemperatureSensorEquipmentMapper extends BaseMapper<EarTemperatureSensorEquipmentEntity> {


    EarTemperatureSensorEquipmentEntity findBaseInfo(@Param("equipmentId") Long equipmentId, @Param("timeEntity") TimeEntity timeEntity);

    EarTemperatureSensorEquipmentEntity findTemperature(@Param("userId") Long userId, @Param("timeEntity") TimeEntity timeEntity, @Param("equipmentId") String equipmentId);

    List<Map<String,String>> findLineChart(LineChartEntity lineChart);

    Map findDataScreenInfo(@Param("date") Date date, @Param("equipmentId") Long equipmentId);
}
