package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
