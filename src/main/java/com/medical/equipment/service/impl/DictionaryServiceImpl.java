package com.medical.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.medical.equipment.entity.DictionaryEntity;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.mapper.DictionaryMapper;
import com.medical.equipment.service.DictionaryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    @Transactional
    public void addDic(DictionaryEntity dictionary) {

        Integer code = dictionary.getCode() == null ? null : dictionary.getCode();

        // 根据传过来父级id去进行判断 是几级菜单

        //如果是添加一级菜单
        if (dictionary.getParentId() == 0) {
            findDicType(dictionary.getType());
            //直接进行添加
            dictionary.setCode(0);
            try {
                dictionaryMapper.insert(dictionary);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErrorException("新增一级字典表失败");
            }
        }
        if (dictionary.getParentId() != 0) {
            //根据父级id去查询这条id的数据
            DictionaryEntity dictionaryEntity = dictionaryMapper.selectOne(enabled().eq(DictionaryEntity::getId, dictionary.getParentId()));
            //如果他的父级id为0 则这是一个二级菜单
            if (dictionaryEntity.getParentId() == 0) {
                findDicType(dictionary.getType());
                dictionary.setCode(0);
                try {
                    dictionaryMapper.insert(dictionary);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ErrorException("新增二级字典表失败!");
                }
            } else {
                //否则的话是三级菜单
                String type = dictionary.getType();
                List<DictionaryEntity> byType = findByType(type);

                //如果根据类型查询出来有数据 说明是在以前的字典表上新增了一个
                if (byType.size() != 0) {
                    List<DictionaryEntity> dictionaryEntities1 = dictionaryMapper.selectList(enabled().eq(DictionaryEntity::getType, dictionary.getType()));
                    if (dictionaryEntities1.size() != 0) {
                        dictionaryEntities1.forEach(dictionary1 -> {
                            DictionaryEntity dictionaryEntity1 = dictionaryMapper.selectById(dictionary1.getParentId());
                            if (dictionaryEntity1.getParentId() == 0) {
                                throw new ErrorException("已存在此字典表类型！请重新命名！");
                            }
                        });
                    }
                    if (code != null) {
                        List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(enabled().eq(DictionaryEntity::getType, dictionary.getType()).eq(DictionaryEntity::getParentId, dictionary.getParentId()));
                        List<Integer> codes = new ArrayList<>();
                        dictionaryEntities.stream().filter(dictionaryCode -> codes.add(dictionaryCode.getCode())).collect(Collectors.toList());
                        if (codes.contains(code)) {
                            throw new ErrorException("该类型code已存在！请重新生成code");
                        }
                    }else {
                        List<Integer> codes = new ArrayList<>();
                        byType.stream().filter(dictionaryCode -> codes.add(dictionaryCode.getCode())).collect(Collectors.toList());
                        Integer maxCode = Collections.max(codes);
                        code = maxCode + 1;
                    }
                    dictionary.setCode(code);
                    try {
                        dictionaryMapper.insert(dictionary);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ErrorException("新增三级字典表失败!");
                    }
                } else {
                    //如果查询出来没有数据 说明是要新增的
                    //判断此type是否已经被使用
                    findDicType(dictionary.getType());
                    code = code == null ? 1 : code;
                    try {
                        dictionaryMapper.insert(new DictionaryEntity(null, dictionary.getType(), code, dictionary.getParentId(), dictionary.getDescription()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ErrorException("新增三级字典表失败!");
                    }
                }


            }
        }

    }


    public void findDicType(String type) {
        List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(enabled().eq(DictionaryEntity::getType, type));
        if (dictionaryEntities.size() != 0) {
            throw new ErrorException("已存在此字典表类型！请重新命名！");
        }
    }

    @Override
    @Transactional
    public void delDic(Long id) {

        //先去查询是否还有父级
        DictionaryEntity dictionaryEntity = dictionaryMapper.selectById(id);
        if (dictionaryEntity.getCode() == 0) {
            //再去找 是否有子级字典引用了这父级字典
            LambdaQueryWrapper<DictionaryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DictionaryEntity::getParentId, id);
            List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(lambdaQueryWrapper);
            if (dictionaryEntities.size() != 0) {
                throw new ErrorException("此字典还存在子级字典！请先删除子级字典！");
            }
        }

        LambdaUpdateWrapper<DictionaryEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(DictionaryEntity::getEnabled, 0)
                .eq(DictionaryEntity::getId, id);
        try {
            dictionaryMapper.update(null, lambdaUpdateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("字典删除失败！");
        }

    }

    @Override
    public List<DictionaryEntity> findAll() {


        List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(enabled());

        List<DictionaryEntity> leave1 =
                dictionaryEntities.stream()
                        .filter(dictionary -> dictionary.getParentId() == 0)
                        //设置我们的子菜单
                        .map(menu -> {
                            menu.setChildren(generateChildren(menu, dictionaryEntities));
                            return menu;
                        })
                        //给菜单排序
                        //得到传入的前后两个菜单
                        .sorted((menu1, menu2) -> {
                            return menu1.getCode() - menu2.getCode();
                        })
                        .collect(Collectors.toList());


        return leave1;
    }

    @Override
    @Transactional
    public void updateDic(DictionaryEntity dictionary) {
        try {
            dictionary.setUpdateTime(new Date());
            dictionaryMapper.updateById(dictionary);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException("字典表修改失败！");
        }
    }

    @Override
    public DictionaryEntity findById(Long id) {
        return dictionaryMapper.selectOne(enabled().eq(DictionaryEntity::getId, id));
    }


    public List<DictionaryEntity> findByType(String type) {
        LambdaQueryWrapper<DictionaryEntity> eq = enabled().eq(DictionaryEntity::getType, type);
        List<DictionaryEntity> dictionaryEntities = dictionaryMapper.selectList(eq);
        return dictionaryEntities;
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param root 当前菜单
     * @param all  所有菜单
     * @return
     */
    public List<DictionaryEntity> generateChildren(DictionaryEntity root, List<DictionaryEntity> all) {


        List<DictionaryEntity> childrenList = all.stream()
                //由于上面我们已经筛选出来了所有的父级id
                //如果当前遍历的父级id等于当前传入的id那么这个数据就是当前传入数据的子数据
                .filter(dictionary -> dictionary.getParentId().equals(root.getId()))
                .map(menu -> {
                    menu.setChildren(generateChildren(menu, all));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return menu1.getCode() - menu2.getCode();
                })
                .collect(Collectors.toList());

        return childrenList;
    }

    public LambdaQueryWrapper<DictionaryEntity> enabled() {
        LambdaQueryWrapper<DictionaryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictionaryEntity::getEnabled, 1);
        return lambdaQueryWrapper;
    }

}
