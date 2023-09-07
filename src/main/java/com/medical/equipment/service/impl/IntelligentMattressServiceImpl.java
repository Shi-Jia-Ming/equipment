package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.constant.ColumnUtil;
import com.medical.equipment.constant.DetectionConstant;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.IntelligentMattressMapper;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.IntelligentMattressService;
import com.medical.equipment.service.UserService;
import com.medical.equipment.utils.CommonEnum;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class IntelligentMattressServiceImpl implements IntelligentMattressService {


    @Resource
    private IntelligentMattressMapper intelligentMattressMapper;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private UserService userService;


    @Override
    public void add(IntelligentMattressEntity intelligentMattressEntity) {
        try {
            intelligentMattressMapper.insert(intelligentMattressEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("插入智能床垫设备数据信息失败！");
        }
    }

    @Override
    public PageResultUtils findAll(PageQuery pageQuery) {

        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        if (pageQuery.getInfo() != null) {
        }
        List<IntelligentMattressEntity> entities = intelligentMattressMapper.selectList(enabled());
        PageInfo<IntelligentMattressEntity> intelligentMattressEntityPageInfo = new PageInfo<>(entities);
        return new PageResultUtils(intelligentMattressEntityPageInfo);
    }

    @Override
    public R findLineChart(LineChartEntity lineChart) {
        List<String> time = new ArrayList<>();
        List<String> heartRate = new ArrayList<>();
        List<String> breathe = new ArrayList<>();

        lineChart = CommonEnum.generateFormat(lineChart);

        //这里用Map<String, String>承接数据会报类型转换的错
        List<Map<String, Object>> list = intelligentMattressMapper.findLineChart(lineChart);

        list.forEach(map -> {
            time.add((String)map.get("time"));
//            System.out.println(String.valueOf(map.get("countHR")));
//            System.out.println(String.valueOf(map.get("countB")));
            heartRate.add(String.valueOf(map.get("countHR")));
            breathe.add(String.valueOf(map.get("countB")));
        });
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("time", time);
        resultMap.put("heartRate", heartRate);
        resultMap.put("breathe", breathe);
        return R.ok().put(RConstant.data, resultMap);
    }

    @Override
    public R findBreatheAndHR(Long userId, Long timestamp,Long equipmentId) {


        Map<String, Object> objectMap = new HashMap<>();

        TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);

        IntelligentMattressEntity intelligentMattressEntity = intelligentMattressMapper.findBreatheAndHR(userId, rangeTime,equipmentId);

        if (intelligentMattressEntity != null) {
            Integer breathe = intelligentMattressEntity.getBreathe();
            Integer breatheStatus = DetectionConstant.mattressBreathe(breathe,intelligentMattressEntity.getInBedStatus());

            Integer heartRate = intelligentMattressEntity.getHeartRate();
            Integer heartRateStatus = DetectionConstant.mattressHeartRateInfo(heartRate,intelligentMattressEntity.getInBedStatus());

            objectMap.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getHeartRate), heartRate);
            objectMap.put(OtherConstant.heartRateStatus, heartRateStatus);
            objectMap.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getBreathe), breathe);
            objectMap.put(OtherConstant.breatheStatus, breatheStatus);

//            String heartRateOriginalData = intelligentMattressEntity.getHeartRateOriginalData();
//            objectMap.put("heartRateOriginalData", heartRateOriginalData);
//            System.out.println("heartRateOriginalData:"+heartRateOriginalData);
        }


        return R.ok().put(RConstant.data, objectMap);
    }

    // 只查找最新一条的数据
    @Override
    public R findBreatheRateAndHeartRate(Long userId, Long equipmentId) {
        Map<String, Object> objectMap = new HashMap<>();

        // 直接获取最新的一条数据
        IntelligentMattressEntity intelligentMattressEntity = intelligentMattressMapper.findBreatheRateAndHeartRate(userId, equipmentId);

        if (intelligentMattressEntity != null) {
            Integer breathe = intelligentMattressEntity.getBreathe();
            Integer breatheStatus = DetectionConstant.mattressBreathe(breathe, intelligentMattressEntity.getInBedStatus());

            Integer heartRate = intelligentMattressEntity.getHeartRate();
            Integer heartRateStatus = DetectionConstant.mattressHeartRateInfo(heartRate, intelligentMattressEntity.getInBedStatus());

            objectMap.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getHeartRate), heartRate);
            objectMap.put(OtherConstant.heartRateStatus, heartRateStatus);
            objectMap.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getBreathe), breathe);
            objectMap.put(OtherConstant.breatheStatus, breatheStatus);

            objectMap.put("heartRateOriginalData",intelligentMattressEntity.getHeartRateOriginalData());
            objectMap.put("breatheOriginalData",intelligentMattressEntity.getBreatheOriginalData());

        }
        return R.ok().put(RConstant.data, objectMap);
    }

    @Override
    public R findEquipmentInfoAndUsrInfo(Long equipmentId, Long userId) {

        UserEntity user = userService.findById(userId);
        EquipmentEntity equipment = equipmentService.findEquipmentById(equipmentId);
        Map<String, Object> map = new HashMap<>();
        map.put(OtherConstant.userInfo, user);
        map.put(OtherConstant.equipmentInfo, equipment);
        return R.ok().put(RConstant.data, map);
    }

    @Override
    public R findBaseInfo(Long timestamp) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        //查询所有的智能床垫设备id
        List<EquipmentEntity> equipmentEntities = equipmentService.findEquipmentByType(OtherConstant.intelligent_mattress);
        equipmentEntities.forEach(equipment -> {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> details;

                TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
                details = details(1, new IntelligentMattressEntity(), equipment, map, new UserEntity(), rangeTime);

            map = (Map<String, Object>) details.get("map");
            map = CommonEnum.getMap(map, (UserEntity) details.get("user"), equipment);
            IntelligentMattressEntity intelligentMattressEntity = (IntelligentMattressEntity) details.get("intelligentMattressEntity");
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getHeartRate), intelligentMattressEntity.getHeartRate());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getBreathe), intelligentMattressEntity.getBreathe());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getStopBreatheSign), intelligentMattressEntity.getStopBreatheSign());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getInBedStatus), intelligentMattressEntity.getInBedStatus());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getShake), intelligentMattressEntity.getShake());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getRespiratoryRate), intelligentMattressEntity.getRespiratoryRate());
            map.put(ColumnUtil.getFieldName(IntelligentMattressEntity::getMattressStatus), intelligentMattressEntity.getMattressStatus());

            mapList.add(map);

        });
        return R.ok().put(RConstant.data, mapList);
    }

    public Map<String, Object> details(Integer count, IntelligentMattressEntity intelligentMattressEntity, EquipmentEntity equipment, Map<String, Object> map, UserEntity user, TimeEntity timeEntity) {

        Map<String, Object> resultMap = new HashMap<>();

        //如果能持续接收到最新的设备数据，则代表设备在线，否则离线
        intelligentMattressEntity = intelligentMattressMapper.findBaseInfo(equipment.getEquipmentId(), timeEntity);
        if (intelligentMattressEntity != null) {
            Integer heartRateStatus = DetectionConstant.mattressHeartRateInfo(intelligentMattressEntity.getHeartRate(),intelligentMattressEntity.getInBedStatus());
            Integer breatheStatus = DetectionConstant.mattressBreathe(intelligentMattressEntity.getBreathe(),intelligentMattressEntity.getInBedStatus());
            int max = Math.max(heartRateStatus, breatheStatus);
            map.put(OtherConstant.status, max);

        } else {
            intelligentMattressEntity = new IntelligentMattressEntity();
            map.put(OtherConstant.status, 0);
        }
        IntelligentMattressEntity baseInfo = intelligentMattressMapper.findBaseInfo(equipment.getEquipmentId(), null);
        if(baseInfo!=null)
        user = userService.findById(baseInfo.getUserId());
        resultMap.put("user", user);
        resultMap.put("map", map);
        resultMap.put("intelligentMattressEntity", intelligentMattressEntity);
        return resultMap;
    }

    @Override
    public Map findDataScreenInfo() {

        AtomicInteger warn = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();

        //查找所有智能床垫设备
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.intelligent_mattress);
        equipmentByType.forEach(equipment -> {
            List<Integer> count = new ArrayList<>();
            Map map = intelligentMattressMapper.findDataScreenInfo(new Date(), equipment.getEquipmentId());
            if (map != null) {
                Integer breathe = DetectionConstant.mattressBreathe((Integer) map.get(ColumnUtil.getFieldName(IntelligentMattressEntity::getBreathe)),0);
                Integer heartRate = DetectionConstant.mattressHeartRateInfo((Integer) map.get(ColumnUtil.modelNameToLine(IntelligentMattressEntity::getHeartRate)),0);
                count.add(breathe);
                count.add(heartRate);
                Integer max = Collections.max(count);
                switch (max) {
                    case 2:
                        warn.set(warn.get() + 1);
                        break;
                    case 3:
                        abnormal.set(abnormal.get() + 1);
                        break;
                }
            }
        });
        Map map = new HashMap();
        map.put("normal", equipmentByType.size() - warn.get() - abnormal.get());
        map.put("warn", warn.get());
        map.put("abnormal", abnormal.get());
        return map;
    }

    @Override
    public List<AbnormalEntity> findAbnormalData() {


        List<AbnormalEntity> abnormalEntities = new ArrayList<>();

        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.intelligent_mattress);
        equipmentEntityList.forEach(equipment -> {
            Map<String, Object> mattressMap = new HashMap<>();
            EquipmentEntity equipmentEntity = equipmentService.findEquipmentById(equipment.getEquipmentId());

                //去查询智能床垫当前的数据

                //这个逻辑是有问题的 应该是根据我们的设备 去寻找
                IntelligentMattressEntity intelligentMattressEntity = intelligentMattressMapper.selectOne(enabled().
                        orderByDesc(BaseEntity::getCreateTime)
                        .last("limit 1"));
                if (intelligentMattressEntity != null) {
                    AbnormalEntity abnormalEntity = new AbnormalEntity();
                    Integer breatheStatus = DetectionConstant.mattressBreathe(intelligentMattressEntity.getBreathe(),intelligentMattressEntity.getInBedStatus());

                    UserEntity user = userService.findById(intelligentMattressEntity.getUserId());
                    abnormalEntity.setUserName(user.getUserName());
                    abnormalEntity.setTime(intelligentMattressEntity.getCreateTime());
                    abnormalEntity.setInfo("无");

                    switch (breatheStatus) {
                        case 2:
                            abnormalEntity.setAbnormalType("呼吸频率过快");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("呼吸频率异常");
                            abnormalEntity.setStatus(3);
                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                    Integer heartRateStatus = DetectionConstant.mattressHeartRateInfo(intelligentMattressEntity.getHeartRate(),intelligentMattressEntity.getInBedStatus());
                    switch (heartRateStatus) {
                        case 2:
                            abnormalEntity.setAbnormalType("心率过快");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setStatus(3);
                            abnormalEntity.setAbnormalType("心率异常");
                            abnormalEntities.add(abnormalEntity);

                            break;
                    }
                }

        });
        return abnormalEntities;
    }


    public LambdaQueryWrapper<IntelligentMattressEntity> enabled() {
        LambdaQueryWrapper<IntelligentMattressEntity> intelligentMattressEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        intelligentMattressEntityLambdaQueryWrapper.eq(IntelligentMattressEntity::getEnabled, 1);
        return intelligentMattressEntityLambdaQueryWrapper;
    }

    public R pythonECGRecognition(Long userId, Long equipmentId){
        List<String> lines = new ArrayList<>();
        try {
            // 调用Python程序
            String[] command = {"C:\\Users\\86133\\AppData\\Local\\Programs\\Python\\Python38\\python.exe", "C:\\Users\\86133\\Desktop\\ECG\\mit-bih_ecg_recognition\\main_tf.py"};
//            Process process = Runtime.getRuntime().exec(command);

            // 切换到指定目录，否则不能正常输出内容
            String workingDir = "C:\\Users\\86133\\Desktop\\ECG\\mit-bih_ecg_recognition";
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(workingDir));

            Process process = pb.start();

            // 获取输出的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pythonOutput", lines);
        return R.ok().put(RConstant.data, map);
    }


}
