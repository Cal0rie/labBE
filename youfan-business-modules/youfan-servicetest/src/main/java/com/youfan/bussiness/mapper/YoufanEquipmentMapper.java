package com.youfan.bussiness.mapper;

import java.util.List;
import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.system.domain.SysOperLog;

/**
 * 设备管理Mapper接口
 * 
 * @author youfan
 * @date 2022-07-03
 */
public interface YoufanEquipmentMapper 
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
     * @param youanEquipment 设备管理
     * @return 设备管理集合
     */
    public List<YoufanEquipment> selectYoufanEquipmentList(YoufanEquipment youanEquipment);


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
     * 删除设备管理
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteYoufanEquipmentById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYoufanEquipmentByIds(Long[] ids);
}
