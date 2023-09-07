package com.medical.equipment.service;

import com.medical.equipment.entity.SystemMenuEntity;
import com.medical.equipment.utils.PageResultUtils;

import java.util.List;

/**
 * @author zl
 * @title: SystemMenuService
 * @projectName equipment
 * @description:
 * @date 2022/2/15 16:06
 */
public interface SystemMenuService {
    /**
     * 新增菜单
     *
     * @param systemMenuEntity
     * @return
     */
    int insert(SystemMenuEntity systemMenuEntity);

    /**
     * 修改菜单
     *
     * @param systemMenuEntity
     * @return
     */
    int update(SystemMenuEntity systemMenuEntity);

    /**
     * 根据id删除菜单
     *
     * @param id
     * @return
     */
    int delete(String operator, int id);

    /**
     * 根据id获取菜单信息
     *
     * @param id
     * @return
     */
    SystemMenuEntity getById(int id);

    /**
     * 分页查询菜单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResultUtils list(int pageNum, int pageSize, Integer parentId);

    /**
     * 获取所有的菜单信息（树形结构）
     *
     * @return
     */
    List<SystemMenuEntity> listTree();
}
