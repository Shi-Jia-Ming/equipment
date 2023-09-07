package com.medical.equipment.constant;


import com.medical.equipment.entity.EquipmentWarningEntity;

import javax.print.attribute.standard.Finishings;
import java.util.HashMap;
import java.util.List;

public class DetectionConstant {
    static HashMap<Integer, HashMap<Integer, List<EquipmentWarningEntity>>> objectObjectHashMap = FileConstant.objectObjectHashMap;

    //床垫心率
    public static Integer mattressHeartRateInfo(Integer HR, Integer inBedStatus) {
        HashMap<Integer, List<EquipmentWarningEntity>> integerListHashMap = objectObjectHashMap.get(1);
        List<EquipmentWarningEntity> equipmentWarningEntities = integerListHashMap.get(1);
        for (EquipmentWarningEntity equipmentWarningEntity : equipmentWarningEntities) {

            if (equipmentWarningEntity.getState().equals("正常")) {
                if (HR <= equipmentWarningEntity.getMax() && HR>equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
            if (equipmentWarningEntity.getState().equals("偏高")) {
                if (HR <= equipmentWarningEntity.getMax() && HR > equipmentWarningEntity.getMin()) {
                    return 2;
                }
            }
            if (equipmentWarningEntity.getState().equals("异常")) {
                if (HR > equipmentWarningEntity.getMax() ) {
                    return 3;
                }
            }
            if (inBedStatus == 2) {
                if (HR <= equipmentWarningEntity.getMin()) {
                    return 3;
                }
            } else if (inBedStatus == 0 || inBedStatus == 1) {
                if (HR <= equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
        }

        return 1;
    }
    //床垫呼吸
    public static Integer mattressBreathe(Integer B,Integer inBedStatus) {
        HashMap<Integer, List<EquipmentWarningEntity>> integerListHashMap = objectObjectHashMap.get(1);
        List<EquipmentWarningEntity> equipmentWarningEntities = integerListHashMap.get(2);
        for (EquipmentWarningEntity equipmentWarningEntity : equipmentWarningEntities) {

            if (equipmentWarningEntity.getState().equals("正常")) {
                if (B <= equipmentWarningEntity.getMax() && B>equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
            if (equipmentWarningEntity.getState().equals("偏高")) {
                if (B <= equipmentWarningEntity.getMax() && B > equipmentWarningEntity.getMin()) {
                    return 2;
                }
            }
            if (inBedStatus == 2) {
                if (B <= equipmentWarningEntity.getMin()) {
                    return 3;
                }
            } else if (inBedStatus == 0 || inBedStatus == 1) {
                if (B <= equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
            if (equipmentWarningEntity.getState().equals("异常")) {
                if (B > equipmentWarningEntity.getMax() || B <=equipmentWarningEntity.getMin()) {
                    return 3;
                }
            }
        }
        return 1;
    }

    public static Integer TemperatureInfo(Double T) {
        if (T >= 35 && T <= 37.5) {
            return 1;
        }
        if (T <= 38.5 && T > 37.5) {
            return 2;
        }
        if (T > 38.5 || T < 35) {
            return 3;
        }
        return null;
    }



    //臂环温度
    public static Integer armletTemperatureInfo(Double T) {
        HashMap<Integer, List<EquipmentWarningEntity>> integerListHashMap = objectObjectHashMap.get(2);
        List<EquipmentWarningEntity> equipmentWarningEntities = integerListHashMap.get(3);
        for (EquipmentWarningEntity equipmentWarningEntity : equipmentWarningEntities) {
            if (equipmentWarningEntity.getState().equals("正常")) {
                if (T <= equipmentWarningEntity.getMax() && T>=equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
            if (equipmentWarningEntity.getState().equals("偏高")) {
                if (T <= equipmentWarningEntity.getMax() && T > equipmentWarningEntity.getMin()) {
                    return 2;
                }
            }
            if (equipmentWarningEntity.getState().equals("异常")) {
                if (T > equipmentWarningEntity.getMax() || T <= equipmentWarningEntity.getMin()) {
                    return 3;
                }
            }
        }
        return 1;
    }

    //臂环心率
    public static Integer armletHeartRateInfo(Integer HR) {
        HashMap<Integer, List<EquipmentWarningEntity>> integerListHashMap = objectObjectHashMap.get(2);
        List<EquipmentWarningEntity> equipmentWarningEntities = integerListHashMap.get(1);
        for (EquipmentWarningEntity equipmentWarningEntity : equipmentWarningEntities) {
            if (equipmentWarningEntity.getState().equals("正常")) {
                if (HR <= equipmentWarningEntity.getMax() && HR>equipmentWarningEntity.getMin()) {
                    return 1;
                }
            }
            if (equipmentWarningEntity.getState().equals("偏高")) {
                if (HR <= equipmentWarningEntity.getMax() && HR > equipmentWarningEntity.getMin()) {
                    return 2;
                }
            }
            if (equipmentWarningEntity.getState().equals("异常")) {
                if (HR > equipmentWarningEntity.getMax() ||  HR <=equipmentWarningEntity.getMin()) {
                    return 3;
                }
            }
        }
        return 1;
    }

}
