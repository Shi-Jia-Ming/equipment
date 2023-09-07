package com.medical.equipment.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class BaseEntity  {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date updateTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String remark;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer enabled = 1;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
