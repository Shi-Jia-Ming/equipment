//package com.medical.equipment.controller;
//
//import com.medical.equipment.commonInterface.AddGroup;
//import com.medical.equipment.constant.RConstant;
//import com.medical.equipment.entity.DetectionArmRingEquipmentEntity;
//import com.medical.equipment.entity.LineChartEntity;
//import com.medical.equipment.service.DetectionArmRingEquipmentService;
//import com.medical.equipment.utils.PageQuery;
//import com.medical.equipment.utils.PageResultUtils;
//import com.medical.equipment.utils.R;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("arm")
//public class DetectionArmRingEquipmentController {
//    @Resource
//    private DetectionArmRingEquipmentService detectionArmRingEquipmentService;
//
//    @GetMapping("findAll")
//    public R findAll(@Validated @RequestBody PageQuery pageQuery) {
//        PageResultUtils pageResultUtils = detectionArmRingEquipmentService.findAll(pageQuery);
//        return R.ok().put(RConstant.data, pageResultUtils);
//    }
//
//
//    @PostMapping("add")
//    public R add(@Validated({AddGroup.class}) @RequestBody DetectionArmRingEquipmentEntity detectionArmRingEquipmentEntity) {
//        detectionArmRingEquipmentService.add(detectionArmRingEquipmentEntity);
//        return R.ok();
//    }
//
//    @GetMapping("findBaseInfo")
//    public R findBaseInfo(@RequestParam Long timestamp) {
//        return detectionArmRingEquipmentService.findBaseInfo(timestamp);
//    }
//
//    @GetMapping("findDetailsInfo")
//    public R findDetailsInfo(@RequestParam Long userId, Long timestamp ,Long equipmentId) {
//        return detectionArmRingEquipmentService.findDetailsInfo(userId,timestamp,equipmentId);
//    }
//
//    @GetMapping("findUserInfoAndEquipmentInfo")
//    public R findUserInfoAndEquipmentInfo(@RequestParam Long userId, Long equipmentId) {
//        return detectionArmRingEquipmentService.findUserInfoAndEquipmentInfo(userId, equipmentId);
//    }
//
//    @PostMapping("findLineChart")
//    public R findLineChart(@RequestBody LineChartEntity lineChart) {
//        return detectionArmRingEquipmentService.findLineChart(lineChart);
//    }
//
//}
