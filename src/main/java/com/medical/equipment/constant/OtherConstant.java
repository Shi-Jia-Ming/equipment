package com.medical.equipment.constant;

import org.apache.coyote.OutputBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: OtherConstant
 * @projectName equipment
 * @description: 其他常量
 */
public class OtherConstant {
    /**
     * 默认系统用户密码
     */
    public static final String SYS_PWD = "12345678";

    public static final String status = "status";

    public static final String userInfo = "userInfo";

    public static final String equipmentInfo = "equipmentInfo";

    public static final String warnInfo = "warnInfo";



    public static final String heartRateStatus = "heartRateStatus";
    public static final String breatheStatus = "breatheStatus";
    public static final String temperatureStatus = "temperatureStatus";
    public static final String firstChannelTemperatureStatus = "firstChannelTemperatureStatus";
    public static final String secondChannelTemperatureStatus = "secondChannelTemperatureStatus";
    public static final String thirdChannelTemperatureStatus = "thirdChannelTemperatureStatus";
    public static final String fourthChannelTemperatureStatus = "fourthChannelTemperatureStatus";


    public static final String FOS = "智能床垫";
    public static final String ARM = "臂环";
    public static final String MCT = "光纤测温";
    public static final String PUM = "控程输液泵";
    public static final String EAR = "耳温传感器";




    public static final int ear_temperature_sensor_equipment = 1;
    public static final int fiber_temperature_detection = 2;
    public static final int intelligent_control_process_infusion_pump = 3;
    public static final int intelligent_mattress = 4;
    public static final int detection_arm_ring_equipment = 5;
    public static final int detection_arm_ring_equipment_terminal = 6;






    public static List<Integer> getEquipmentType() {
        List<Integer> list = new ArrayList<>();
        list.add(ear_temperature_sensor_equipment);
        list.add(fiber_temperature_detection);
        list.add(intelligent_control_process_infusion_pump);
        list.add(intelligent_mattress);
        list.add(detection_arm_ring_equipment);
        list.add(detection_arm_ring_equipment_terminal);
        return list;
    }
}
