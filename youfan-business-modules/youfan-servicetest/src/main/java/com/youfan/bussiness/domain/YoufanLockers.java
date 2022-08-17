package com.youfan.bussiness.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import com.youfan.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 设备管理对象 youfan_lockers
 * 
 * @author
 * @date 2022-07-23
 */
@Data
public class YoufanLockers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    @Excel(name = "储物柜编号")
    private String lockersCode;

    /** 设备数量 */
    @Excel(name = "储物柜所在位置")
    private String lockersArea;

    /** 设备状态(0、入库 1、使用中 2、维护中) */
    @Excel(name = "储物柜设备总数")
    private String lockersCount;

    /** 设备使用部门 */
    @Excel(name = "储物柜设备剩余数量")
    private String lockersSurplus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLockersCode() {
        return lockersCode;
    }

    public void setLockersCode(String lockersCode) {
        this.lockersCode = lockersCode;
    }

    public String getLockersArea() {
        return lockersArea;
    }

    public void setLockersArea(String lockersArea) {
        this.lockersArea = lockersArea;
    }

    public String getLockersCount() {
        return lockersCount;
    }

    public void setLockersCount(String lockersCount) {
        this.lockersCount = lockersCount;
    }

    public String getLockersSurplus() {
        return lockersSurplus;
    }

    public void setLockersSurplus(String lockersSurplus) {
        this.lockersSurplus = lockersSurplus;
    }
}
