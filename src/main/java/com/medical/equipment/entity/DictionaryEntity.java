package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @TableName dictionary
 */
@TableName("dictionary")
public class DictionaryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 48052695311521424L;
    /**
     *
     */
    @Null(message = RConstant.add, groups = {AddGroup.class})
    @NotNull(message = RConstant.update, groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类别
     */
    @NotBlank(message = RConstant.common, groups = {AddGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 对应代码
     */
    private Integer code;

    /**
     * 父级id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long parentId;

    /**
     * 描述
     */
    @NotBlank(message = RConstant.common, groups = {AddGroup.class, UpdateGroup.class})
    private String description;



    @JsonInclude(JsonInclude.Include.NON_EMPTY) //当这个字段不为空的时候我们才返回这个数据
    @TableField(exist = false) //数据库里面不存在这个字段 我们用这个来区分 这样我们查询或者添加的时候只要没有指定 就不会操作此字段
    private List<DictionaryEntity> children;


    public DictionaryEntity() {
    }

    public DictionaryEntity(Long id, String type, Integer code, Long parentId, String description) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.parentId = parentId;
        this.description = description;
    }

    public List<DictionaryEntity> getChildren() {
        return children;
    }

    public void setChildren(List<DictionaryEntity> children) {
        this.children = children;
    }



    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 类别
     */
    public String getType() {
        return type;
    }

    /**
     * 类别
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 对应代码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 对应代码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 父级id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父级id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }


}