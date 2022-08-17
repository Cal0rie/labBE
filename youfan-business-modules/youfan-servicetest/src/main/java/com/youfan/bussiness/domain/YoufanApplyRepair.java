package com.youfan.bussiness.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.youfan.common.core.domain.BaseEntity;

/**
 * 报修管理对象 youfan_apply_repair
 * 
 * @author youfan
 * @date 2022-07-03
 */
@Data
public class YoufanApplyRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 报修部门 */
    @Excel(name = "报修部门")
    private String applyDeptName;

    /** 报修人 */
    @Excel(name = "报修人")
    private String applyUser;

    /** 工单编号 */
    @Excel(name = "工单编号")
    private String orderNum;

    /** 工单分配人 */
    @Excel(name = "工单分配人")
    private String orderAllotuser;

    /** 工单执行人 */
    @Excel(name = "工单执行人")
    private String orderExecutor;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 验收结果 */
    @Excel(name = "验收结果")
    private String checkResult;

    /** 报修时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报修时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date executeTime;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyDeptName", getApplyDeptName())
            .append("applyUser", getApplyUser())
            .append("orderNum", getOrderNum())
            .append("orderAllotuser", getOrderAllotuser())
            .append("orderExecutor", getOrderExecutor())
            .append("handleResult", getHandleResult())
            .append("checkResult", getCheckResult())
            .append("applyTime", getApplyTime())
            .append("executeTime", getExecuteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
