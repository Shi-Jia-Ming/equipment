package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medical.equipment.entity.UserEntity;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.UserMapper;
import com.medical.equipment.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserEntity> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userMapper.selectById(userId);
    }


}
