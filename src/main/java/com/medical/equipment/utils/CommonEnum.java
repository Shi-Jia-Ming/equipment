package com.medical.equipment.utils;

import com.medical.equipment.constant.ColumnUtil;
import com.medical.equipment.entity.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CommonEnum {

    /**
     * 智能床垫设备
     *
     * @param commonDtoEntity
     * @param equipment
     * @param user
     * @return
     */
    public static IntelligentMattressEntity generateIntelligentMattressEntity(CommonDtoEntity commonDtoEntity, EquipmentEntity equipment, UserEntity user, String ts) {
        IntelligentMattressEntity intelligentMattressEntity = new IntelligentMattressEntity();
        intelligentMattressEntity.setHeartRateOriginalData(commonDtoEntity.getHOD().toString());
        intelligentMattressEntity.setBreatheOriginalData(commonDtoEntity.getROD().toString());
        intelligentMattressEntity.setMattressStatus(commonDtoEntity.getER());
        intelligentMattressEntity.setInBedStatus(commonDtoEntity.getNN());
        intelligentMattressEntity.setSleepQualitySign(commonDtoEntity.getAD());
        intelligentMattressEntity.setShake(commonDtoEntity.getSK());
        intelligentMattressEntity.setRespiratoryRate(commonDtoEntity.getAP());
        intelligentMattressEntity.setStopBreatheSign(commonDtoEntity.getAPE());
        intelligentMattressEntity.setHeartRate(commonDtoEntity.getHR());
        intelligentMattressEntity.setBreathe(commonDtoEntity.getRR());
        intelligentMattressEntity.setEquipmentId(equipment.getEquipmentId());
        intelligentMattressEntity.setUserId(user.getUserId());
        intelligentMattressEntity.setCreateTime(stringToTime(ts));
        return intelligentMattressEntity;
    }

    /**
     * 耳温传感器设备
     *
     * @param commonDtoEntity
     * @param equipment
     * @param user
     * @return
     */
    public static EarTemperatureSensorEquipmentEntity generateEarTemperatureSensorEquipmentEntity(CommonDtoEntity commonDtoEntity, EquipmentEntity equipment, UserEntity user, String ts) {
        EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = new EarTemperatureSensorEquipmentEntity();
        earTemperatureSensorEquipmentEntity.setEquipmentId(equipment.getEquipmentId());
        earTemperatureSensorEquipmentEntity.setTemperature(commonDtoEntity.getTE());
        earTemperatureSensorEquipmentEntity.setUserId(user.getUserId());
        earTemperatureSensorEquipmentEntity.setCreateTime(stringToTime(ts));
        return earTemperatureSensorEquipmentEntity;
    }

    /**
     * 光纤测温设备
     *
     * @param commonDtoEntity
     * @param equipment
     * @param user
     * @return
     */
    public static FiberTemperatureDetectionEntity generateFiberTemperatureDetectionEntity(CommonDtoEntity commonDtoEntity, EquipmentEntity equipment, UserEntity user, String ts) {
        FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = new FiberTemperatureDetectionEntity();
        fiberTemperatureDetectionEntity.setEquipmentId(equipment.getEquipmentId());
        fiberTemperatureDetectionEntity.setFirstChannelTemperature(commonDtoEntity.getTE01());
        fiberTemperatureDetectionEntity.setSecondChannelTemperature(commonDtoEntity.getTE02());
        fiberTemperatureDetectionEntity.setThirdChannelTemperature(commonDtoEntity.getTE03());
        fiberTemperatureDetectionEntity.setFourthChannelTemperature(commonDtoEntity.getTE04());
        fiberTemperatureDetectionEntity.setUserId(user.getUserId());
        fiberTemperatureDetectionEntity.setCreateTime(stringToTime(ts));
        fiberTemperatureDetectionEntity.setUpdateTime(new Date());
        return fiberTemperatureDetectionEntity;
    }

    /**
     * 智能输液泵设备
     *
     * @param commonDtoEntity
     * @param equipment
     * @param user
     * @param ts
     * @return
     */
    public static IntelligentControlProcessInfusionPumpEntity generateIntelligentControlProcessInfusionPumpEntity(CommonDtoEntity commonDtoEntity, EquipmentEntity equipment, UserEntity user, String ts) {
        IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity = new IntelligentControlProcessInfusionPumpEntity();
        intelligentControlProcessInfusionPumpEntity.setTotalLiquid(commonDtoEntity.getTOTAL());
        intelligentControlProcessInfusionPumpEntity.setAlreadyLiquid(commonDtoEntity.getCUR());
        intelligentControlProcessInfusionPumpEntity.setSpeed(commonDtoEntity.getSPEED());
        intelligentControlProcessInfusionPumpEntity.setResidueTime(commonDtoEntity.getTIME());
        intelligentControlProcessInfusionPumpEntity.setEquipmentId(equipment.getEquipmentId());
        intelligentControlProcessInfusionPumpEntity.setUserId(user.getUserId());
        intelligentControlProcessInfusionPumpEntity.setCreateTime(stringToTime(ts));
        intelligentControlProcessInfusionPumpEntity.setDumpEnergy(commonDtoEntity.getBATT());
        intelligentControlProcessInfusionPumpEntity.setWarnInfo(commonDtoEntity.getALERT());
        return intelligentControlProcessInfusionPumpEntity;
    }


    /**
     * 臂环设备
     *
     * @param commonDtoEntity
     * @param equipment
     * @param user
     * @param ts
     * @return
     */
    public static DetectionArmRingEquipmentEntity generateDetectionArmRingEquipmentEntity(CommonDtoEntity commonDtoEntity, EquipmentEntity equipment, UserEntity user, String ts) {
        DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity = new DetectionArmRingEquipmentEntity();
        detectionArmRingEquipmentEntity.setStepNumber(commonDtoEntity.getST());
        detectionArmRingEquipmentEntity.setTemperature(commonDtoEntity.getTE());
        detectionArmRingEquipmentEntity.setHeartRate(commonDtoEntity.getHR());
        detectionArmRingEquipmentEntity.setEquipmentId(equipment.getEquipmentId());
        detectionArmRingEquipmentEntity.setUserId(user.getUserId());
        detectionArmRingEquipmentEntity.setCreateTime(stringToTime(ts));
        detectionArmRingEquipmentEntity.setArmId(commonDtoEntity.getID());
        detectionArmRingEquipmentEntity.setUpdateTime(new Date());
        return detectionArmRingEquipmentEntity;
    }


    public static Date stringToTime(String time) {
        Date date = new Date(Long.parseLong(time));// 时间戳转换成时间
        return date;
    }

    public static LineChartEntity generateFormat(LineChartEntity lineChart) {
        final String split = "-";
        Integer countString = countString(lineChart.getStartTime(), split);
        if (countString == 0) {
            //年份
            lineChart.setDataFormat("%Y");
        }
        if (countString == 1) {
            //月份
            lineChart.setDataFormat("%Y-%m");
        }
        if (countString == 2) {
            //到天
            lineChart.setDataFormat("%Y-%m-%d");
        }
        return lineChart;
    }

    public static Integer countString(String str, String s) {
        if (str == null) {
            str = new Date().toString();
        }
        int count = 0, len = str.length();
        while (str.indexOf(s) != -1) {
            str = str.substring(str.indexOf(s) + 1, str.length());
            count++;
        }
        System.out.println("此字符串有" + count + "个" + s);
        return count;
    }

    /**
     * 获取设置的用户信息和设备信息
     *
     * @param map
     * @param user
     * @param equipment
     * @return
     */
    public static Map<String, Object> getMap(Map<String, Object> map, UserEntity user, EquipmentEntity equipment) {

        map.put(ColumnUtil.getFieldName(UserEntity::getUserId), user.getUserId());
        map.put(ColumnUtil.getFieldName(UserEntity::getUserName), user.getUserName());

        map.put(ColumnUtil.getFieldName(EquipmentEntity::getEquipmentCode), equipment.getEquipmentCode());
        map.put(ColumnUtil.getFieldName(EquipmentEntity::getEquipmentId), equipment.getEquipmentId());

        return map;
    }


    public static TimeEntity getRangeTime(Long time) {
        TimeEntity timeEntity = new TimeEntity();
        long second = 5* 1000;

        String result1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time+second));
        String result2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time-second));
        timeEntity.setEndTime(result1);
        timeEntity.setStartTime(result2);
        return timeEntity;
    }





}
