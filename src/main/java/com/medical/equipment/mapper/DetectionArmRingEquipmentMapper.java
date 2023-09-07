package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.DetectionArmRingEquipmentEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.TimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @description 针对表【detection_arm_ring_equipment】的数据库操作Mapper
* @Entity generator.domain.DetectionArmRingEquipment
*/
@Mapper
public interface DetectionArmRingEquipmentMapper extends BaseMapper<DetectionArmRingEquipmentEntity> {


    DetectionArmRingEquipmentEntity findBaseInfo(@Param("equipmentId") Long equipmentId ,@Param("timeEntity") TimeEntity timeEntity);

    List<Map<String,String>> findLineChart( LineChartEntity lineChart);

    Map findDataScreenInfo(@Param("date") Date date,@Param("equipmentId") Long equipmentId);

    DetectionArmRingEquipmentEntity findDetailsInfo(@Param("userId") Long userId,@Param("timeEntity") TimeEntity timeEntity,@Param("equipmentId") Long equipmentId);
}
