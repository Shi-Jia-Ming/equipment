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
 * 
 * @TableName detection_arm_ring_equipment
 */
@TableName("detection_arm_ring_equipment")
public class DetectionArmRingEquipmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6193924155188244117L;
    /**
     * 
     */
    @Null(message = RConstant.add,groups = {AddGroup.class})
    @NotNull(message = RConstant.update,groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 步数
     */
    private Integer stepNumber;

    /**
     * 温度
     */
    private Double temperature;



    /**
     * 心率
     */
    private Integer heartRate;



    /**
     * 关联设备id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long equipmentId;

    /**
     * 关联用户id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long userId;
    /**
     * 臂环类别id
     */
    private String armId;


    public String getArmId() {
        return armId;
    }

    public void setArmId(String armId) {
        this.armId = armId;
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
     * 步数
     */
    public Integer getStepNumber() {
        return stepNumber;
    }

    /**
     * 步数
     */
    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
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
     * 心率
     */
    public Integer getHeartRate() {
        return heartRate;
    }

    /**
     * 心率
     */
    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    /**
     * 关联设备id
     */
    public Long getEquipmentId() {
        return equipmentId;
    }

    /**
     * 关联设备id
     */
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
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