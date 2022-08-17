package com.youfan.bussiness.service;

import java.util.List;
import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.system.domain.SysOperLog;

/**
 * 设备管理Service接口
 * 
 * @author youfan
 * @date 2022-07-03
 */
public interface IYoufanEquipmentService 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    public YoufanEquipment selectYoufanEquipmentById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param youfanEquipment 设备管理
     * @return 设备管理集合
     */
    public List<YoufanEquipment> selectYoufanEquipmentList(YoufanEquipment youfanEquipment);

    /**
     * 查询近期借用设备列表
     *
     * @return 设备管理集合
     */
    public List<YoufanEquipment> selectUseEquipmentList();

    /**
     * 新增设备管理
     * 
     * @param youfanEquipment 设备管理
     * @return 结果
     */
    public int insertYoufanEquipment(YoufanEquipment youfanEquipment);

    /**
     * 修改设备管理
     * 
     * @param youfanEquipment 设备管理
     * @return 结果
     */
    public int updateYoufanEquipment(YoufanEquipment youfanEquipment);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理主键集合
     * @return 结果
     */
    public int deleteYoufanEquipmentByIds(Long[] ids);

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteYoufanEquipmentById(Long id);
}
