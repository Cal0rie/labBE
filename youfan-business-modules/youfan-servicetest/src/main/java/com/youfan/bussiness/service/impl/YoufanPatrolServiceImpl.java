package com.youfan.bussiness.service.impl;

import java.util.List;
import com.youfan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youfan.bussiness.mapper.YoufanPatrolMapper;
import com.youfan.bussiness.domain.YoufanPatrol;
import com.youfan.bussiness.service.IYoufanPatrolService;

/**
 * 巡检管理Service业务层处理
 *
 * @author
 * @date 2022-07-23
 */
@Service
public class YoufanPatrolServiceImpl implements IYoufanPatrolService 
{
    @Autowired
    private YoufanPatrolMapper youfanPatrolMapper;

    /**
     * 查询巡检管理
     * 
     * @param id 巡检管理主键
     * @return 巡检管理
     */
    @Override
    public YoufanPatrol selectYoufanPatrolById(Long id)
    {
        return youfanPatrolMapper.selectYoufanPatrolById(id);
    }

    /**
     * 查询巡检管理列表
     * 
     * @param youfanPatrol 巡检管理
     * @return 巡检管理
     */
    @Override
    public List<YoufanPatrol> selectYoufanPatrolList(YoufanPatrol youfanPatrol)
    {
        return youfanPatrolMapper.selectYoufanPatrolList(youfanPatrol);
    }

    /**
     * 新增巡检管理
     * 
     * @param youfanPatrol 巡检管理
     * @return 结果
     */
    @Override
    public int insertYoufanPatrol(YoufanPatrol youfanPatrol)
    {
        youfanPatrol.setCreateTime(DateUtils.getNowDate());
        return youfanPatrolMapper.insertYoufanPatrol(youfanPatrol);
    }

    /**
     * 修改巡检管理
     * 
     * @param youfanPatrol 巡检管理
     * @return 结果
     */
    @Override
    public int updateYoufanPatrol(YoufanPatrol youfanPatrol)
    {
        youfanPatrol.setUpdateTime(DateUtils.getNowDate());
        return youfanPatrolMapper.updateYoufanPatrol(youfanPatrol);
    }

    /**
     * 批量删除巡检管理
     * 
     * @param ids 需要删除的巡检管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanPatrolByIds(Long[] ids)
    {
        return youfanPatrolMapper.deleteYoufanPatrolByIds(ids);
    }

    /**
     * 删除巡检管理信息
     * 
     * @param id 巡检管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanPatrolById(Long id)
    {
        return youfanPatrolMapper.deleteYoufanPatrolById(id);
    }
}
