//package com.medical.equipment.controller;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.medical.equipment.constant.RConstant;
//import com.medical.equipment.entity.IntelligentControlProcessInfusionPumpEntity;
//import com.medical.equipment.entity.LineChartEntity;
//import com.medical.equipment.service.IntelligentControlProcessInfusionPumpService;
//import com.medical.equipment.utils.PageQuery;
//import com.medical.equipment.utils.PageResultUtils;
//import com.medical.equipment.utils.R;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("infusionPump")
//public class IntelligentControlProcessInfusionPumpController {
//
//    @Resource
//    private IntelligentControlProcessInfusionPumpService intelligentControlProcessInfusionPumpService;
//
//
//    @GetMapping("findAll")
//    public R findAll(@Validated @RequestBody PageQuery pageQuery) {
//        PageResultUtils pageResultUtils = intelligentControlProcessInfusionPumpService.findAll(pageQuery);
//        return R.ok().put(RConstant.data, pageResultUtils);
//    }
//
//
//    @PostMapping("add")
//    public R add(@Validated @RequestBody IntelligentControlProcessInfusionPumpEntity intelligentControlProcessInfusionPumpEntity) {
//        intelligentControlProcessInfusionPumpService.add(intelligentControlProcessInfusionPumpEntity);
//        return R.ok();
//    }
//
//    @GetMapping("findBaseInfo")
//    public R findBaseInfo(@RequestParam Long timestamp) {
//        return intelligentControlProcessInfusionPumpService.findBaseInfo(timestamp);
//    }
//
//    @GetMapping("findDetails")
//    public R findDetails(@RequestParam Long userId,@RequestParam Long timestamp,@RequestParam Long equipmentId) {
//        return intelligentControlProcessInfusionPumpService.findDetails(userId,timestamp,equipmentId);
//    }
//
//    @GetMapping("findUserInfoAndEquipmentInfo")
//    public R findUserInfoAndEquipmentInfo(@RequestParam Long userId, @RequestParam Long equipmentId) {
//        return intelligentControlProcessInfusionPumpService.findUserInfoAndEquipmentInfo(userId, equipmentId);
//    }
//
//    @PostMapping("findLineChart")
//    public R findLineChart(@RequestBody LineChartEntity lineChart) {
//      return   intelligentControlProcessInfusionPumpService.findLineChart(lineChart);
//    }
//}
