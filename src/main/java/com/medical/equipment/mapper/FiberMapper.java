package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.FiberTemperatureDetectionEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.entity.TimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FiberMapper extends BaseMapper<FiberTemperatureDetectionEntity> {
    FiberTemperatureDetectionEntity findBaseInfo(@Param("equipmentId") Long equipmentId, @Param("timeEntity")TimeEntity timeEntity);

    FiberTemperatureDetectionEntity findChannelInfo(@Param("userId") Long userId, @Param("timeEntity") TimeEntity timeEntity, @Param("equipmentId") String equipmentId);

    List<Map<String,String>> findLineChart(LineChartEntity lineChart);

    Map findDataScreenInfo(@Param("date") Date date,@Param("equipmentId") Long equipmentId);
}
