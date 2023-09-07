package com.medical.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.equipment.commonInterface.AddGroup;
import com.medical.equipment.commonInterface.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * system_user
 * 系统用户
 *
 * @author
 */
@TableName("system_user")
public class SystemUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Null(message = "新增系统用户时，不需要用户id", groups = {AddGroup.class})
    @NotNull(message = "修改系统用户时，用户id必传", groups = UpdateGroup.class)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String account;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空", groups = {AddGroup.class})
    private String password;

    /**
     * 手机
     */
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 登录状态
     */
    private Byte status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 操作人
     */
    private String operator;
    /**
     * 是否有效（1.有效；0.无效）
     */
    private Byte valid;

    private Integer authority;

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Sets the valid
     * <p>You can use getValid() to get the value of valid</p>
     *
     * @param valid valid
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * Gets the value of valid
     *
     * @return the value of valid
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * Sets the updateTime
     * <p>You can use getUpdateTime() to get the value of updateTime</p>
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Gets the value of updateTime
     *
     * @return the value of updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}