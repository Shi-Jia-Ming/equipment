package com.medical.equipment.service;

import com.medical.equipment.entity.CommonDtoEntity;
import com.medical.equipment.utils.R;

import javax.servlet.http.HttpServletRequest;

public interface CommonService {
    void insert(CommonDtoEntity commonDtoEntity, HttpServletRequest request);

    R findAbnormalData();

    String findContext();

}
