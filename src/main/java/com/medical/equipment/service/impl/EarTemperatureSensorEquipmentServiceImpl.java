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
import com.medical.equipment.mapper.EarTemperatureSensorEquipmentMapper;
import com.medical.equipment.service.EarTemperatureSensorEquipmentService;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.UserService;
import com.medical.equipment.utils.CommonEnum;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class EarTemperatureSensorEquipmentServiceImpl implements EarTemperatureSensorEquipmentService {


    @Resource
    private EarTemperatureSensorEquipmentMapper earTemperatureSensorEquipmentMapper;

    @Resource
    private EquipmentService equipmentService;
    @Resource
    private UserService userService;

    @Override
    public PageResultUtils findAll(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<EarTemperatureSensorEquipmentEntity> earTemperatureSensorEquipmentEntities = earTemperatureSensorEquipmentMapper.selectList(enabled());
        PageInfo<EarTemperatureSensorEquipmentEntity> earTemperatureSensorEquipmentEntityPageInfo = new PageInfo<>(earTemperatureSensorEquipmentEntities);
        return new PageResultUtils(earTemperatureSensorEquipmentEntityPageInfo);
    }

    @Override
    public void add(EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity) {
        try {
            earTemperatureSensorEquipmentMapper.insert(earTemperatureSensorEquipmentEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("新增耳温传感器信息失败！");
        }
    }


    @Override
    public R findBaseInfo(Long timestamp) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.ear_temperature_sensor_equipment);
        equipmentByType.forEach(equipment -> {

            Map<String, Object> map = new HashMap();
            Map<String, Object> details;
                TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
                details= details(1, new EarTemperatureSensorEquipmentEntity(), equipment, map, new UserEntity(), rangeTime);
            map = (Map<String, Object>) details.get("map");
            map = CommonEnum.getMap(map, (UserEntity) details.get("user"), equipment);
            EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = (EarTemperatureSensorEquipmentEntity) details.get("earTemperatureSensorEquipmentEntity");
            map.put(ColumnUtil.getFieldName(EarTemperatureSensorEquipmentEntity::getTemperature), earTemperatureSensorEquipmentEntity.getTemperature());
            mapList.add(map);
        });
        return R.ok().put(RConstant.data, mapList);
    }

    public Map<String, Object> details(Integer count, EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity, EquipmentEntity equipment, Map<String, Object> map, UserEntity user, TimeEntity timeEntity) {


        Map<String, Object> resultMap = new HashMap<>();

        earTemperatureSensorEquipmentEntity = earTemperatureSensorEquipmentMapper.findBaseInfo(equipment.getEquipmentId(), timeEntity);
        if (earTemperatureSensorEquipmentEntity != null) {
            Integer temperatureStatus = DetectionConstant.TemperatureInfo(earTemperatureSensorEquipmentEntity.getTemperature());
            map.put(OtherConstant.status, temperatureStatus);

        } else {
            earTemperatureSensorEquipmentEntity = new EarTemperatureSensorEquipmentEntity();
            //todo 这个地方如果数据库本身就没有数据的话 会存在一个死循环 要处理一下
            count++;
            if (count <= 3) {
                return details(count, earTemperatureSensorEquipmentEntity, equipment, map, user, timeEntity);
            }
            map.put(OtherConstant.status, 0);
        }
        EarTemperatureSensorEquipmentEntity baseInfo = earTemperatureSensorEquipmentMapper.findBaseInfo(equipment.getEquipmentId(), null);
        user = userService.findById(baseInfo.getUserId());
        resultMap.put("user", user);
        resultMap.put("map", map);
        resultMap.put("earTemperatureSensorEquipmentEntity", earTemperatureSensorEquipmentEntity);
        return resultMap;

    }

    @Override
    public R findTemperature(Long userId, Long timestamp,String equipmentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
        EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = earTemperatureSensorEquipmentMapper.findTemperature(userId, rangeTime, equipmentId);

        if (earTemperatureSensorEquipmentEntity != null) {
            Integer temperatureStatus = DetectionConstant.TemperatureInfo(earTemperatureSensorEquipmentEntity.getTemperature());
            map.put(ColumnUtil.getFieldName(EarTemperatureSensorEquipmentEntity::getTemperature), earTemperatureSensorEquipmentEntity.getTemperature());
            map.put(OtherConstant.temperatureStatus, temperatureStatus);
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
        List<String> temperature = new ArrayList<>();

        lineChart = CommonEnum.generateFormat(lineChart);
        List<Map < String,String> >list = earTemperatureSensorEquipmentMapper.findLineChart(lineChart);
        list.forEach(map -> {
            time.add( map.get("time"));
            temperature.add( map.get("countT"));
        });
        Map map = new HashMap();
        map.put("time", time);
        map.put("temperature", temperature);
        return R.ok().put(RConstant.data, map);
    }


    @Override
    public Map findDataScreenInfo() {
        AtomicInteger warn = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();

        //查找所有智能床垫设备
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.ear_temperature_sensor_equipment);
        equipmentByType.forEach(equipment -> {
            List<Integer> count = new ArrayList<>();
            Map map = earTemperatureSensorEquipmentMapper.findDataScreenInfo(new Date(), equipment.getEquipmentId());
            if (map != null) {
                Integer temperature = DetectionConstant.TemperatureInfo((Double) map.get("temperature"));
                count.add(temperature);
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

        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.ear_temperature_sensor_equipment);
        equipmentEntityList.forEach(equipment -> {

//            EquipmentEntity equipmentEntity = equipmentService.findEquipmentById(equipment.getEquipmentId());


                EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipment = earTemperatureSensorEquipmentMapper.selectOne(enabled().orderByDesc(BaseEntity::getCreateTime).last("limit 1"));
                if (earTemperatureSensorEquipment != null) {

                    UserEntity user = userService.findById(earTemperatureSensorEquipment.getUserId());
                    AbnormalEntity abnormalEntity = new AbnormalEntity();
                    abnormalEntity.setTime(earTemperatureSensorEquipment.getCreateTime());
                    abnormalEntity.setUserName(user.getUserName());
                    abnormalEntity.setInfo("无");

                    Integer temperatureStatus = DetectionConstant.TemperatureInfo(earTemperatureSensorEquipment.getTemperature());
                    switch (temperatureStatus) {
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
                }
        });

        return abnormalEntities;
    }

    public LambdaQueryWrapper<EarTemperatureSensorEquipmentEntity> enabled() {

        LambdaQueryWrapper<EarTemperatureSensorEquipmentEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EarTemperatureSensorEquipmentEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }

    public static Integer countString(String str, String s) {
        if (StringUtils.isBlank(str)) {
            throw new ErrorException("未获取到时间");
        }
        int count = 0, len = str.length();
        while (str.indexOf(s) != -1) {
            str = str.substring(str.indexOf(s) + 1, str.length());
            count++;
        }
        System.out.println("此字符串有" + count + "个" + s);
        return count;
    }



}
