package com.medical.equipment.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.constant.ColumnUtil;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.IntelligentControlProcessInfusionPumpMapper;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.service.IntelligentControlProcessInfusionPumpService;
import com.medical.equipment.service.UserService;
import com.medical.equipment.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class IntelligentControlProcessInfusionPumpServiceImpl implements IntelligentControlProcessInfusionPumpService {


    @Resource
    private IntelligentControlProcessInfusionPumpMapper intelligentControlProcessInfusionPumpMapper;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private UserService userService;

    @Override
    public PageResultUtils findAll(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<IntelligentControlProcessInfusionPumpEntity> intelligentControlProcessInfusionPumpEntities = intelligentControlProcessInfusionPumpMapper.selectList(enabled());
        PageInfo<IntelligentControlProcessInfusionPumpEntity> intelligentControlProcessInfusionPumpEntityPageInfo = new PageInfo<>(intelligentControlProcessInfusionPumpEntities);

        return new PageResultUtils(intelligentControlProcessInfusionPumpEntityPageInfo);
    }

    @Override
    public void add(IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity) {
        try {
            intelligentControlProcessInfusionPumpMapper.insert(intelligentControlProcessInfusionPumpEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("新增智能控程输液泵信息失败!");
        }
    }

    @Override
    public R findBaseInfo(Long timestamp) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        //得到所有的设备
        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.intelligent_control_process_infusion_pump);
        equipmentEntityList.forEach(equipment -> {
            Map<String, Object> details;
            Map<String, Object> map = new HashMap<>();

                TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);
                details = details(1, null, equipment, map, new UserEntity(), rangeTime);
            IntelligentControlProcessInfusionPumpEntity infusionPumpEntity = (IntelligentControlProcessInfusionPumpEntity) details.get("infusionPumpEntity");
            map = (Map<String, Object>) details.get("map");
            map = CommonEnum.getMap(map, (UserEntity) details.get("user"), equipment);
            map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getTotalLiquid), infusionPumpEntity!=null?infusionPumpEntity.getTotalLiquid():0);
            map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getAlreadyLiquid), infusionPumpEntity!=null?infusionPumpEntity.getAlreadyLiquid():0);
            map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getResidueTime), infusionPumpEntity!=null?infusionPumpEntity.getResidueTime():0);
            map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getSpeed), infusionPumpEntity!=null?infusionPumpEntity.getSpeed():0);
            mapList.add(map);

        });
        return R.ok().put(RConstant.data, mapList);
    }

    public Map<String, Object> details(Integer count, IntelligentControlProcessInfusionPumpEntity infusionPumpEntity, EquipmentEntity equipment, Map<String, Object> map, UserEntity user, TimeEntity timeEntity) {

        Map<String, Object> resultMap = new HashMap<>();

        infusionPumpEntity = intelligentControlProcessInfusionPumpMapper.findBaseInfo(equipment.getEquipmentId(), timeEntity);
        if (infusionPumpEntity != null) {
           List<String> errorInfo = getErrorInfo(infusionPumpEntity.getWarnInfo());
            if(errorInfo.size()>0){
                map.put(OtherConstant.status, 3); // 2柱塞
                map.put(OtherConstant.warnInfo, errorInfo);
            }else {
                map.put(OtherConstant.status, 1);
            }
        } else {
            count++;
            if (count <= 3) {
                return  details(count, infusionPumpEntity, equipment, map, user, timeEntity);
            }
            map.put(OtherConstant.status, 0);
        }
        IntelligentControlProcessInfusionPumpEntity baseInfo = intelligentControlProcessInfusionPumpMapper.findBaseInfo(equipment.getEquipmentId(), null);
        if(baseInfo!=null)
        user = userService.findById(baseInfo.getUserId());
        resultMap.put("user", user);
        resultMap.put("map", map);
        resultMap.put("infusionPumpEntity", infusionPumpEntity);
        return resultMap;
    }

    public  List<String> getErrorInfo(String str) {

        int num = Integer.parseInt(str);
        List<String> exception=new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        //气泡
        map.put(0,"QP");
        //结束
        map.put(1,"JS");
        //网络
        map.put(2,"WL");
        //背板
        map.put(3,"BB");
        //截取阻塞信息
        map.put(4,"ZS");
        //电池电量极低
        map.put(5,"DLJD");
        //电池电量低
        map.put(6,"DLD");
        //预警
        map.put(7,"YJ");
        //停止
        map.put(8,"TZ");
        //kvo报警
        map.put(9,"KVO");
        //超出范围
        map.put(10,"CC");

        for (int i = 0; i < 11; i++)
        {
            if ((num & 1) == 1)
            {
                exception.add(map.get(i));
            }
            num =(num >> 1);
        }

        return exception;
    }


    @Override
    public Map findDataScreenInfo() {
        AtomicInteger warn = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();
        //查找所有智能床垫设备
        List<EquipmentEntity> equipmentByType = equipmentService.findEquipmentByType(OtherConstant.intelligent_control_process_infusion_pump);
        equipmentByType.forEach(equipment -> {
            Map map = intelligentControlProcessInfusionPumpMapper.findDataScreenInfo(new Date(), equipment.getEquipmentId());
            if (map != null) {
                String warnInfo = (String) map.get("warn_info");
                List<String> errorInfo = getErrorInfo(warnInfo);
                if (errorInfo.size()>0) {
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
    public R findDetails(Long userId, Long timestamp, Long equipmentId) {
        Map<String, Object> map = new HashMap<>();

        TimeEntity rangeTime = CommonEnum.getRangeTime(timestamp);

        IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity
                = intelligentControlProcessInfusionPumpMapper.findDetails(userId,rangeTime,equipmentId);
        if (intelligentControlProcessInfusionPumpEntity == null) {
            intelligentControlProcessInfusionPumpEntity = new IntelligentControlProcessInfusionPumpEntity();
        }

        String warnInfo = intelligentControlProcessInfusionPumpEntity.getWarnInfo();
        if ( !StringUtils.isBlank(warnInfo)) {
           List<String> errorInfo = getErrorInfo(warnInfo);
            if(errorInfo.size()>0){
                map.put(OtherConstant.status, 3); // 2柱塞
                map.put(OtherConstant.warnInfo, errorInfo);
            }else {
                map.put(OtherConstant.status, 1);
            }
        } else {
            map.put(OtherConstant.status, 0);
        }

        UserEntity user = userService.findById(userId);


        map.put(OtherConstant.userInfo, user);
        map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getTotalLiquid), intelligentControlProcessInfusionPumpEntity.getTotalLiquid());
        map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getAlreadyLiquid), intelligentControlProcessInfusionPumpEntity.getAlreadyLiquid());
        map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getSpeed), intelligentControlProcessInfusionPumpEntity.getSpeed());
        map.put(ColumnUtil.getFieldName(IntelligentControlProcessInfusionPumpEntity::getResidueTime), intelligentControlProcessInfusionPumpEntity.getResidueTime());

        return R.ok().put(RConstant.data, map);
    }

    @Override
    public R findUserInfoAndEquipmentInfo(Long userId, Long equipmentId) {
        UserEntity user = userService.findById(userId);
        EquipmentEntity equipment = equipmentService.findEquipmentById(equipmentId);
        Map<String, Object> map = new HashMap<>();
        map.put(OtherConstant.userInfo, user);
        map.put(OtherConstant.equipmentInfo, equipment);
        return R.ok().put(RConstant.data, map);
    }

    @Override
    public R findLineChart(LineChartEntity lineChart) {


        List<String> time = new ArrayList<>();
        List<String> alreadyLiquid = new ArrayList<>();


        lineChart = CommonEnum.generateFormat(lineChart);

        List<Map<String, String>> list = intelligentControlProcessInfusionPumpMapper.findLineChart(lineChart);
        list.forEach(map -> {
            time.add(map.get("time"));
            alreadyLiquid.add( map.get("alreadyLiquid"));

        });
        Map<String, Object> map = new HashMap();
        map.put("time", time);
        map.put("alreadyLiquid", alreadyLiquid);

        return R.ok().put(RConstant.data, map);


    }

    @Override
    public List<AbnormalEntity> findAbnormalData() {


        List<AbnormalEntity> abnormalEntities = new ArrayList<>();
        List<EquipmentEntity> equipmentEntityList = equipmentService.findEquipmentByType(OtherConstant.intelligent_control_process_infusion_pump);
        equipmentEntityList.forEach(equipment -> {
            Map<String, Object> mattressMap = new HashMap<>();
            EquipmentEntity equipmentEntity = equipmentService.findEquipmentById(equipment.getEquipmentId());

                //去查询智能床垫当前的数据
                IntelligentControlProcessInfusionPumpEntity processInfusionPumpEntity = intelligentControlProcessInfusionPumpMapper.selectOne(enabled().orderByDesc(BaseEntity::getCreateTime).last("limit 1"));
                if (processInfusionPumpEntity != null) {
                    UserEntity user = userService.findById(processInfusionPumpEntity.getUserId());

                    AbnormalEntity abnormalEntity = new AbnormalEntity();
                    abnormalEntity.setTime(processInfusionPumpEntity.getCreateTime());
                    abnormalEntity.setUserName(user.getUserName());
                    abnormalEntity.setInfo("无");

                    String warnInfo = processInfusionPumpEntity.getWarnInfo();
                    List<String> errorInfo = getErrorInfo(warnInfo);
                    //todo 需要修改

                    for(String s : errorInfo) {
                        if (s.equals("QP")) {
                            abnormalEntity.setStatus(3);
                            abnormalEntity.setAbnormalType("气泡预警");
                            abnormalEntities.add(abnormalEntity);
                        }
                        if (s.equals("ZS")) {
                            abnormalEntity.setStatus(3);
                            abnormalEntity.setAbnormalType("阻塞");
                            abnormalEntities.add(abnormalEntity);

                        }
                        if (s.equals("DLD")) {
                            abnormalEntity.setStatus(3);
                            abnormalEntity.setAbnormalType("电量低");
                            abnormalEntities.add(abnormalEntity);

                        }
                        if (s.equals("KVO")) {
                            abnormalEntity.setStatus(3);
                            abnormalEntity.setAbnormalType("kvo异常");
                            abnormalEntities.add(abnormalEntity);

                        }
                    }
                }
        });

        return abnormalEntities;
    }


    public LambdaQueryWrapper<IntelligentControlProcessInfusionPumpEntity> enabled() {

        LambdaQueryWrapper<IntelligentControlProcessInfusionPumpEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(IntelligentControlProcessInfusionPumpEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }

}
