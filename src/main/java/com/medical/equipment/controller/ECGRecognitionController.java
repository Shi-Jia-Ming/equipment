package com.medical.equipment.controller;

import com.medical.equipment.service.IntelligentMattressService;
import com.medical.equipment.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ECG")
public class ECGRecognitionController {
    @Resource
    IntelligentMattressService intelligentMattressService;

    @GetMapping("recognition")
    public R ECGRecognitionByPython(@RequestParam Long userId , @RequestParam Long equipmentId){
        return intelligentMattressService.pythonECGRecognition(userId, equipmentId);
    }
}
