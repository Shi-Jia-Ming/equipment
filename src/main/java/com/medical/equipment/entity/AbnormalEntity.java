package com.medical.equipment.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbnormalEntity {
    private String time;
    private String userName;
    private String abnormalType;
    private String info;
    private Integer status;

    public AbnormalEntity(String time, String userName, String abnormalType, String info,Integer status) {
        this.time = time;
        this.userName = userName;
        this.abnormalType = abnormalType;
        if (info == null) {
            this.info = "无";
        }else {
            this.info = info;
        }
        this.status = status;
    }

    public AbnormalEntity() {

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String dateString = formatter.format(time);
        this.time = dateString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
