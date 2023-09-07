package com.medical.equipment.controller;

import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;
import com.medical.equipment.entity.DictionaryEntity;
import com.medical.equipment.service.DictionaryService;
import com.medical.equipment.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("dic")
@Api(value = "字典表控制层", tags = "字典表管理相关的接口")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    @PostMapping("add")
    @ApiOperation(value = "字典表新增接口")
    public R addDic(@Validated({AddGroup.class}) @Valid @RequestBody DictionaryEntity dictionary) {
        dictionaryService.addDic(dictionary);
        return R.ok("新增成功");
    }

    @DeleteMapping("del")
    @ApiOperation(value = "字典表删除接口")
    public R delDic( @RequestParam Long id) {
        dictionaryService.delDic(id);
        return R.ok("删除成功");
    }

    @GetMapping("findAll")
    @ApiOperation(value = "字典表查询所有接口")
    public R findAll(){
     List<DictionaryEntity> dictionaryEntities= dictionaryService.findAll();
        return R.ok().put(RConstant.data, dictionaryEntities);
    }

    @PutMapping("update")
    @ApiOperation(value = "字典表编辑接口")
    public R updateDic(@Validated({UpdateGroup.class}) @Valid @RequestBody DictionaryEntity dictionary) {
        dictionaryService.updateDic(dictionary);
        return R.ok("修改成功");
    }

    @GetMapping("findById")
    @ApiOperation(value = "字典表查询单个详情接口")
    public R findById(@RequestParam Long id) {
      DictionaryEntity dictionaryEntity=  dictionaryService.findById(id);
        return R.ok().put(RConstant.data, dictionaryEntity);
    }

    @GetMapping("/findByType")
    public R findByType(@RequestParam String type) {
        List<DictionaryEntity> dictionaryEntities = dictionaryService.findByType(type);
        return R.ok().put(RConstant.data, dictionaryEntities);
    }

}
