package com.medical.equipment.controller;

import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.IntelligentMattressEntity;
import com.medical.equipment.entity.LineChartEntity;
import com.medical.equipment.service.IntelligentMattressService;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RestController
@RequestMapping("mattress")
public class IntelligentMattressController {


    @Resource
    private IntelligentMattressService intelligentMattressService;

    @PostMapping("add")
    public R add(@Validated({AddGroup.class}) @RequestBody IntelligentMattressEntity intelligentMattressEntity) {
        intelligentMattressService.add(intelligentMattressEntity);
        return R.ok();
    }

    @GetMapping("findAll")
    public R findAll(@Validated @RequestBody PageQuery pageQuery) {
        PageResultUtils pageResultUtils = intelligentMattressService.findAll(pageQuery);
        return R.ok().put(RConstant.data, pageResultUtils);
    }


    @PostMapping("findLineChart")
    public R findLineChart(@Validated @RequestBody LineChartEntity lineChart) {
        return intelligentMattressService.findLineChart(lineChart);
    }

    @GetMapping("findBreatheAndHR")
    public R findBreatheAndHR(@RequestParam Long userId ,@RequestParam Long timestamp,Long equipmentId) {
        return intelligentMattressService.findBreatheAndHR( userId,timestamp,equipmentId);
    }

    // 额外加的方法
    @GetMapping("findBreatheRateAndHeartRate")
    public R findBreatheRateAndHeartRate(@RequestParam Long userId ,@RequestParam Long equipmentId){
        return intelligentMattressService.findBreatheRateAndHeartRate(userId, equipmentId);
    }

    @GetMapping("findEquipmentInfoAndUsrInfo")
    public R findEquipmentInfoAndUsrInfo(@RequestParam Long equipmentId,@RequestParam Long userId) {
        return intelligentMattressService.findEquipmentInfoAndUsrInfo(equipmentId,userId);
    }

    @GetMapping("findBaseInfo")
    public R findBaseInfo(@RequestParam Long timestamp) {
        return intelligentMattressService.findBaseInfo(timestamp);
    }





}
