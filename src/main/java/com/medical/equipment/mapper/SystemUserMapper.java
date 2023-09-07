package com.medical.equipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medical.equipment.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUserEntity> {

}