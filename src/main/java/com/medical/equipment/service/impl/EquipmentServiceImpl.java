package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.entity.EquipmentEntity;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.EquipmentMapper;
import com.medical.equipment.service.EquipmentService;
import com.medical.equipment.utils.PageResultUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;


    @Override
    public EquipmentEntity findEquipmentById(Long equipmentId) {
        return equipmentMapper.selectOne(enabled().eq(EquipmentEntity::getEquipmentId, equipmentId));
    }


    @Override
    public List<EquipmentEntity> findEquipmentByType(Integer type) {
        List<EquipmentEntity> equipmentEntities;
        if (OtherConstant.getEquipmentType().contains(type)) {

            LambdaQueryWrapper<EquipmentEntity> entityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            entityLambdaQueryWrapper.eq(EquipmentEntity::getEquipmentType, type).eq(EquipmentEntity::getEnabled, 1);
           equipmentEntities = equipmentMapper.selectList(entityLambdaQueryWrapper);
        }else {
            throw new ErrorException("不存在此设备类型！请核实！");
        }
        return equipmentEntities;
    }

    @Override
//    @Transactional 我只有这一个事务 就不需要回滚了
    public void addEquipment(EquipmentEntity equipment) {
        equipment.setCreateTime(new Date());
        try {
            equipmentMapper.insert(equipment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("设备信息新增失败！");
        }
    }

    @Override
    public void delEquipment(Long id) {
        LambdaUpdateWrapper<EquipmentEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(EquipmentEntity::getEnabled, 0).eq(EquipmentEntity::getEquipmentId, id);
        try {
            equipmentMapper.update(null, lambdaUpdateWrapper);
        } catch (Exception e) {
            throw new ErrorException("设备信息删除失败！");
        }
    }

    @Override
    public PageResultUtils findAll() {

        PageHelper.startPage(1, 10);

        List<EquipmentEntity> equipmentEntities = equipmentMapper.selectList(enabled());

        PageInfo<EquipmentEntity> equipmentEntityPageInfo = new PageInfo<>(equipmentEntities);
        PageResultUtils pageResultUtils = new PageResultUtils(equipmentEntityPageInfo);

        return pageResultUtils;
    }

    @Override
    public void update(EquipmentEntity equipment) {
        try {
            equipment.setUpdateTime(new Date());
            equipmentMapper.updateById(equipment);
        }catch (Exception e){
            e.printStackTrace();
            throw new ErrorException("设备信息更新失败！");
        }
    }

    public static LambdaQueryWrapper<EquipmentEntity> enabled() {
        LambdaQueryWrapper<EquipmentEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EquipmentEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }

    public void updateOnline(Integer online, Long equipmentId) {
        LambdaUpdateWrapper<EquipmentEntity> equipmentEntityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        equipmentEntityLambdaUpdateWrapper
                .set(EquipmentEntity::getOnline, online)
                .eq(EquipmentEntity::getEnabled, 1)
                .eq(EquipmentEntity::getEquipmentId, equipmentId);
        try {
            equipmentMapper.update(null, equipmentEntityLambdaUpdateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("更改设备状态失败！");
        }
    }

}
