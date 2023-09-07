package com.medical.equipment.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.constant.ColumnUtil;
import com.medical.equipment.constant.DetectionConstant;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.DetectionArmRingEquipmentMapper;
import com.medical.equipment.service.DetectionArmRingEquipmentService;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.UserService;
import com.medical.equipment.utils.CommonEnum;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class DetectionArmRingEquipmentServiceImpl implements DetectionArmRingEquipmentService {


    @Resource
    private DetectionArmRingEquipmentMapper detectionArmRingEquipmentMapper;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private UserService userService;

    public final static String split = "-";


    @Override
    public PageResultUtils findAll(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<DetectionArmRingEquipmentEntity> detectionArmRingEquipmentEntities = detectionArmRingEquipmentMapper.selectList(enabled());
        PageInfo<DetectionArmRingEquipmentEntity> detectionArmRingEquipmentEntityPageInfo = new PageInfo<>(detectionArmRingEquipmentEntities);
        return new PageResultUtils(detectionArmRingEquipmentEntityPageInfo);
    }

    @Override
    public void add(DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity) {
        try {
            if(detectionArmRingEquipmentEntity.getTemperature()>=36){
                detectionArmRingEquipmentMapper.insert(detectionArmRingEquipmentEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("臂环设备信息新增失败！");
        }
    }

    @Override
    public R findBaseInfo(Long timestamp) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        //查找设备信息
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.detection_arm_ring_equipment);
        equipmentByType.forEach(equipment -> {
            Map<String, Object> map = new HashMap();
            //如果设备在线 就查询当前设备的数据  如果不在线 就查询当前设备最近的数据
                TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
            Map<String, Object> details = details(1, new DetectionArmRingEquipmentEntity(), equipment, map, new UserEntity(), rangeTime);
            map = (Map<String, Object>) details.get("map");

            map = CommonEnum.getMap(map, (UserEntity) details.get("user"), equipment);

            DetectionArmRingEquipmentEntity armRingEquipment = (DetectionArmRingEquipmentEntity) details.get("armRingEquipment");
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getStepNumber),armRingEquipment.getStepNumber());
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getTemperature),armRingEquipment.getTemperature());
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getHeartRate),armRingEquipment.getHeartRate());

            mapList.add(map);
        });
        return R.ok().put(RConstant.data, mapList);
    }


    public Map<String, Object> details(Integer count, DetectionArmRingEquipmentEntity armRingEquipment, EquipmentEntity equipment, Map<String, Object> map, UserEntity user, TimeEntity timeEntity) {
        Map<String, Object> resultMap = new HashMap<>();

        armRingEquipment = detectionArmRingEquipmentMapper.findBaseInfo(equipment.getEquipmentId(), timeEntity);
        if (armRingEquipment != null) {
            Integer temperatureStatus = DetectionConstant.armletTemperatureInfo(armRingEquipment.getTemperature());
            Integer heartRateStatus = DetectionConstant.armletHeartRateInfo(armRingEquipment.getHeartRate());
            int max = Math.max(temperatureStatus, heartRateStatus);
            map.put(OtherConstant.status, max);
        } else {
            count++;
            armRingEquipment = new DetectionArmRingEquipmentEntity();
            if (count <= 3) {
                return details(count, armRingEquipment, equipment, map, user, timeEntity);
            }
            if(equipment.getOnline()==1){
                map.put(OtherConstant.status, 3);
            }else {
                map.put(OtherConstant.status, 0);
            }
        }
        DetectionArmRingEquipmentEntity baseInfo = detectionArmRingEquipmentMapper.findBaseInfo(equipment.getEquipmentId(), null);
        if(baseInfo!=null)
        user = userService.findById(baseInfo.getUserId());
        resultMap.put("user", user);
        resultMap.put("map", map);
        resultMap.put("armRingEquipment", armRingEquipment);
        return resultMap;
    }

    @Override
    public R findDetailsInfo(Long userId, Long timestamp,Long equipmentId) {
        TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);

        DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity = detectionArmRingEquipmentMapper.findDetailsInfo(userId, rangeTime,equipmentId);
        Map<String, Object> map = new HashMap<String, Object>();

        if (detectionArmRingEquipmentEntity != null) {
            Integer heartRateStatus = DetectionConstant.armletHeartRateInfo(detectionArmRingEquipmentEntity.getHeartRate());
            Integer temperatureStatus = DetectionConstant.armletTemperatureInfo(detectionArmRingEquipmentEntity.getTemperature());
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getHeartRate), detectionArmRingEquipmentEntity.getHeartRate());
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getTemperature), detectionArmRingEquipmentEntity.getTemperature());
            map.put(OtherConstant.temperatureStatus, temperatureStatus);
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getHeartRate), detectionArmRingEquipmentEntity.getHeartRate());
            map.put(OtherConstant.heartRateStatus, heartRateStatus);
            map.put(ColumnUtil.getFieldName(DetectionArmRingEquipmentEntity::getStepNumber), detectionArmRingEquipmentEntity.getStepNumber());
        }else {
        EquipmentEntity equipment = equipmentService.findEquipmentById(equipmentId);
        if(equipment.getOnline()==1){
            map.put(OtherConstant.temperatureStatus, 3);
            map.put(OtherConstant.heartRateStatus, 3);
        }else {
            map.put(OtherConstant.temperatureStatus, 1);
            map.put(OtherConstant.heartRateStatus, 1);
        }
        }
        return R.ok().put(RConstant.data, map);

    }

    @Override
    public R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId) {
        UserEntity user = userService.findById(userId);
        EquipmentEntity equipment = equipmentService.findEquipmentById(equipmentId);
        Map<String, Object> map = new HashMap();
        map.put(OtherConstant.userInfo, user);
        map.put(OtherConstant.equipmentInfo, equipment);
        return R.ok().put(RConstant.data, map);

    }

    @Override
    public R findLineChart(LineChartEntity lineChart) {
        List<String> time = new ArrayList<>();
        List<String> heartRate = new ArrayList<>();
        List<String> temperature = new ArrayList<>();

        lineChart = CommonEnum.generateFormat(lineChart);

        List<Map<String, String>> list = detectionArmRingEquipmentMapper.findLineChart(lineChart);
        list.forEach(map -> {
            time.add( map.get("time"));
            heartRate.add(map.get("countHR"));
            temperature.add(map.get("countT"));
        });
        Map map = new HashMap();
        map.put("time", time);
        map.put("heartRate", heartRate);
        map.put("temperature", temperature);
        return R.ok().put(RConstant.data, map);
    }

    @Override
    public Map findDataScreenInfo() {
        AtomicInteger warn = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();


        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.detection_arm_ring_equipment);
        equipmentByType.forEach(equipment -> {
            List<Integer> count = new ArrayList<>();
            Map map = detectionArmRingEquipmentMapper.findDataScreenInfo(new Date(), equipment.getEquipmentId());
            if (map != null) {
                Integer temperature = DetectionConstant.armletTemperatureInfo((Double) map.get("temperature"));
                Integer heartRate = DetectionConstant.armletHeartRateInfo((Integer) map.get("heart_rate"));
                count.add(temperature);
                count.add(heartRate);
                Integer max = Collections.max(count);
                switch (max) {
                    case 2:
                        warn.set(warn.get() + 1);
                        break;
                    case 3:
                        abnormal.set(abnormal.get() + 1);
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


        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.detection_arm_ring_equipment);
        equipmentEntityList.forEach(equipment -> {

//            EquipmentEntity equipmentEntity = equipmentService.findEquipmentById(equipment.getEquipmentId());

                //去查询智能床垫当前的数据
                DetectionArmRingEquipmentEntity armRingEquipment = detectionArmRingEquipmentMapper.selectOne(enabled().orderByDesc(BaseEntity::getCreateTime).last("limit 1"));
                if (armRingEquipment != null) {

                    UserEntity user = userService.findById(armRingEquipment.getUserId());

                    AbnormalEntity abnormalEntity = new AbnormalEntity();
                    abnormalEntity.setTime(armRingEquipment.getCreateTime());
                    abnormalEntity.setUserName(user.getUserName());
                    abnormalEntity.setInfo("无");


                    Integer temperatureStatus = DetectionConstant.TemperatureInfo(armRingEquipment.getTemperature());
                    switch (temperatureStatus) {
                        case 2:
                            abnormalEntity.setAbnormalType("体温过高");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("体温异常");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);

                            break;
                    }
                    Integer heartRateStatus = DetectionConstant.armletHeartRateInfo(armRingEquipment.getHeartRate());
                    switch (heartRateStatus) {
                        case 2:
                            abnormalEntity.setAbnormalType("心率过快");
                            abnormalEntities.add(abnormalEntity);

                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("心率异常");
                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                }

        });
        return abnormalEntities;
    }

    public LambdaQueryWrapper<DetectionArmRingEquipmentEntity> enabled() {
        LambdaQueryWrapper<DetectionArmRingEquipmentEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DetectionArmRingEquipmentEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }


}
