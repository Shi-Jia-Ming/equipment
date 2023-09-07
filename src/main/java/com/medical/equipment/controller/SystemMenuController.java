package com.medical.equipment.controller;

import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.SystemMenuEntity;
import com.medical.equipment.entity.SystemUserEntity;
import com.medical.equipment.service.SystemMenuService;
import com.medical.equipment.utils.PageResultUtils;
import com.medical.equipment.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: SystemMenuController
 * @projectName equipment
 * @description:
 */
@RestController
@RequestMapping("/systemMenu")
@Api(value = "系统菜单控制层", tags = "系统菜单相关接口")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增菜单")
    public R insert(HttpServletRequest request,
                    @Validated({AddGroup.class}) @RequestBody SystemMenuEntity systemMenuEntity) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        systemMenuEntity.setCreateBy(user.getAccount());
        int insert = systemMenuService.insert(systemMenuEntity);
        return insert > 0 ? R.ok() : R.error("新增失败！");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改菜单")
    public R update(HttpServletRequest request,
                    @Validated({UpdateGroup.class}) @RequestBody SystemMenuEntity systemMenuEntity) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        systemMenuEntity.setUpdateBy(user.getAccount());
        int update = systemMenuService.update(systemMenuEntity);

        return update > 0 ? R.ok() : R.error("修改失败！");
    }

    @GetMapping("/delete")
    @ApiOperation(value = "根据id删除菜单")
    public R delete(HttpServletRequest request, @RequestParam("id") int id) {
        SystemUserEntity user = (SystemUserEntity) request.getSession().getAttribute("user");
        int delete = systemMenuService.delete(user.getAccount(), id);
        return delete > 0 ? R.ok() : R.error("删除失败！");
    }

    @GetMapping("/getById")
    @ApiOperation(value = "根据id获取菜单信息")
    public R getById(int id) {
        return R.ok().put(RConstant.data, systemMenuService.getById(id));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询菜单列表")
    public R page(@RequestParam int pageNum, @RequestParam int pageSize, Integer parentId) {
        PageResultUtils list = systemMenuService.list(pageNum, pageSize, parentId);
        return R.ok().put(RConstant.data, list);
    }

    @GetMapping("/listTree")
    @ApiOperation(value = "获取菜单树形结构")
    public R listTree() {
        return R.ok().put(RConstant.data, systemMenuService.listTree());
    }
}
