package com.youfan.bussiness.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.youfan.common.core.domain.BaseEntity;

/**
 * 采购管理对象 youfan_supplier
 * 
 * @author youfan
 * @date 2022-07-03
 */
@Data
public class YoufanSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备数量 */
    @Excel(name = "设备数量")
    private String equipmentNum;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveTime;

    /** 审批结果(0、未审批 1、通过 2、未通过) */
    @Excel(name = "审批结果(0、未审批 1、通过 2、未通过)")
    private String approveStatus;

    /** 采购金额 */
    @Excel(name = "采购金额")
    private String supplierAmount;

    /** 采购合同图片 */
    @Excel(name = "采购合同图片")
    private String supplierPic;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierName", getSupplierName())
            .append("equipmentName", getEquipmentName())
            .append("equipmentNum", getEquipmentNum())
            .append("applyTime", getApplyTime())
            .append("approveTime", getApproveTime())
            .append("approveStatus", getApproveStatus())
            .append("supplierAmount", getSupplierAmount())
            .append("supplierPic", getSupplierPic())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
