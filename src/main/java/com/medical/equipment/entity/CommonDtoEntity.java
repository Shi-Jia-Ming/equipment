package com.medical.equipment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
public class CommonDtoEntity implements Serializable {

    private static final long serialVersionUID = 1068478931890788757L;


    /**
     * 智能床垫
     */
    @JsonProperty(value = "VS")
    private String VS;
    //heart rate 心率
    @JsonProperty(value = "HR")
    private Integer HR;
    //respiratory rate 呼吸次数
    @JsonProperty(value = "RR")
    private Integer RR;
    //‘0-99’  # respiratory rate 呼吸暂停标志（显示数字）
    @JsonProperty(value = "AP")
    private Integer AP;
    // #呼吸暂停，介入标志（00绿灯，01红灯）
    @JsonProperty(value = "APE")
    private Integer APE;
    //# shake 抖动（00不显示，01身体抖动）
    @JsonProperty(value = "SK")
    private Integer SK;
    //     # 睡眠质量标志(云上计算)
    @JsonProperty(value = "AD")
    private Integer AD;
    //# 00红色：“无人在床”，01绿色：“床垫受压”，02绿色“有人在床”
    @JsonProperty(value = "NN")
    private Integer NN;
    // #床垫状态；绿灯01正常；黄灯02异常；红灯03非法
    @JsonProperty(value = "ER")
    private Integer ER;
    //# 心率原始数据字符串
    @JsonProperty(value = "HOD")
    private List<Integer> HOD;
    //# 呼吸原始数据字符串
    @JsonProperty(value = "ROD")
    private List<Integer> ROD;
    @JsonProperty(value = "ONLINE")
    private Integer ONLINE;


    /**
     * 耳温传感器
     */

    //Temperature 体温
    @JsonProperty(value = "TE")
    private double TE;
    //剩余电量
    @JsonProperty(value = "BATT")
    private String BATT;



    /**
     * 光纤传感器
     */
    @JsonProperty(value = "TE01")
    private Double TE01;    //1通道体温
    @JsonProperty(value = "TE02")
    private Double TE02;    //2通道体温
    @JsonProperty(value = "TE03")
    private Double TE03;    //3通道体温
    @JsonProperty(value = "TE04")
    private Double TE04;    //4通道体温


    /**
     * 智能输液泵
     */
    @JsonProperty(value = "TOTAL")
    private Integer TOTAL; //输液总量
    @JsonProperty(value = "SPEED")
    private Integer SPEED; //输液速度
    @JsonProperty(value = "CUR")
    private Integer CUR; //已输入液体
    @JsonProperty(value = "TIME")
    private String TIME; //剩余时间
    @JsonProperty(value = "ALERT")
    private String ALERT;



    /**
     * 臂环设备
     */
    @JsonProperty(value = "ST")
    private Integer ST; //步数
    @JsonProperty(value = "ID")
    private String ID; //臂环ID号


    public void setALERT(String ALERT) {
        this.ALERT = ALERT;
    }

    public void setVS(String VS) {
        this.VS = VS;
    }

    public void setST(Integer ST) {
        this.ST = ST;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTOTAL(Integer TOTAL) {
        this.TOTAL = TOTAL;
    }

    public void setSPEED(Integer SPEED) {
        this.SPEED = SPEED;
    }

    public void setCUR(Integer CUR) {
        this.CUR = CUR;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public void setTE01(Double TE01) {
        this.TE01 = TE01;
    }

    public void setTE02(Double TE02) {
        this.TE02 = TE02;
    }

    public void setTE03(Double TE03) {
        this.TE03 = TE03;
    }

    public void setTE04(Double TE04) {
        this.TE04 = TE04;
    }

    public void setBATT(String BATT) {
        this.BATT = BATT;
    }

    public void setTE(double TE) {
        this.TE = TE;
    }

    public void setONLINE(Integer ONLINE) {
        this.ONLINE = ONLINE;
    }

    public void setAD(Integer AD) {
        this.AD = AD;
    }

    public void setHR(Integer HR) {
        this.HR = HR;
    }

    public void setRR(Integer RR) {
        this.RR = RR;
    }

    public void setAP(Integer AP) {
        this.AP = AP;
    }

    public void setAPE(Integer APE) {
        this.APE = APE;
    }

    public void setSK(Integer SK) {
        this.SK = SK;
    }

    public void setNN(Integer NN) {
        this.NN = NN;
    }

    public void setER(Integer ER) {
        this.ER = ER;
    }

    public void setHOD(List<Integer> HOD) {
        this.HOD = HOD;
    }

    public void setROD(List<Integer> ROD) {
        this.ROD = ROD;
    }
}
