package com.youfan.bussiness.service.impl;

import java.util.List;
import com.youfan.common.utils.DateUtils;
import com.youfan.system.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youfan.bussiness.mapper.YoufanEquipmentMapper;
import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.bussiness.service.IYoufanEquipmentService;

/**
 * 设备管理Service业务层处理
 *
 * @author
 * @date 2022-07-23
 */
@Service
public class YoufanEquipmentServiceImpl implements IYoufanEquipmentService 
{
    @Autowired
    private YoufanEquipmentMapper youfanEquipmentMapper;

    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    @Override
    public YoufanEquipment selectYoufanEquipmentById(Long id)
    {
        return youfanEquipmentMapper.selectYoufanEquipmentById(id);
    }

    /**
     * 查询设备管理列表
     * 
     * @param youfanEquipment 设备管理
     * @return 设备管理
     */
    @Override
    public List<YoufanEquipment> selectYoufanEquipmentList(YoufanEquipment youfanEquipment)
    {
        return youfanEquipmentMapper.selectYoufanEquipmentList(youfanEquipment);
    }

    /**
     * 查询近期借用设备列表
     *
     * @return 设备管理集合
     */
    @Override
    public List<YoufanEquipment> selectUseEquipmentList() {
        return youfanEquipmentMapper.selectUseEquipmentList();
    }

    /**
     * 新增设备管理
     * 
     * @param youfanEquipment 设备管理
     * @return 结果
     */
    @Override
    public int insertYoufanEquipment(YoufanEquipment youfanEquipment)
    {
        youfanEquipment.setCreateTime(DateUtils.getNowDate());
        return youfanEquipmentMapper.insertYoufanEquipment(youfanEquipment);
    }

    /**
     * 修改设备管理
     * 
     * @param youfanEquipment 设备管理
     * @return 结果
     */
    @Override
    public int updateYoufanEquipment(YoufanEquipment youfanEquipment)
    {
        youfanEquipment.setUpdateTime(DateUtils.getNowDate());
        return youfanEquipmentMapper.updateYoufanEquipment(youfanEquipment);
    }

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanEquipmentByIds(Long[] ids)
    {
        return youfanEquipmentMapper.deleteYoufanEquipmentByIds(ids);
    }

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanEquipmentById(Long id)
    {
        return youfanEquipmentMapper.deleteYoufanEquipmentById(id);
    }
}
