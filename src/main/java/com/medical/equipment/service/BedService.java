package com.medical.equipment.service;

import com.medical.equipment.entity.BedEntity;

public interface BedService {
    BedEntity findBedById(Long bedId);
}
