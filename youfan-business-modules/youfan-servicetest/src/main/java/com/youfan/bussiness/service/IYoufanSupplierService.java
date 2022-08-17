package com.youfan.bussiness.service;

import java.util.List;
import com.youfan.bussiness.domain.YoufanSupplier;

/**
 * 采购管理Service接口
 * 
 * @author youfan
 * @date 2022-07-03
 */
public interface IYoufanSupplierService 
{
    /**
     * 查询采购管理
     * 
     * @param id 采购管理主键
     * @return 采购管理
     */
    public YoufanSupplier selectYoufanSupplierById(Long id);

    /**
     * 查询采购管理列表
     * 
     * @param youfanSupplier 采购管理
     * @return 采购管理集合
     */
    public List<YoufanSupplier> selectYoufanSupplierList(YoufanSupplier youfanSupplier);

    /**
     * 新增采购管理
     * 
     * @param youfanSupplier 采购管理
     * @return 结果
     */
    public int insertYoufanSupplier(YoufanSupplier youfanSupplier);

    /**
     * 修改采购管理
     * 
     * @param youfanSupplier 采购管理
     * @return 结果
     */
    public int updateYoufanSupplier(YoufanSupplier youfanSupplier);

    /**
     * 批量删除采购管理
     * 
     * @param ids 需要删除的采购管理主键集合
     * @return 结果
     */
    public int deleteYoufanSupplierByIds(Long[] ids);

    /**
     * 删除采购管理信息
     * 
     * @param id 采购管理主键
     * @return 结果
     */
    public int deleteYoufanSupplierById(Long id);
}
