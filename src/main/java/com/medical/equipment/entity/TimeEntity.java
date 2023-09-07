package com.medical.equipment.entity;

import java.io.Serializable;
import java.util.Date;

public class TimeEntity implements Serializable {
    private static final long serialVersionUID = 6502245293246466084L;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
