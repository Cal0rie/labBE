package com.youfan.bussiness.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youfan.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.youfan.common.core.domain.BaseEntity;

/**
 * 巡检管理对象 youfan_patrol
 * 
 * @author youfan
 * @date 2022-07-03
 */
@Data
public class YoufanPatrol extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 巡检人 */
    @Excel(name = "巡检人")
    private String patrolUser;

    /** 巡检计划 */
    @Excel(name = "巡检计划")
    private String patrolPlan;

    /** 巡检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date patrolTime;

    /** 巡检执行状态 */
    @Excel(name = "巡检执行状态")
    private String patrolStatus;

    /** 巡检结果 */
    @Excel(name = "巡检结果")
    private String patrolResult;

    /** 巡检内容 */
    @Excel(name = "巡检内容")
    private String patrolContent;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patrolUser", getPatrolUser())
            .append("patrolPlan", getPatrolPlan())
            .append("patrolTime", getPatrolTime())
            .append("patrolStatus", getPatrolStatus())
            .append("patrolResult", getPatrolResult())
            .append("patrolContent", getPatrolContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
