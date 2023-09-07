package com.medical.equipment.service.impl;

import com.medical.equipment.entity.BedEntity;
import com.medical.equipment.mapper.BedMapper;
import com.medical.equipment.service.BedService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BedServiceImpl implements BedService {

    @Resource
    private BedMapper bedMapper;
    @Override
    public BedEntity findBedById(Long bedId) {
        return bedMapper.selectById(bedId);
    }
}
