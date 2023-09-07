package com.medical.equipment.controller;

import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.SystemUserEntity;
import com.medical.equipment.service.SystemUserService;
import com.medical.equipment.utils.PageQuery;
import com.medical.equipment.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title:  SystemUserController
 * @projectName  equipment
 * @description:
 */
@RestController
@RequestMapping("/systemUser")
@Api(value = "系统用户控制层", tags = "系统用户相关的接口")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;


    @PostMapping("/insert")
    @ApiOperation(value = "系统用户新增")
    public R insert(HttpServletRequest request,
                    @Validated({AddGroup.class}) @RequestBody SystemUserEntity systemUserEntity) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        if (user != null) {
            systemUserEntity.setOperator(user.getAccount());
        }
        systemUserService.insert(systemUserEntity);
        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "系统用户修改")
    public R update(HttpServletRequest request,
                    @Validated({UpdateGroup.class}) @RequestBody SystemUserEntity systemUserEntity) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        systemUserEntity.setOperator(user.getAccount());
        systemUserService.update(systemUserEntity);
        return R.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "系统用户软删除")
    public R delete(HttpServletRequest request, int userId) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        int delete = systemUserService.delete(user.getAccount(), userId);
        return delete > 0 ? R.ok() : R.error("删除失败");
    }


    @PostMapping("/login")
    @ApiOperation(value = "系统用户登录接口")
    public R login(HttpServletRequest request, HttpServletResponse response, @RequestParam String userAccount,
                   @RequestParam String password) {
        systemUserService.login(request, response, userAccount, password);
        return R.ok();
    }


    @GetMapping("/logout")
    @ApiOperation(value = "系统用户退出登录接口")
    public R logout(HttpServletRequest request) {
        SystemUserEntity systemUserEntity = (SystemUserEntity) request.getSession().getAttribute("user");
        int logout = systemUserService.logout(systemUserEntity.getId());
        request.getSession().removeAttribute("user");
        return logout > 0 ? R.ok() : R.error("退出失败");
    }

    @GetMapping("/getById")
    @ApiOperation(value = "根据id查询用户信息接口")
    public R getById(int id) {
        return R.ok().put(RConstant.data, systemUserService.getById(id));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户信息接口")
    public R page(@RequestBody PageQuery<SystemUserEntity> pageQuery) {
        return R.ok().put(RConstant.data, systemUserService.list(pageQuery));
    }


    @PostMapping("/updatePwd")
    @ApiOperation(value = "用户修改登录密码接口")
    public R updatePwd(HttpServletRequest request, @RequestParam String oldPwd, @RequestParam String newPwd) {
        int result = systemUserService.updatePwd(request, oldPwd, newPwd);
        return result > 0 ? R.ok() : R.error("密码修改失败！");
    }

    @GetMapping("/resetPwd")
    @ApiOperation(value = "重置用户登录密码接口")
    public R resetPwd(@RequestParam int userId) {
        systemUserService.resetPwd(userId);
        return R.ok();
    }

}
