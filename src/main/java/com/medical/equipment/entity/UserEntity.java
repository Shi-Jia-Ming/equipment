package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName("user")
public class UserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7143851086724267642L;
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户性别 1男性 2女性
     */
    private Integer sex;

    /**
     * 用户血型
     */
    private String bloodType;

    /**
     * 用户身高
     */
    private Integer height;

    /**
     * 用户年龄
     */
    private Integer age;



    /**
     * 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 用户性别 1男性 2女性
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 用户性别 1男性 2女性
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 用户血型
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * 用户血型
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * 用户身高
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 用户身高
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 用户年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 用户年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }




}