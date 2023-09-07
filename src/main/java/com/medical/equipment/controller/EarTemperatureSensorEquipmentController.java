//package com.medical.equipment.controller;
//
//import com.medical.equipment.constant.RConstant;
//import com.medical.equipment.entity.EarTemperatureSensorEquipmentEntity;
//import com.medical.equipment.entity.LineChartEntity;
//import com.medical.equipment.service.EarTemperatureSensorEquipmentService;
//import com.medical.equipment.utils.PageQuery;
//import com.medical.equipment.utils.PageResultUtils;
//import com.medical.equipment.utils.R;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("ear")
//public class EarTemperatureSensorEquipmentController {
//    @Resource
//    private EarTemperatureSensorEquipmentService earTemperatureSensorEquipmentService;
//
//    @GetMapping("findAll")
//    public R findAll(@Validated @RequestBody PageQuery pageQuery) {
//        PageResultUtils resultUtils = earTemperatureSensorEquipmentService.findAll(pageQuery);
//        return R.ok().put(RConstant.data, resultUtils);
//    }
//
//    @PostMapping("add")
//    public R add(@Validated @RequestBody EarTemperatureSensorEquipmentEntity earTemperatureSensorEquipmentEntity) {
//        earTemperatureSensorEquipmentService.add(earTemperatureSensorEquipmentEntity);
//        return R.ok();
//    }
//
//    @GetMapping("findBaseInfo")
//    public R findBaseInfo(@RequestParam Long timestamp) {
//        return earTemperatureSensorEquipmentService.findBaseInfo(timestamp);
//    }
//
//    @GetMapping("findTemperature")
//    public R findTemperature(@RequestParam Long userId,@RequestParam Long timestamp,@RequestParam String equipmentId) {
//        return earTemperatureSensorEquipmentService.findTemperature(userId,timestamp,equipmentId);
//    }
//
//    @GetMapping("findUserInfoAndEquipmentInfo")
//    public R findUserInfoAndEquipmentInfo(@RequestParam Long userId, @RequestParam Long equipmentId) {
//        return earTemperatureSensorEquipmentService.findUserInfoAndEquipmentInfo(userId, equipmentId);
//    }
//
//    @PostMapping("findLineChart")
//    public R findLineChart(@RequestBody LineChartEntity lineChart) {
//        return earTemperatureSensorEquipmentService.findLineChart(lineChart);
//    }
//
//}
