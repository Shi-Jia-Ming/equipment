package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息表
 * &#064;TableName  equipment
 */
@TableName("equipment")
public class EquipmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6125912934164478776L;
    /**
     * 设备id
     */
    @Null(message = RConstant.add,groups = {AddGroup.class})
    @NotNull(message = RConstant.update,groups = UpdateGroup.class)
    @TableId(value = "equipment_id", type = IdType.AUTO)
    private Long equipmentId;

    /**
     * 设备编号
     */
    @NotBlank(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private String equipmentCode;

    /**
     * 设备类型
     */
    @NotNull(message =RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Integer equipmentType;


    @URL(message = RConstant.common, groups = {AddGroup.class, UpdateGroup.class})
    private String img;

    /**
     * 设备名称
     */
    @NotBlank(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private String equipmentName;

    /**
     * 设备在线状态
     */
    private Integer online;

    @NotBlank(message = RConstant.common, groups = {AddGroup.class, UpdateGroup.class})
    private String version;


    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 设备编号
     */
    public String getEquipmentCode() {
        return equipmentCode;
    }

    /**
     * 设备编号
     */
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    /**
     * 设备类型
     */
    public Integer getEquipmentType() {
        return equipmentType;
    }

    /**
     * 设备类型
     */
    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }

    /**
     * 设备名称
     */
    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * 设备名称
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }


}