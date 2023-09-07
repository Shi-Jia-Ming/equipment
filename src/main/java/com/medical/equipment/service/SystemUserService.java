package com.medical.equipment.service;


import com.medical.equipment.entity.SystemUserEntity;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zl
 *
 * @title:   SystemUserService
 * @projectName  medical_monitor_system
 * @description:
 * @date  2022/2/10 17:28
 */
public interface SystemUserService {

    /**
     * 新增系统用户
     *
     * @param systemUserEntity
     * @return
     */
    int insert(SystemUserEntity systemUserEntity);

    /**
     * 登录成功后将用户id存入session
     *
     * @param userAccount 用户账号
     * @param password    密码
     */
    void login(HttpServletRequest request, HttpServletResponse response, String userAccount, String password);

    /**
     * 用户退出系统，根据用户id修改用户在线状态
     *
     * @param userId 用户的id
     * @return logout > 0 ? R.ok() : R.error("退出失败")
     */
    int logout(Integer userId);

    /**
     * 修改用户信息
     *
     * @param systemUserEntity
     * @return
     */
    int update(SystemUserEntity systemUserEntity);

    /**
     * 根据用户id删除用户信息
     *
     * @param id
     * @return
     */
    int delete(String operator, int id);


    /**
     * 分页查询用户信息
     *
     * @param pageQuery
     * @return
     */
    PageResultUtils list(PageQuery<SystemUserEntity> pageQuery);


    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     */
    SystemUserEntity getById(int userId);


    /**
     * 重置用户密码
     *
     * @param userId
     * @return
     */
    int resetPwd(int userId);


    /**
     * 修改用户密码
     *
     * @param request
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int updatePwd(HttpServletRequest request, String oldPwd, String newPwd);
}
