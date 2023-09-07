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
 * @TableName fiber_temperature_detection
 */
@TableName("fiber_temperature_detection")
public class FiberTemperatureDetectionEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8133235157372134994L;
    /**
     * 主键id
     */
    @NotNull(message = RConstant.update,groups = {UpdateGroup.class})
    @Null(message = RConstant.add,groups = {AddGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 设备id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long equipmentId;

    /**
     * 一通道温度
     */
    private Double firstChannelTemperature;



    /**
     * 二通道温度
     */
    private Double secondChannelTemperature;



    /**
     * 三通道温度
     */
    private Double thirdChannelTemperature;


    /**
     * 四通道温度
     */
    private Double fourthChannelTemperature;



    /**
     * 关联用户id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long userId;

    /**
     * 床位id
     */
    @NotNull(message = RConstant.common,groups = {AddGroup.class,UpdateGroup.class})
    private Long bedId;



    /**
     * 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键id
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
     * 一通道温度
     */
    public Double getFirstChannelTemperature() {
        return firstChannelTemperature;
    }

    /**
     * 一通道温度
     */
    public void setFirstChannelTemperature(Double firstChannelTemperature) {
        this.firstChannelTemperature = firstChannelTemperature;
    }



    /**
     * 二通道温度
     */
    public Double getSecondChannelTemperature() {
        return secondChannelTemperature;
    }

    /**
     * 二通道温度
     */
    public void setSecondChannelTemperature(Double secondChannelTemperature) {
        this.secondChannelTemperature = secondChannelTemperature;
    }


    /**
     * 三通道温度
     */
    public Double getThirdChannelTemperature() {
        return thirdChannelTemperature;
    }

    /**
     * 三通道温度
     */
    public void setThirdChannelTemperature(Double thirdChannelTemperature) {
        this.thirdChannelTemperature = thirdChannelTemperature;
    }


    /**
     * 四通道温度
     */
    public Double getFourthChannelTemperature() {
        return fourthChannelTemperature;
    }

    /**
     * 四通道温度
     */
    public void setFourthChannelTemperature(Double fourthChannelTemperature) {
        this.fourthChannelTemperature = fourthChannelTemperature;
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

    /**
     * 床位id
     */
    public Long getBedId() {
        return bedId;
    }

    /**
     * 床位id
     */
    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }


}