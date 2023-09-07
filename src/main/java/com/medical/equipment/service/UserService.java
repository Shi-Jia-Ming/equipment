package com.medical.equipment.service;

import com.medical.equipment.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();

    UserEntity findById(Long userId);

}
