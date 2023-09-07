package com.medical.equipment.constant;


import com.medical.equipment.entity.EquipmentWarningEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileConstant {
    // 文件上传路径
    public static String UPLOAD_PATH = "";
    //文件转发地址
    public static String FORWARD_PATH = "";
    //服务器地址
    public static String SERVER_ADDRESS = "";

    //预警信息
    public static  HashMap<Integer, HashMap<Integer,List<EquipmentWarningEntity>>> objectObjectHashMap = new HashMap<>();
//    //配置文件查询的文件服务器信息
//    public static HashMap<String, MyServerInfo> serverDetail = new HashMap<>();

    public static final int IMG = 1;
    public static final int TEXT = 2;
    public static final int VIDEO = 3;
    public static final int OTHER = 4;
}
