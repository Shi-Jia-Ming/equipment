package com.medical.equipment.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.mapper.*;
import com.medical.equipment.service.impl.EquipmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class MyTask {


    @Resource
    private EquipmentServiceImpl equipmentServiceImpl;


    /**
     * 耳温设备
     */
    @Resource
    private EarTemperatureSensorEquipmentMapper earTemperatureSensorEquipmentMapper;

    /**
     * 光纤设备
     */
    @Resource
    private FiberMapper fiberMapper;

    /**
     * 输液泵设备
     */
    @Resource
    private IntelligentControlProcessInfusionPumpMapper pumpMapper;

    /**
     * 智能床垫
     */
    @Resource
    private IntelligentMattressMapper mattressMapper;

    /**
     * 臂环设备
     */

    @Resource
    private DetectionArmRingEquipmentMapper armRingEquipmentMapper;

    //每五分钟执行一次查看设备的在线状态 //不能去进行手动开启和关闭 因为不知道应该在什么时候进行这个操作
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void work() {
        List<Integer> equipmentTypeList = OtherConstant.getEquipmentType();
        equipmentTypeList.forEach(this::process);
    }

    public void process(Integer equipmentType) {
        long l = System.currentTimeMillis();
        //四分钟时间
        int i = 5 * 60 * 1000;
        long l1 = l - i;
        Date date = new Date(l1);

        //根据设备类型查找所有的此类型设备
        List<EquipmentEntity> equipmentByTypeList = equipmentServiceImpl.findEquipmentByType(equipmentType);
        equipmentByTypeList.forEach(equipmentByType -> {
            //查找此类型单个设备的记录数据
            Object model = getModel(equipmentType, equipmentByType.getEquipmentId());

            //耳温
            switch (equipmentType) {
                case OtherConstant.ear_temperature_sensor_equipment:
                    EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = (EarTemperatureSensorEquipmentEntity) model;
                    //如果此设备存在数据
                    if (earTemperatureSensorEquipmentEntity != null) {
                        Date createTime = earTemperatureSensorEquipmentEntity.getCreateTime();

                        online(equipmentByType, date, createTime);
                    } else {
                        notOnline(equipmentByType);
                    }
                    break;
                case OtherConstant.fiber_temperature_detection:
                    FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = (FiberTemperatureDetectionEntity) model;
                    //如果此设备存在数据
                    if (fiberTemperatureDetectionEntity != null) {
                        Date createTime = fiberTemperatureDetectionEntity.getCreateTime();
                        online(equipmentByType, date, createTime);
                    } else {
                        notOnline(equipmentByType);
                    }
                    break;
                case OtherConstant.intelligent_control_process_infusion_pump:
                    IntelligentControlProcessInfusionPumpEntity pumpEntity = (IntelligentControlProcessInfusionPumpEntity) model;
                    //如果此设备存在数据
                    if (pumpEntity != null) {
                        Date createTime = pumpEntity.getCreateTime();
                        online(equipmentByType, date, createTime);
                    } else {
                        notOnline(equipmentByType);
                    }
                    break;
                case OtherConstant.intelligent_mattress:
                    IntelligentMattressEntity mattressEntity = (IntelligentMattressEntity) model;
                    //如果此设备存在数据
                    if (mattressEntity != null) {
                        Date createTime = mattressEntity.getCreateTime();
                        online(equipmentByType, date, createTime);
                    } else {
                        notOnline(equipmentByType);
                    }
                    break;
                case OtherConstant.detection_arm_ring_equipment:
                    DetectionArmRingEquipmentEntity armRingEquipment = (DetectionArmRingEquipmentEntity) model;
                    //如果此设备存在数据
                    if (armRingEquipment != null) {
                        Date createTime = armRingEquipment.getCreateTime();
                        online(equipmentByType, date, createTime);
                    } else {
                        notOnline(equipmentByType);
                    }
                    break;
            }
        });
    }

    public <T> T getModel(Integer type, Long equipmentId) {
        switch (type) {
            //耳温
            case OtherConstant.ear_temperature_sensor_equipment:
                LambdaQueryWrapper<EarTemperatureSensorEquipmentEntity> earTemperatureSensorEquipmentEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                earTemperatureSensorEquipmentEntityLambdaQueryWrapper
                        .eq(EarTemperatureSensorEquipmentEntity::getEquipmentId, equipmentId)
                        .orderByDesc(EarTemperatureSensorEquipmentEntity::getCreateTime).last(" limit 1");
                EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = earTemperatureSensorEquipmentMapper.selectOne(earTemperatureSensorEquipmentEntityLambdaQueryWrapper);
                return (T) earTemperatureSensorEquipmentEntity;
            //光纤
            case OtherConstant.fiber_temperature_detection:
                LambdaQueryWrapper<FiberTemperatureDetectionEntity> fiberTemperatureDetectionEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                fiberTemperatureDetectionEntityLambdaQueryWrapper.eq(FiberTemperatureDetectionEntity::getEquipmentId, equipmentId)
                        .orderByDesc(FiberTemperatureDetectionEntity::getCreateTime).last(" limit 1");
                FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = fiberMapper.selectOne(fiberTemperatureDetectionEntityLambdaQueryWrapper);
                return (T) fiberTemperatureDetectionEntity;
            //输液泵
            case OtherConstant.intelligent_control_process_infusion_pump:
                LambdaQueryWrapper<IntelligentControlProcessInfusionPumpEntity> pumpEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                pumpEntityLambdaQueryWrapper.eq(IntelligentControlProcessInfusionPumpEntity::getEquipmentId, equipmentId)
                        .orderByDesc(IntelligentControlProcessInfusionPumpEntity::getCreateTime).last(" limit 1");
                IntelligentControlProcessInfusionPumpEntity pumpEntity = pumpMapper.selectOne(pumpEntityLambdaQueryWrapper);
                return (T) pumpEntity;
            case OtherConstant.intelligent_mattress:
                //床垫
                LambdaQueryWrapper<IntelligentMattressEntity> mattressEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                mattressEntityLambdaQueryWrapper.eq(IntelligentMattressEntity::getEquipmentId, equipmentId)
                        .orderByDesc(IntelligentMattressEntity::getCreateTime).last(" limit 1");
                IntelligentMattressEntity intelligentMattressEntity = mattressMapper.selectOne(mattressEntityLambdaQueryWrapper);
                return (T) intelligentMattressEntity;
            //臂环
            case OtherConstant.detection_arm_ring_equipment:
            case OtherConstant.detection_arm_ring_equipment_terminal:
                LambdaQueryWrapper<DetectionArmRingEquipmentEntity> armRingEquipmentEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                armRingEquipmentEntityLambdaQueryWrapper.eq(DetectionArmRingEquipmentEntity::getEquipmentId, equipmentId)
                        .orderByDesc(DetectionArmRingEquipmentEntity::getCreateTime).last(" limit 1");
                DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity = armRingEquipmentMapper.selectOne(armRingEquipmentEntityLambdaQueryWrapper);
                return (T) detectionArmRingEquipmentEntity;
        }

        return null;
    }

    public void online(EquipmentEntity equipmentByType, Date date, Date createTime) {
        if (equipmentByType.getOnline() == 1) {
            // 判断此设备的最后一条数据和当前时间5分钟前的时间进行对比 如果已经有5分钟都没有推送数据 那么此类型该设备离线
            if (createTime.compareTo(date) < 0) {
                log.error("id为：" + equipmentByType.getEquipmentId() + "的设备已经离线");
                equipmentServiceImpl.updateOnline(0, equipmentByType.getEquipmentId());
            }
        } else {
            //如果设备不在线 则更改状态为在线
            if (createTime.compareTo(date) > 0) {
                log.error("id为：" + equipmentByType.getEquipmentId() + "的设备在线");
                equipmentServiceImpl.updateOnline(1, equipmentByType.getEquipmentId());
            }
        }
    }

    public void notOnline(EquipmentEntity equipmentByType) {
        //如果没有此设备的数据
        //判断该设备是否在线
        //如果设备在线 则更改状态为不在线
        if (equipmentByType.getEquipmentType() != OtherConstant.detection_arm_ring_equipment_terminal) {
            //如果是臂环终端的话暂不做处理 其实是应该做处理的 不想写了 todo

            if (equipmentByType.getOnline() == 1) {
                log.error("id为：" + equipmentByType.getEquipmentId() + "的设备已经离线");
                equipmentServiceImpl.updateOnline(0, equipmentByType.getEquipmentId());
            }
        }
    }



}