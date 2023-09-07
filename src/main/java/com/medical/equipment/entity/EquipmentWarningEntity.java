package com.medical.equipment.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * equipment warning
 * @author 
 */
@TableName("equipment_warning")
public class EquipmentWarningEntity  {

    // TODO  Warn: Could not find @TableId in Class: com.medical.equipment.entity.EquipmentWarningEntity.
    @TableId(value = "equipment_id")
    private Integer equipmentId;

    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 预警类型
     */
    private String equipmentWarningName;

    /**
     * 状态类型
     */
    private String state;

    /**
     * 最大值
     */
    private Double max;

    /**
     * 最小值
     */
    private Double min;

    /**
     * 预警类型id
     */
    private Integer equipmentWarningId;

    /**
     * 设备类型id
     */
    private Integer equipmentTypeId;

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentWarningName() {
        return equipmentWarningName;
    }

    public void setEquipmentWarningName(String equipmentWarningName) {
        this.equipmentWarningName = equipmentWarningName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Integer getEquipmentWarningId() {
        return equipmentWarningId;
    }

    public void setEquipmentWarningId(Integer equipmentWarningId) {
        this.equipmentWarningId = equipmentWarningId;
    }

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(Integer equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    @Override
    public String toString() {
        return "EquipmentWarningEntity{" +
                "equipmentId=" + equipmentId +
                ", equipmentType='" + equipmentType + '\'' +
                ", equipmentWarningName='" + equipmentWarningName + '\'' +
                ", state='" + state + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", equipmentWarningId=" + equipmentWarningId +
                ", equipmentTypeId=" + equipmentTypeId +
                '}';
    }
}