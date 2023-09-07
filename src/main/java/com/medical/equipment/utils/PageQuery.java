package com.medical.equipment.utils;

import com.medical.equipment.constant.RConstant;

import javax.validation.constraints.NotNull;

public class PageQuery<T> {
    /**
     * 模糊查询对象
     */

    private T info;
    @NotNull(message = RConstant.common)
    private Integer pageNum;
    @NotNull(message = RConstant.common)
    private Integer pageSize;


    /**
     * 获取模糊查询对象
     *
     * @return
     */
    public T getInfo() {
        return info;
    }

    /**
     * 设置模糊查询对象
     *
     * @param info 提交的模糊查询对象
     */
    public void setInfo(T info) {
        this.info = info;
    }

    /**
     * Sets the pageNum
     * <p>You can use getPageNum() to get the value of pageNum</p>
     *
     * @param pageNum pageNum
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Gets the value of pageNum
     *
     * @return the value of pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * Sets the pageSize
     * <p>You can use getPageSize() to get the value of pageSize</p>
     *
     * @param pageSize pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the value of pageSize
     *
     * @return the value of pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public PageQuery() {
    }
}
