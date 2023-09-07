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
import java.util.List;

/**
 * 
 * @TableName intelligent_mattress
 */
@TableName("intelligent_mattress")
public class IntelligentMattressEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6507280992384526743L;
    /**
     * 
     */
    @Null(message = RConstant.add,groups = {AddGroup.class})
    @NotNull(message = RConstant.update,groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 心率
     */
    private Integer heartRate;

    /**
     * 呼吸
     */
    private Integer breathe;

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
     * 呼吸暂停，介入标志 0绿灯 1红灯
     */
    private Integer stopBreatheSign;


    /**
     * 呼吸暂停标志  呼吸率
     */
    private Integer respiratoryRate;

    /**
     * 抖动 0不显示 1身体抖动
     */
    private Integer shake;

    /**
     * 睡眠质量标志
     */
    private Integer sleepQualitySign;


    /**
     * 在床状态 0无人在床 1床垫受压 2有人在床
     */
    private Integer inBedStatus;

    /**
     * 床垫状态 绿灯01正常；黄灯02异常；红灯03非法
     */
    private Integer mattressStatus;


    /**
     * 心率原始数据
     */
    private String heartRateOriginalData;

    /**
     * 呼吸原始数据
     */
    private String breatheOriginalData;

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getHeartRateOriginalData() {
        return heartRateOriginalData;
    }

    public void setHeartRateOriginalData(String heartRateOriginalData) {
        this.heartRateOriginalData = heartRateOriginalData;
    }

    public String getBreatheOriginalData() {
        return breatheOriginalData;
    }

    public void setBreatheOriginalData(String breatheOriginalData) {
        this.breatheOriginalData = breatheOriginalData;
    }

    public Integer getMattressStatus() {
        return mattressStatus;
    }

    public void setMattressStatus(Integer mattressStatus) {
        this.mattressStatus = mattressStatus;
    }

    public Integer getInBedStatus() {
        return inBedStatus;
    }

    public void setInBedStatus(Integer inBedStatus) {
        this.inBedStatus = inBedStatus;
    }

    public Integer getSleepQualitySign() {
        return sleepQualitySign;
    }

    public void setSleepQualitySign(Integer sleepQualitySign) {
        this.sleepQualitySign = sleepQualitySign;
    }

    public Integer getShake() {
        return shake;
    }

    public void setShake(Integer shake) {
        this.shake = shake;
    }

    public Integer getStopBreatheSign() {
        return stopBreatheSign;
    }

    public void setStopBreatheSign(Integer stopBreatheSign) {
        this.stopBreatheSign = stopBreatheSign;
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
     * 呼吸
     */
    public Integer getBreathe() {
        return breathe;
    }

    /**
     * 呼吸
     */
    public void setBreathe(Integer breathe) {
        this.breathe = breathe;
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