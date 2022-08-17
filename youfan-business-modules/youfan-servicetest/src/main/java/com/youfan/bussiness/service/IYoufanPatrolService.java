package com.youfan.bussiness.service;

import java.util.List;
import com.youfan.bussiness.domain.YoufanPatrol;

/**
 * 巡检管理Service接口
 * 
 * @author youfan
 * @date 2022-07-03
 */
public interface IYoufanPatrolService 
{
    /**
     * 查询巡检管理
     * 
     * @param id 巡检管理主键
     * @return 巡检管理
     */
    public YoufanPatrol selectYoufanPatrolById(Long id);

    /**
     * 查询巡检管理列表
     * 
     * @param youfanPatrol 巡检管理
     * @return 巡检管理集合
     */
    public List<YoufanPatrol> selectYoufanPatrolList(YoufanPatrol youfanPatrol);

    /**
     * 新增巡检管理
     * 
     * @param youfanPatrol 巡检管理
     * @return 结果
     */
    public int insertYoufanPatrol(YoufanPatrol youfanPatrol);

    /**
     * 修改巡检管理
     * 
     * @param youfanPatrol 巡检管理
     * @return 结果
     */
    public int updateYoufanPatrol(YoufanPatrol youfanPatrol);

    /**
     * 批量删除巡检管理
     * 
     * @param ids 需要删除的巡检管理主键集合
     * @return 结果
     */
    public int deleteYoufanPatrolByIds(Long[] ids);

    /**
     * 删除巡检管理信息
     * 
     * @param id 巡检管理主键
     * @return 结果
     */
    public int deleteYoufanPatrolById(Long id);
}
