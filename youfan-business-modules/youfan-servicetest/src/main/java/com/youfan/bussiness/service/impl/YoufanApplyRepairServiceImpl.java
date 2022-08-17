package com.youfan.bussiness.service.impl;

import java.util.List;
import com.youfan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youfan.bussiness.mapper.YoufanApplyRepairMapper;
import com.youfan.bussiness.domain.YoufanApplyRepair;
import com.youfan.bussiness.service.IYoufanApplyRepairService;

/**
 * 报修管理Service业务层处理
 *
 * @author
 * @date 2022-07-23
 */
@Service
public class YoufanApplyRepairServiceImpl implements IYoufanApplyRepairService 
{
    @Autowired
    private YoufanApplyRepairMapper youfanApplyRepairMapper;

    /**
     * 查询报修管理
     * 
     * @param id 报修管理主键
     * @return 报修管理
     */
    @Override
    public YoufanApplyRepair selectYoufanApplyRepairById(Long id)
    {
        return youfanApplyRepairMapper.selectYoufanApplyRepairById(id);
    }

    /**
     * 查询报修管理列表
     * 
     * @param youfanApplyRepair 报修管理
     * @return 报修管理
     */
    @Override
    public List<YoufanApplyRepair> selectYoufanApplyRepairList(YoufanApplyRepair youfanApplyRepair)
    {
        return youfanApplyRepairMapper.selectYoufanApplyRepairList(youfanApplyRepair);
    }

    /**
     * 新增报修管理
     * 
     * @param youfanApplyRepair 报修管理
     * @return 结果
     */
    @Override
    public int insertYoufanApplyRepair(YoufanApplyRepair youfanApplyRepair)
    {
        youfanApplyRepair.setCreateTime(DateUtils.getNowDate());
        return youfanApplyRepairMapper.insertYoufanApplyRepair(youfanApplyRepair);
    }

    /**
     * 修改报修管理
     * 
     * @param youfanApplyRepair 报修管理
     * @return 结果
     */
    @Override
    public int updateYoufanApplyRepair(YoufanApplyRepair youfanApplyRepair)
    {
        youfanApplyRepair.setUpdateTime(DateUtils.getNowDate());
        return youfanApplyRepairMapper.updateYoufanApplyRepair(youfanApplyRepair);
    }

    /**
     * 批量删除报修管理
     * 
     * @param ids 需要删除的报修管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanApplyRepairByIds(Long[] ids)
    {
        return youfanApplyRepairMapper.deleteYoufanApplyRepairByIds(ids);
    }

    /**
     * 删除报修管理信息
     * 
     * @param id 报修管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanApplyRepairById(Long id)
    {
        return youfanApplyRepairMapper.deleteYoufanApplyRepairById(id);
    }
}
