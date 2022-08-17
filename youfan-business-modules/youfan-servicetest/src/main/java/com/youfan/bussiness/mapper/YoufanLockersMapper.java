package com.youfan.bussiness.mapper;

import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.bussiness.domain.YoufanLockers;

import java.util.List;

/**
 * 设备管理Mapper接口
 * 
 * @author
 * @date 2022-07-23
 */
public interface YoufanLockersMapper
{
    /**
     * 查询储物柜
     * 
     * @param id 储物柜主键
     * @return 储物柜管理
     */
    public YoufanLockers selectYoufanLockersById(Long id);

    /**
     * 查询储物柜列表
     * 
     * @param lockers 储物柜
     * @return 储物柜管理集合
     */
    public List<YoufanLockers> selectYoufanLockersList(YoufanLockers lockers);

    /**
     * 新增储物柜
     * 
     * @param lockers 储物柜
     * @return 结果
     */
    public int insertYoufanLockers(YoufanLockers lockers);

    /**
     * 修改储物柜
     * 
     * @param lockers 设备管理
     * @return 结果
     */
    public int updateYoufanLockers(YoufanLockers lockers);

    /**
     * 删除储物柜
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteYoufanLockersById(Long id);

    /**
     * 批量删除储物柜
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteYoufanLockersByIds(Long[] ids);
}
