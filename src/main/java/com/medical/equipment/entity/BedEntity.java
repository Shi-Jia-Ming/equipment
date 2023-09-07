package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;
import com.medical.equipment.constant.RConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName bed
 */
@TableName("bed")
public class BedEntity extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = -4999080790393649583L;
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @Null(message = RConstant.add,groups = {AddGroup.class})
    @NotNull(message = RConstant.update,groups = {UpdateGroup.class})
    private Long bedId;

    /**
     * 床位编码
     */
    @NotBlank(message = RConstant.common,groups = {AddGroup.class, UpdateGroup.class})
    private String bedCode;

    /**
     * 
     */
    public Long getBedId() {
        return bedId;
    }

    /**
     * 
     */
    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    /**
     * 床位编码
     */
    public String getBedCode() {
        return bedCode;
    }

    /**
     * 床位编码
     */
    public void setBedCode(String bedCode) {
        this.bedCode = bedCode;
    }


}