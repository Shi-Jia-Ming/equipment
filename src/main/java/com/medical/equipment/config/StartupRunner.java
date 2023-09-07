package com.medical.equipment.config;

import com.medical.equipment.constant.DetectionConstant;
import com.medical.equipment.constant.FileConstant;
import com.medical.equipment.entity.EquipmentWarningEntity;
import com.medical.equipment.service.EquipmentWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    EquipmentWarningService equipmentWarningService;
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        initParm();
    }
    /**
     初始化预警信息
     **/
    /**
     * {
     *     1: {1: list,}     设备id: {预警id： 预警信息}
     *     2: {2: list,}
     * }
     */
    private void initParm() {
        List<EquipmentWarningEntity> list = equipmentWarningService.findList();
        HashMap<Integer, HashMap<Integer,List<EquipmentWarningEntity>>> objectObjectHashMap = new HashMap<>();

//        list.stream().forEach(a->{  这么写可能会导致HashMap为空
        list.forEach(a->{
        if(objectObjectHashMap.containsKey(a.getEquipmentTypeId())){
            //存在相同的设备比较预警信息
            HashMap<Integer, List<EquipmentWarningEntity>> map2 = objectObjectHashMap.get(a.getEquipmentTypeId());
            Integer equipmentWarningId = a.getEquipmentWarningId();
            if(map2.containsKey(equipmentWarningId)){
                //存在相同的预警类型，加到list里
                map2.get(equipmentWarningId).add(a);
            }else {
                List<EquipmentWarningEntity> equipmentWarnings = new ArrayList<>();
                equipmentWarnings.add(a);
                map2.put(equipmentWarningId, equipmentWarnings);
            }
        }else {
            //把此条预警信息加入map
            HashMap<Integer, List<EquipmentWarningEntity>> hashMap = new HashMap<>();
            List<EquipmentWarningEntity> equipmentWarnings = new ArrayList<>();
            equipmentWarnings.add(a);
            hashMap.put(a.getEquipmentWarningId(),equipmentWarnings);
            objectObjectHashMap.put(a.getEquipmentTypeId(), hashMap);
        }
        });
        FileConstant.objectObjectHashMap=objectObjectHashMap;
    }
}
