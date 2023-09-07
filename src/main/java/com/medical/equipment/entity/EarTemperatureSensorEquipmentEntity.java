package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 耳温传感器设备
 * @TableName ear_temperature_sensor_equipment
 */
@TableName("ear_temperature_sensor_equipment")
public class EarTemperatureSensorEquipmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5332245866896081270L;

    @Null(message = RConstant.add,groups = {AddGroup.class})
    @NotNull(message = RConstant.update,groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long equipmentId;

    /**
     * 温度
     */
    private Double temperature;


    /**
     * 关联用户id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long userId;


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
     * 设备id
     */
    public Long getEquipmentId() {
        return equipmentId;
    }

    /**
     * 设备id
     */
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 温度
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * 温度
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }


    /**
     * 关联用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 关联用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}