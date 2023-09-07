package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.entity.SystemMenuEntity;
import com.medical.equipment.mapper.SystemMenuMapper;
import com.medical.equipment.service.SystemMenuService;
import com.medical.equipment.utils.PageResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SystemMenuServiceImpl implements SystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public int insert(SystemMenuEntity systemMenuEntity) {
        systemMenuEntity.setCreateTime(new Date());
        return systemMenuMapper.insert(systemMenuEntity);
    }

    @Override
    public int update(SystemMenuEntity systemMenuEntity) {
        systemMenuEntity.setUpdateTime(new Date());
        return systemMenuMapper.updateById(systemMenuEntity);
    }

    @Override
    public int delete(String operator, int id) {
        LambdaUpdateWrapper<SystemMenuEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SystemMenuEntity::getStatus, 1)
                .set(SystemMenuEntity::getUpdateBy, operator)
                .set(SystemMenuEntity::getUpdateTime, new Date())
                .eq(SystemMenuEntity::getMenuId, id);

        return systemMenuMapper.update(null, updateWrapper);
    }

    @Override
    public SystemMenuEntity getById(int id) {
        SystemMenuEntity systemMenuEntity = systemMenuMapper.selectById(id);
        return systemMenuEntity;
    }

    @Override
    public PageResultUtils list(int pageNum, int pageSize, Integer parentId) {
        PageHelper.startPage(pageNum, pageSize);
        List<SystemMenuEntity> systemMenuEntities = new ArrayList<>();
        if (parentId != null) {
            LambdaQueryWrapper<SystemMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SystemMenuEntity::getParentId, parentId);
            systemMenuEntities = systemMenuMapper.selectList(queryWrapper);
        } else {
            systemMenuEntities = systemMenuMapper.selectList(null);
        }
        PageInfo<SystemMenuEntity> systemMenuEntityPageInfo = new PageInfo<>(systemMenuEntities);
        return new PageResultUtils(systemMenuEntityPageInfo);
    }

    @Override
    public List<SystemMenuEntity> listTree() {
        //获取所有的菜单列表
        List<SystemMenuEntity> systemMenuEntities = systemMenuMapper.selectList(null);
        //组装成树形
        //找到所有的一级菜单
        systemMenuEntities.stream().filter(s -> s.getParentId() == 0).map(menu -> {
            //找到所有一级菜单的子菜单
            menu.setChildren(getChildes(menu, systemMenuEntities));
            return menu;
        }).sorted((menu1, menu2) -> {
            //排序
            return (menu1.getOrderNum() == null ? 0 : menu1.getOrderNum()) - (menu2.getOrderNum() == null ? 0 : menu2.getOrderNum());
        }).collect(Collectors.toList());

        return systemMenuEntities;
    }

    //递归查找所有的子菜单
    private static List<SystemMenuEntity> getChildes(SystemMenuEntity systemMenuEntity,
                                                     List<SystemMenuEntity> all) {
        List<SystemMenuEntity> children =
                all.stream().filter(s -> s.getParentId() == systemMenuEntity.getMenuId())
                        .map(menu -> {
                            menu.setChildren(getChildes(menu, all));
                            return menu;
                        }).sorted((menu1, menu2) -> {
                    //排序
                    return (menu1.getOrderNum() == null ? 0 : menu1.getOrderNum()) - (menu2.getOrderNum() == null ? 0 : menu2.getOrderNum());
                }).collect(Collectors.toList());
        return children;
    }

}
