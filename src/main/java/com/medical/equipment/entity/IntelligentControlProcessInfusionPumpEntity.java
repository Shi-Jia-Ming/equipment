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
 * 智能控程输液泵设备
 *
 * @TableName intelligent_control_process_infusion_pump
 */
@TableName("intelligent_control_process_infusion_pump")
public class IntelligentControlProcessInfusionPumpEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4119281358934549165L;
    /**
     *
     */
    @Null(message = RConstant.add, groups = {AddGroup.class})
    @NotNull(message = RConstant.update, groups = {UpdateGroup.class})
    @TableId(type = IdType.AUTO)
    private Long id;



    /**
     * 输液液体总量
     */
    private Integer totalLiquid;

    /**
     * 已经输液量
     */
    private Integer alreadyLiquid;

    /**
     * 液体流速
     */
    private Integer speed;

    /**
     * 剩余时间
     */
    private String residueTime;



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
     * 电量状态
     */

    private String dumpEnergy;

    /**
     * 设备报警信息
     */

    private String warnInfo;


    public String getWarnInfo() {
        return warnInfo;
    }

    public void setWarnInfo(String warnInfo) {
        this.warnInfo = warnInfo;
    }

    public String getDumpEnergy() {
        return dumpEnergy;
    }

    public void setDumpEnergy(String dumpEnergy) {
        this.dumpEnergy = dumpEnergy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(Integer totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    public Integer getAlreadyLiquid() {
        return alreadyLiquid;
    }

    public void setAlreadyLiquid(Integer alreadyLiquid) {
        this.alreadyLiquid = alreadyLiquid;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getResidueTime() {
        return residueTime;
    }

    public void setResidueTime(String residueTime) {
        this.residueTime = residueTime;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}