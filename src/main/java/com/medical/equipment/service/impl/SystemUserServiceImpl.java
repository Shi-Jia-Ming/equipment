package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.equipment.constant.OtherConstant;
import com.medical.equipment.entity.SystemUserEntity;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.SystemUserMapper;
import com.medical.equipment.service.SystemUserService;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public int insert(SystemUserEntity systemUserEntity) {
        isExist(systemUserEntity.getAccount());
        systemUserEntity.setCreateTime(new Date());
        //密码加密
        String md5Pwd =
                PasswordUtil.md5(systemUserEntity.getPassword() + systemUserEntity.getPassword());
        systemUserEntity.setPassword(md5Pwd);
        return systemUserMapper.insert(systemUserEntity);
    }


    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, String userAccount, String password) {
        //根据用户名查询数据库中对应的用户信息
        LambdaQueryWrapper<SystemUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SystemUserEntity::getId, SystemUserEntity::getName,
                SystemUserEntity::getPassword,
                SystemUserEntity::getStatus, SystemUserEntity::getAccount)
                .eq(SystemUserEntity::getAccount, userAccount).eq(SystemUserEntity::getValid, 1);
        List<SystemUserEntity> systemUsers = systemUserMapper.selectList(lambdaQueryWrapper);
        if (systemUsers.isEmpty()) {
            throw new ErrorException("用户信息错误！");
        }
        SystemUserEntity systemUser = systemUsers.get(0);
        //判断用户名密码是否正确（密码需加密保存）
        // 将传入的密码进行md5加密
        // String md5Pwd = PasswordUtil.md5(password + password);
        // 登录信息错误
        if (!password.equals(systemUser.getPassword())) {
            throw new ErrorException("用户信息错误！");
        }
        //判断用户是否已登录（同一个账户不能在同一时刻登录多个）
        // TODO 暂时去掉网页版异地登录验证
        // if (systemUser.getStatus() == 1) {
        //     throw new ErrorException("该用户已在其他地方登录，可联系管理员强行下线！");
        // }
        systemUser.setStatus((byte) 1);
        systemUser.setUpdateTime(new Date());
        systemUserMapper.updateById(systemUser);


        //首次登录成功后将用户id存入cookie，便于后续拦截器使用
        Cookie cookie = new Cookie("userId", systemUser.getId().toString());
        //设置cookie名称
        System.out.println(cookie.getName());
        //设置Maximum Age
        cookie.setMaxAge(3600);
        // 过期时间s （默认是-1 关闭浏览器失效）
//        cookie.setHttpOnly(false);
        //设置cookie路径为当前项目路径
//        cookie.setPath(request.getContextPath());
        cookie.setPath("/");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        cookie.setDomain("127.0.0.1");
        //添加cookie
        response.addCookie(cookie);
        System.out.println("Cookie added!");

        //将用户存入session，方便后续获取
        request.getSession().setAttribute("user", systemUser);
    }

    @Override
    public int logout(Integer userId) {
        LambdaUpdateWrapper<SystemUserEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SystemUserEntity::getStatus, 0).eq(SystemUserEntity::getId, userId);
        return systemUserMapper.update(null, updateWrapper);
    }

    @Override
    public int update(SystemUserEntity systemUserEntity) {
        isExist(systemUserEntity.getAccount(), systemUserEntity.getId());
        systemUserEntity.setUpdateTime(new Date());
        return systemUserMapper.updateById(systemUserEntity);
    }

    @Override
    public int delete(String operator, int id) {
        LambdaUpdateWrapper<SystemUserEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SystemUserEntity::getValid, 0)
                .set(SystemUserEntity::getUpdateTime, new Date())
                .set(SystemUserEntity::getOperator, operator)
                .eq(SystemUserEntity::getId, id);
        return systemUserMapper.update(null, updateWrapper);
    }

    @Override
    public PageResultUtils list(PageQuery<SystemUserEntity> pageQuery) {
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(pageQuery.getInfo().getName()),
                SystemUserEntity::getName, pageQuery.getInfo().getName()).eq(SystemUserEntity::getValid, 1);
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<SystemUserEntity> systemUserEntities = systemUserMapper.selectList(queryWrapper);
        PageInfo<SystemUserEntity> pageInfo = new PageInfo<>(systemUserEntities);
        return new PageResultUtils(pageInfo);
    }

    @Override
    public SystemUserEntity getById(int userId) {
        return systemUserMapper.selectById(userId);
    }

    @Override
    public int resetPwd(int userId) {
        LambdaUpdateWrapper<SystemUserEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SystemUserEntity::getPassword,
                PasswordUtil.md5(OtherConstant.SYS_PWD + OtherConstant.SYS_PWD))
                .set(SystemUserEntity::getUpdateTime, new Date())
                .eq(SystemUserEntity::getId, userId);
        return systemUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updatePwd(HttpServletRequest request, String oldPwd, String newPwd) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        SystemUserEntity systemUserEntity = systemUserMapper.selectById(user.getId());
        if (!PasswordUtil.md5(oldPwd + oldPwd).equals(systemUserEntity.getPassword())) {
            throw new ErrorException("密码错误！");
        }
        systemUserEntity.setPassword(PasswordUtil.md5(newPwd + newPwd));
        return systemUserMapper.updateById(systemUserEntity);
    }


    /**
     * 新增时判断用户登录账号是否已存在
     *
     * @param account
     */
    private void isExist(String account) {
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SystemUserEntity::getId).eq(SystemUserEntity::getAccount, account);
        List<SystemUserEntity> systemUserEntities = systemUserMapper.selectList(queryWrapper);
        if (!systemUserEntities.isEmpty()) {
            throw new ErrorException("该账号已存在！");
        }

    }


    /**
     * 修改时判断用户登录账号是否已存在，排除自身
     *
     * @param account
     * @param id
     */
    private void isExist(String account, int id) {
        LambdaQueryWrapper<SystemUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SystemUserEntity::getId).eq(SystemUserEntity::getAccount, account);
        List<SystemUserEntity> systemUserEntities = systemUserMapper.selectList(queryWrapper);
        if (!systemUserEntities.isEmpty()) {
            for (SystemUserEntity systemUserEntity : systemUserEntities) {
                if (systemUserEntity.getId() != id) {
                    throw new ErrorException("该账号已存在！");
                }
            }
        }

    }
}
