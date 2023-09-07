package com.medical.equipment.service.impl;

import java.util.Date;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.*;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.EquipmentMapper;
import com.medical.equipment.service.*;
import com.medical.equipment.utils.CommonEnum;
import com.medical.equipment.utils.R;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {


    @Resource    //智能床垫
    private IntelligentMattressService intelligentMattressService;
    @Resource    //耳温传感器
    private EarTemperatureSensorEquipmentService earTemperatureSensorEquipmentService;

    @Resource    //光纤测温
    private FiberService fiberService;

    @Resource   //智能输液泵设备
    private IntelligentControlProcessInfusionPumpService controlProcessInfusionPumpService;

    @Resource  //检测臂环设备
    private DetectionArmRingEquipmentService detectionArmRingEquipmentService;

    @Resource   //设备
    private EquipmentMapper equipmentMapper;

    @Resource
    private EquipmentService equipmentService;


    // TODO ThingsBoard向后端发送数据使用的函数，根据这个修改规则链
    @Override
    public void insert(CommonDtoEntity commonDtoEntity, HttpServletRequest request) {

        String deviceName = request.getHeader("devicename");
        String ts = request.getHeader("ts");
        List<EquipmentEntity> equipmentEntities;


        LambdaQueryWrapper<EquipmentEntity> equipmentEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        equipmentEntityLambdaQueryWrapper
                .eq(EquipmentEntity::getEnabled, 1)
                .eq(EquipmentEntity::getEquipmentCode, deviceName);

        equipmentEntities = equipmentMapper.selectList(equipmentEntityLambdaQueryWrapper);


        if (equipmentEntities.isEmpty()) {

            // 去自动添加设备
            EquipmentEntity equipment = new EquipmentEntity();
            equipment.setOnline(commonDtoEntity.getONLINE());
            // 获取版本信息
            if (commonDtoEntity.getVS() == null) {
                equipment.setVersion("v1.001");
            } else {
                equipment.setVersion(commonDtoEntity.getVS());
            }
            equipment.setEquipmentCode(deviceName);
            String equipmentName = "";
            if (deviceName.contains("FOS")) {
                equipment.setEquipmentType(OtherConstant.intelligent_mattress);
                equipmentName = OtherConstant.FOS;
            }
            if (deviceName.contains("ARM")) {
                equipment.setEquipmentType(OtherConstant.detection_arm_ring_equipment_terminal);
                equipmentName = OtherConstant.ARM;
            }
            if (deviceName.contains("MCT")) {
                equipment.setEquipmentType(OtherConstant.fiber_temperature_detection);
                equipmentName = OtherConstant.MCT;
            }
            if (deviceName.contains("PUM")) {
                equipment.setEquipmentType(OtherConstant.intelligent_control_process_infusion_pump);
                equipmentName = OtherConstant.PUM;
            }
            if (deviceName.contains("EAR")) {
                equipment.setEquipmentType(OtherConstant.ear_temperature_sensor_equipment);
                equipmentName = OtherConstant.EAR;
            }
            equipment.setEquipmentName(equipmentName);

            equipmentService.addEquipment(equipment);
            equipmentEntities = equipmentMapper.selectList(equipmentEntityLambdaQueryWrapper);
        } if (equipmentEntities.size() > 1) {
            throw new ErrorException("此设备名存在多个设备");
        }


        if (deviceName.contains("ARM")) {

            EquipmentEntity equipmentEntity1 = equipmentMapper.selectOne(EquipmentServiceImpl.enabled()
                    .eq(EquipmentEntity::getEquipmentCode, commonDtoEntity.getID()));
            //查询数据库是否包含此臂环设备
            if (equipmentEntity1 == null) {
                EquipmentEntity equipmentEntity = new EquipmentEntity();
                if (StringUtils.isBlank(commonDtoEntity.getVS())) {
                    equipmentEntity.setVersion("v1.001");
                } else {
                    equipmentEntity.setVersion(commonDtoEntity.getVS());
                }
                equipmentEntity.setEquipmentCode(commonDtoEntity.getID());
                equipmentEntity.setEquipmentType(OtherConstant.detection_arm_ring_equipment);
                equipmentEntity.setEquipmentName(OtherConstant.ARM);
                equipmentEntity.setCreateTime(new Date());
                equipmentEntity.setOnline(1);
                equipmentService.addEquipment(equipmentEntity);
            }

            LambdaQueryWrapper<EquipmentEntity> entityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            entityLambdaQueryWrapper.eq(EquipmentEntity::getEnabled, 1).eq(EquipmentEntity::getEquipmentCode, commonDtoEntity.getID());
            equipmentEntities = equipmentMapper.selectList(entityLambdaQueryWrapper);
        }

        EquipmentEntity equipment = equipmentEntities.get(0);
        //去查找一波此设备号的设备信息
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        //智能床垫设备

        if (deviceName.contains("FOS")) {
            updateOnlineStatus(commonDtoEntity.getONLINE(), equipment);
            IntelligentMattressEntity intelligentMattressEntity = CommonEnum.generateIntelligentMattressEntity(commonDtoEntity, equipment, user, ts);
            intelligentMattressService.add(intelligentMattressEntity);

        }
        //耳温
        if (deviceName.contains("EAR")) {
            updateOnlineStatus(commonDtoEntity.getONLINE(), equipment);
            EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity = CommonEnum.generateEarTemperatureSensorEquipmentEntity(commonDtoEntity, equipment, user, ts);
            if (commonDtoEntity.getONLINE() != null) {
                if (commonDtoEntity.getONLINE() == 1) {
                    earTemperatureSensorEquipmentService.add(earTemperatureSensorEquipmentEntity);
                }
            }
        }
        //光纤
        if (deviceName.contains("MCT")) {
            updateOnlineStatus(commonDtoEntity.getONLINE(), equipment);
            FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity = CommonEnum.generateFiberTemperatureDetectionEntity(commonDtoEntity, equipment, user, ts);
                    fiberService.add(fiberTemperatureDetectionEntity);
        }

        //输液泵
        if (deviceName.contains("PUM")) {
            updateOnlineStatus(commonDtoEntity.getONLINE(), equipment);
            IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity = CommonEnum.generateIntelligentControlProcessInfusionPumpEntity(commonDtoEntity, equipment, user, ts);
            if (commonDtoEntity.getONLINE() != null) {
                if (commonDtoEntity.getONLINE() == 1) {
                    controlProcessInfusionPumpService.add(intelligentControlProcessInfusionPumpEntity);
                }
            }
        }
        //臂环设备
        if (deviceName.contains("ARM")) {
            updateOnlineStatus(commonDtoEntity.getONLINE(), equipment);
            DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity = CommonEnum.generateDetectionArmRingEquipmentEntity(commonDtoEntity, equipment, user, ts);
            if (commonDtoEntity.getONLINE() != null) {
                if (commonDtoEntity.getONLINE() == 1) {
                    detectionArmRingEquipmentService.add(detectionArmRingEquipmentEntity);
                }
            }
        }

        System.out.println(deviceName);
        System.out.println(ts);
    }

    @Override
    public R findAbnormalData() {
        List<AbnormalEntity> finallyList = new ArrayList();
        //获取智能床垫设备的异常数据
        List<AbnormalEntity> mattressList = intelligentMattressService.findAbnormalData();

        //臂环设备
        List<AbnormalEntity> armList = detectionArmRingEquipmentService.findAbnormalData();

        //光纤测温
        List<AbnormalEntity> fiberList = fiberService.findAbnormalData();

        //输液泵设备
        List<AbnormalEntity> pumpList = controlProcessInfusionPumpService.findAbnormalData();

        //耳温设备
        List<AbnormalEntity> earList = earTemperatureSensorEquipmentService.findAbnormalData();

        finallyList.addAll(mattressList);
        finallyList.addAll(armList);
        finallyList.addAll(fiberList);
        finallyList.addAll(pumpList);
        finallyList.addAll(earList);
        return R.ok().put(RConstant.data, finallyList);

    }

    @Override
    public String findContext() {
//        File file = new File("src/main/resources/template/context.txt");
//        BufferedReader old = null;
//        try {
//            old = new BufferedReader(new FileReader(file));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String str = null;
//        StringBuffer stringBuffer = new StringBuffer();
//        while (true) {
//            try {
//                if (!((str = old.readLine()) != null)) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            stringBuffer.append(str);
//        }
//        return stringBuffer.toString();

        return "<p>中国人民解放军总医院海南医院与哈尔滨工业大学（威海）理学院、深圳市富斯光电科技有限公司共建海南国际医学港5G医疗实验室。</p >\n" +
                "<br />\n" +
                "<p>一是着眼未来海南国际医学港建设的前期工作，提前谋划。加强医学港创新研发平台顶层设计，共同投入智能医疗设备的研发；</p >\n" +
                "<br />\n" +
                "<p>二是双方共同成立5G+医疗应用联合创新实验室，将云计算、大数据、物联网、5G等信息技术应用于海南国际医学港的建设，打造基于5G远程医疗监测平台，加大在移动医疗和健康大数据应用的合作，通过云间301医联体互联网医院服务广大患者，为海南省各医疗机构提供远程医学交流服务；</p >\n" +
                "<br />\n" +
                "<p>三是加快智慧化医院建设，双方共同推动加快解放军总医院海南医院智慧化医院建设进程，助力医院实现智慧病房建设、智能化医疗设备应用、实时监测患者生理指标数据，提高工作效率和管理水平；</p >\n" +
                "<br />\n" +
                "<p>四是开展协同创新及推进成果转化，双方发挥各自资源优势，面向国际科技发展前沿和海南省医疗产业布局，开展科研合作和创新研发平台建设。</p >\n" +
                "<br />\n" +
                "<p>解放军总医院海南医院是解放军总医院建院60余年来首次异地办院。医院于2010年9月经中央军委批准组建，2011年12月试运行，2012年6月正式开诊，2018年1月转隶移交联勤保障部队并于11月正式更名为解放军总医院海南医院，是总医院唯一的全学科综合医院。编设床位570张，展开720张；编设临床医技及职能科室63个，展开56个；实有工作人员1357名。设备总值超过7亿元，先进程度居总医院各中心和海南省前列。医院位于三亚市海棠区C10片区，现占地217亩，以内河为界，其中河西侧142亩，建筑面积24.1万平方米。</p >\n" +
                "<br />";

    }


    /**
     * 更改设备的在线状态
     */
    public void updateOnlineStatus(Integer online, EquipmentEntity equipment) {
        LambdaUpdateWrapper<EquipmentEntity> equipmentEntityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        equipmentEntityLambdaUpdateWrapper.eq(EquipmentEntity::getEquipmentId, equipment.getEquipmentId());

//光纤和床垫没有 设备状态 所以默认是null  在线
        if (online == null) {
            online = 1;
        }
        if (online == 1) {
            if (equipment.getOnline() == 0) {
                equipmentEntityLambdaUpdateWrapper.set(EquipmentEntity::getOnline, 1);
                try {
                    equipmentMapper.update(null, equipmentEntityLambdaUpdateWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ErrorException(e.getMessage());
                }
            }
        } else {
            if (equipment.getOnline() == 1) {
                equipmentEntityLambdaUpdateWrapper.set(EquipmentEntity::getOnline, 0);
                try {
                    equipmentMapper.update(null, equipmentEntityLambdaUpdateWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ErrorException(e.getMessage());
                }
            }
        }
    }



}
