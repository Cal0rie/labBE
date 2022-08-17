package com.youfan.bussiness.mapper;

import java.util.List;
import com.youfan.bussiness.domain.YoufanApplyRepair;

/**
 * 报修管理Mapper接口
 * 
 * @author youfan
 * @date 2022-07-03
 */
public interface YoufanApplyRepairMapper 
{
    /**
     * 查询报修管理
     * 
     * @param id 报修管理主键
     * @return 报修管理
     */
    public YoufanApplyRepair selectYoufanApplyRepairById(Long id);

    /**
     * 查询报修管理列表
     * 
     * @param youfanApplyRepair 报修管理
     * @return 报修管理集合
     */
    public List<YoufanApplyRepair> selectYoufanApplyRepairList(YoufanApplyRepair youfanApplyRepair);

    /**
     * 新增报修管理
     * 
     * @param youfanApplyRepair 报修管理
     * @return 结果
     */
    public int insertYoufanApplyRepair(YoufanApplyRepair youfanApplyRepair);

    /**
     * 修改报修管理
     * 
     * @param youfanApplyRepair 报修管理
     * @return 结果
     */
    public int updateYoufanApplyRepair(YoufanApplyRepair youfanApplyRepair);

    /**
     * 删除报修管理
     * 
     * @param id 报修管理主键
     * @return 结果
     */
    public int deleteYoufanApplyRepairById(Long id);

    /**
     * 批量删除报修管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYoufanApplyRepairByIds(Long[] ids);
}
