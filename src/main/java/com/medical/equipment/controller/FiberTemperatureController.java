//package com.medical.equipment.controller;
//
//
//import com.medical.equipment.commonInterface.AddGroup;
//import com.medical.equipment.constant.RConstant;
//import com.medical.equipment.entity.*;
//import com.medical.equipment.service.BedService;
//import com.medical.equipment.service.EquipmentService;
//import com.medical.equipment.service.FiberService;
//import com.medical.equipment.service.UserService;
//import com.medical.equipment.utils.PageQuery;
//import com.medical.equipment.utils.PageResultUtils;
//import com.medical.equipment.utils.R;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@RestController
//@RequestMapping("fiber")
//public class FiberTemperatureController {
//    @Resource
//    private FiberService fiberService;
//
//    @Resource
//    private UserService userService;
//
//    @Resource
//    private EquipmentService equipmentService;
//
//    @Resource
//    private BedService bedService;
//
//    @GetMapping("findByUserId")
//    public R findByUserId(Long userId) {
//        List<FiberTemperatureDetectionEntity> fiber = fiberService.findByUserId(userId);
//        BedEntity bed = bedService.findBedById(fiber.get(0).getBedId());
//        EquipmentEntity equipment = equipmentService.findEquipmentById(fiber.get(0).getEquipmentId());
//        UserEntity user = userService.findById(userId);
//        return R.ok().put("fiber", fiber)
//                .put("equipment", equipment)
//                .put("user", user)
//                .put("bed", bed);
//    }
//
//
//    @GetMapping("findAll")
//    public R findAll(@Validated @RequestBody PageQuery pageQuery) {
//        PageResultUtils pageResultUtils = fiberService.findAll(pageQuery);
//        return R.ok().put(RConstant.data, pageResultUtils);
//    }
//
//    @PostMapping("add")
//    public R add(@Validated({AddGroup.class}) @RequestBody FiberTemperatureDetectionEntity fiberTemperatureDetectionEntity) {
//        fiberService.add(fiberTemperatureDetectionEntity);
//        return R.ok();
//    }
//
//    @GetMapping("findBaseInfo")
//    public R findBaseInfo(@RequestParam Long timestamp) {
//        return fiberService.findBaseInfo(timestamp);
//    }
//
//    @GetMapping("findChannelInfo")
//    public R findChannelInfo(@RequestParam Long userId,@RequestParam Long timestamp,@RequestParam String equipmentId) {
//        return fiberService.findChannelInfo(userId,timestamp,equipmentId);
//    }
//
//    @GetMapping("findUserInfoAndEquipmentInfo")
//    public R findUserInfoAndEquipmentInfo(@RequestParam Long userId, @RequestParam Long equipmentId) {
//        return fiberService.findUserInfoAndEquipmentInfo(userId, equipmentId);
//    }
//
//    @PostMapping("findLineChart")
//    public R findLineChart(@RequestBody LineChartEntity lineChart) {
//
//        return fiberService.findLineChart(lineChart);
//    }
//
//
//}
