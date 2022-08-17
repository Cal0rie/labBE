package com.youfan.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import com.youfan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class Equipment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    private String equipmentName;

    /** 设备数量 */
    private String equipmentNum;

    /** 设备状态(0、入库 1、使用中 2、维护中) */
    private String equipmentStatus;

    /** 设备使用部门 */
    private String equipmentDept;

    /** 设备采购时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date supplierTime;

    /** 设备生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionTime;

    /** 设备图片 */
    private String equipmentPic;

    /** 设备使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date useTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public String getEquipmentDept() {
        return equipmentDept;
    }

    public void setEquipmentDept(String equipmentDept) {
        this.equipmentDept = equipmentDept;
    }

    public Date getSupplierTime() {
        return supplierTime;
    }

    public void setSupplierTime(Date supplierTime) {
        this.supplierTime = supplierTime;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public String getEquipmentPic() {
        return equipmentPic;
    }

    public void setEquipmentPic(String equipmentPic) {
        this.equipmentPic = equipmentPic;
    }

    /**
     * 使用次数
     */
    private int useTimes;

    /**
     * 储物柜编号
     */
    private String lockersCode;

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public int getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(int useTimes) {
        this.useTimes = useTimes;
    }

    public String getLockersCode() {
        return lockersCode;
    }

    public void setLockersCode(String lockersCode) {
        this.lockersCode = lockersCode;
    }

}
