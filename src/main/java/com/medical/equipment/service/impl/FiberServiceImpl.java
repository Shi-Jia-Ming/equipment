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
import com.medical.equipment.mapper.FiberMapper;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.FiberService;
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
public class FiberServiceImpl implements FiberService {

    @Resource
    private FiberMapper fiberMapper;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private UserService userService;

    @Override
    public List<FiberTemperatureDetectionEntity> findByUserId(Long userId) {
        List<FiberTemperatureDetectionEntity> fiberTemperatureDetectionEntities = fiberMapper.selectList(null);
        return fiberTemperatureDetectionEntities;
    }

    @Override
    public PageResultUtils findAll(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<FiberTemperatureDetectionEntity> fiberTemperatureDetectionEntities = fiberMapper.selectList(enabled());
        PageInfo<FiberTemperatureDetectionEntity> fiberTemperatureDetectionEntityPageInfo = new PageInfo<>(fiberTemperatureDetectionEntities);
        return new PageResultUtils(fiberTemperatureDetectionEntityPageInfo);
    }

    @Override
    public void add(FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity) {
        try {
            fiberMapper.insert(fiberTemperatureDetectionEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("光纤测温设备信息新增失败！");
        }

    }

    @Override
    public R findBaseInfo(Long timestamp) {
        List<Map> mapList = new ArrayList<>();
        //查找设备信息
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.fiber_temperature_detection);
        equipmentByType.forEach(equipment -> {
            Map<String, Object> map = new HashMap();
            Map<String, Object> details;
//            if (equipment.getOnline() == 1) {
                TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
                details= details(1,new FiberTemperatureDetectionEntity(), equipment, map, new UserEntity(), rangeTime);
//            } else {
//                details= details(1,new FiberTemperatureDetectionEntity(), equipment, map, new UserEntity(), null);
//            }

            map = (Map<String, Object>) details.get("map");
            FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = (FiberTemperatureDetectionEntity) details.get("fiberTemperatureDetectionEntity");
            map = CommonEnum.getMap(map, (UserEntity) details.get("user"), equipment);
            map = fiberMapper(map, fiberTemperatureDetectionEntity);
            mapList.add(map);

        });
        return R.ok().put(RConstant.data, mapList);
    }


    public Map<String, Object> details(Integer count, FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity,EquipmentEntity equipment,Map<String,Object> map,UserEntity user,TimeEntity timeEntity) {

        Map<String, Object> resultMap = new HashMap<>();

        fiberTemperatureDetectionEntity = fiberMapper.findBaseInfo(equipment.getEquipmentId(),timeEntity);
        if (fiberTemperatureDetectionEntity != null) {
            List<Integer> result = new ArrayList<>();
            Integer firstChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFirstChannelTemperature());
            Integer secondChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getSecondChannelTemperature());
            Integer thirdChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getThirdChannelTemperature());
            Integer fourthChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFourthChannelTemperature());

            result.add(firstChannelTemperatureStatus);
            result.add(secondChannelTemperatureStatus);
            result.add(thirdChannelTemperatureStatus);
            result.add(fourthChannelTemperatureStatus);

            map.put(OtherConstant.status, Collections.max(result));
        } else {
            count++;
            //todo 这个地方如果数据库本身就没有数据的话 会存在一个死循环 要处理一下
            fiberTemperatureDetectionEntity = new FiberTemperatureDetectionEntity();

            if (count <= 3) {
                return   details(count, fiberTemperatureDetectionEntity, equipment, map, user, timeEntity);
            }
            map.put(OtherConstant.status,0);
        }
        FiberTemperatureDetectionEntity baseInfo = fiberMapper.findBaseInfo(equipment.getEquipmentId(), null);
        user = userService.findById(baseInfo.getUserId());
        resultMap.put("user", user);
        resultMap.put("map", map);
        resultMap.put("fiberTemperatureDetectionEntity", fiberTemperatureDetectionEntity);
        return resultMap;
    }

    @Override
    public R findChannelInfo(Long userId, Long timestamp,String equipmentId) {
        Map<String, Object> resultMap = new HashMap();
        TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);

        FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = fiberMapper.findChannelInfo(userId,rangeTime,equipmentId);
        if (fiberTemperatureDetectionEntity != null) {
            Integer firstChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFirstChannelTemperature());
            Integer secondChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getSecondChannelTemperature());
            Integer thirdChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getThirdChannelTemperature());
            Integer fourthChannelTemperatureStatus = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFourthChannelTemperature());
            resultMap = fiberMapper(resultMap, fiberTemperatureDetectionEntity);
            resultMap.put(OtherConstant.firstChannelTemperatureStatus, firstChannelTemperatureStatus);
            resultMap.put(OtherConstant.secondChannelTemperatureStatus, secondChannelTemperatureStatus);
            resultMap.put(OtherConstant.thirdChannelTemperatureStatus, thirdChannelTemperatureStatus);
            resultMap.put(OtherConstant.fourthChannelTemperatureStatus, fourthChannelTemperatureStatus);
        }
        return R.ok().put(RConstant.data, resultMap);

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
        List<String> countFirstT = new ArrayList<>();
        List<String> countSecondT = new ArrayList<>();
        List<String> countThirdT = new ArrayList<>();
        List<String> countFourT = new ArrayList<>();

        lineChart = CommonEnum.generateFormat(lineChart);

        List<Map<String,String>> list = fiberMapper.findLineChart(lineChart);
        list.forEach(map -> {
            time.add(map.get("time"));
            countFirstT.add( map.get("countFirstT"));
            countSecondT.add( map.get("countSecondT"));
            countThirdT.add( map.get("countThirdT"));
            countFourT.add( map.get("countFourT"));
        });
        Map map = new HashMap();
        map.put("time", time);
        map.put("countFirstT", countFirstT);
        map.put("countSecondT", countSecondT);
        map.put("countThirdT", countThirdT);
        map.put("countFourT", countFourT);
        return R.ok().put(RConstant.data, map);
    }

    @Override
    public Map findDataScreenInfo() {
        AtomicInteger warn = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();

        //查找所有智能床垫设备
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.fiber_temperature_detection);
        equipmentByType.forEach(equipment -> {
            List<Integer> count = new ArrayList<>();
            Map map = fiberMapper.findDataScreenInfo(new Date(), equipment.getEquipmentId());
            if (map != null) {
                Integer firstChannelTemperature = DetectionConstant.TemperatureInfo((Double) map.get("first_channel_temperature"));
                Integer secondChannelTemperature = DetectionConstant.TemperatureInfo((Double) map.get("second_channel_temperature"));
                Integer thirdChannelTemperature = DetectionConstant.TemperatureInfo((Double) map.get("third_channel_temperature"));
                Integer fourthChannelTemperature = DetectionConstant.TemperatureInfo((Double) map.get("fourth_channel_temperature"));
                count.add(firstChannelTemperature);
                count.add(secondChannelTemperature);
                count.add(thirdChannelTemperature);
                count.add(fourthChannelTemperature);
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

        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.fiber_temperature_detection);
        equipmentEntityList.forEach(equipment -> {
            Map<String, Object> mattressMap = new HashMap<>();
            EquipmentEntity equipmentEntity = equipmentService.findEquipmentById(equipment.getEquipmentId());
                //去查询智能床垫当前的数据
                FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = fiberMapper.selectOne(enabled().orderByDesc(BaseEntity::getCreateTime).last("limit 1"));
                if (fiberTemperatureDetectionEntity != null) {
                    Integer temperatureStatus1 = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFirstChannelTemperature());
                    Integer temperatureStatus2 = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getSecondChannelTemperature());
                    Integer temperatureStatus3 = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getThirdChannelTemperature());
                    Integer temperatureStatus4 = DetectionConstant.TemperatureInfo(fiberTemperatureDetectionEntity.getFourthChannelTemperature());

                    UserEntity user = userService.findById(fiberTemperatureDetectionEntity.getUserId());

                    AbnormalEntity abnormalEntity = new AbnormalEntity();
                    abnormalEntity.setTime(fiberTemperatureDetectionEntity.getCreateTime());
                    abnormalEntity.setUserName(user.getUserName());
                    abnormalEntity.setInfo("无");

                    switch (temperatureStatus1) {
                        case 2:
                            abnormalEntity.setAbnormalType("体温过高");
                            abnormalEntity.setStatus(2);

                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("体温异常");
                            abnormalEntity.setStatus(3);
                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                    switch (temperatureStatus2) {
                        case 2:
                            abnormalEntity.setAbnormalType("体温过高");
                            abnormalEntity.setStatus(2);

                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("体温异常");
                            abnormalEntity.setStatus(3);

                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                    switch (temperatureStatus3) {
                        case 2:
                            abnormalEntity.setAbnormalType("体温过高");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("体温异常");
                            abnormalEntity.setStatus(3);
                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                    switch (temperatureStatus4) {
                        case 2:
                            abnormalEntity.setAbnormalType("体温过高");
                            abnormalEntity.setStatus(2);
                            abnormalEntities.add(abnormalEntity);
                            break;
                        case 3:
                            abnormalEntity.setAbnormalType("体温异常");
                            abnormalEntity.setStatus(3);
                            abnormalEntities.add(abnormalEntity);
                            break;
                    }
                }
        });
        return abnormalEntities;
    }


    public LambdaQueryWrapper<FiberTemperatureDetectionEntity> enabled() {
        LambdaQueryWrapper<FiberTemperatureDetectionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FiberTemperatureDetectionEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }

    public Map<String, Object> fiberMapper(Map<String, Object> map, FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity) {
        map.put(ColumnUtil.getFieldName(FiberTemperatureDetectionEntity::getFirstChannelTemperature), fiberTemperatureDetectionEntity.getFirstChannelTemperature());
        map.put(ColumnUtil.getFieldName(FiberTemperatureDetectionEntity::getSecondChannelTemperature), fiberTemperatureDetectionEntity.getSecondChannelTemperature());
        map.put(ColumnUtil.getFieldName(FiberTemperatureDetectionEntity::getThirdChannelTemperature), fiberTemperatureDetectionEntity.getThirdChannelTemperature());
        map.put(ColumnUtil.getFieldName(FiberTemperatureDetectionEntity::getFourthChannelTemperature), fiberTemperatureDetectionEntity.getFourthChannelTemperature());
        return map;
    }


}
