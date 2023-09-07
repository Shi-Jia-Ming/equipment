package com.medical.equipment.controller;

import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.CommonDtoEntity;
import com.medical.equipment.service.*;
import com.medical.equipment.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("equipment")
public class CommonController {

    @Resource
    private CommonService commonService;

    @Resource
    private IntelligentMattressService intelligentMattressService;

    @Resource
    private EarTemperatureSensorEquipmentService earTemperatureSensorEquipmentService;

    @Resource
    private DetectionArmRingEquipmentService detectionArmRingEquipmentService;

    @Resource
    private FiberService fiberService;
    @Resource
    private IntelligentControlProcessInfusionPumpService pumpService;

    // thingsboard将数据转发到这个接口
    @PostMapping("insert")
    public R insert(@RequestBody CommonDtoEntity commonDtoEntity, HttpServletRequest request) {
        commonService.insert(commonDtoEntity, request);
        return R.ok();
    }

    @GetMapping("dataScreen")
    public R getDataScreenInfo() {
        Map mattressMap;
        Map earMap;
        Map armMap;
        Map fiberMap;
        Map pumpMap;

        //查询关于智能床垫的大屏信息
        mattressMap = intelligentMattressService.findDataScreenInfo();
        //查询耳温传感器大屏信息
        earMap = earTemperatureSensorEquipmentService.findDataScreenInfo();
        //臂环设备
        armMap = detectionArmRingEquipmentService.findDataScreenInfo();
        //光纤设备
        fiberMap = fiberService.findDataScreenInfo();
        //输液泵
        pumpMap = pumpService.findDataScreenInfo();
        // TODO     方法调用 'put' 可能产生 'NullPointerException'
        return Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(R.ok()
                                                .put("mattress", mattressMap))
                                                .put("ear", earMap))
                                                .put("arm", armMap))
                                                .put("fiber", fiberMap))
                                                .put("pump", pumpMap);
    }

    @GetMapping("findAbnormalData")
    public R findAbnormalData() {
        return commonService.findAbnormalData();
    }

    @GetMapping("findContext")
    public R findContext() {
        String context = commonService.findContext();
        return R.ok().put(RConstant.data, context);

    }
}
