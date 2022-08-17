package com.youfan.bussiness.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.youfan.common.core.domain.BaseEntity;

/**
 * 设备管理对象 youfan_equipment
 * 
 * @author youfan
 * @date 2022-07-03
 */
@Data
public class YoufanEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备数量 */
    @Excel(name = "设备数量")
    private String equipmentNum;

    /** 设备状态(0、入库 1、使用中 2、维护中) */
    @Excel(name = "设备状态(0、入库 1、使用中 2、维护中)")
    private String equipmentStatus;

    /** 设备使用部门 */
    @Excel(name = "设备使用部门")
    private String equipmentDept;

    /** 设备采购时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "设备采购时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date supplierTime;

    /** 设备生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "设备生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionTime;

    /** 设备图片 */
    @Excel(name = "设备图片")
    private String equipmentPic;

    /** 设备使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date useTime;




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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentNum", getEquipmentNum())
            .append("equipmentStatus", getEquipmentStatus())
            .append("equipmentDept", getEquipmentDept())
            .append("supplierTime", getSupplierTime())
            .append("productionTime", getProductionTime())
            .append("equipmentPic", getEquipmentPic())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
