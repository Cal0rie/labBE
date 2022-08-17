package com.youfan.bussiness.service.impl;

import java.util.List;
import com.youfan.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youfan.bussiness.mapper.YoufanSupplierMapper;
import com.youfan.bussiness.domain.YoufanSupplier;
import com.youfan.bussiness.service.IYoufanSupplierService;

/**
 * 采购管理Service业务层处理
 * 
 * @author
 * @date 2022-07-23
 */
@Service
public class YoufanSupplierServiceImpl implements IYoufanSupplierService 
{
    @Autowired
    private YoufanSupplierMapper youfanSupplierMapper;

    /**
     * 查询采购管理
     * 
     * @param id 采购管理主键
     * @return 采购管理
     */
    @Override
    public YoufanSupplier selectYoufanSupplierById(Long id)
    {
        return youfanSupplierMapper.selectYoufanSupplierById(id);
    }

    /**
     * 查询采购管理列表
     * 
     * @param youfanSupplier 采购管理
     * @return 采购管理
     */
    @Override
    public List<YoufanSupplier> selectYoufanSupplierList(YoufanSupplier youfanSupplier)
    {
        return youfanSupplierMapper.selectYoufanSupplierList(youfanSupplier);
    }

    /**
     * 新增采购管理
     * 
     * @param youfanSupplier 采购管理
     * @return 结果
     */
    @Override
    public int insertYoufanSupplier(YoufanSupplier youfanSupplier)
    {
        youfanSupplier.setCreateTime(DateUtils.getNowDate());
        return youfanSupplierMapper.insertYoufanSupplier(youfanSupplier);
    }

    /**
     * 修改采购管理
     * 
     * @param youfanSupplier 采购管理
     * @return 结果
     */
    @Override
    public int updateYoufanSupplier(YoufanSupplier youfanSupplier)
    {
        youfanSupplier.setUpdateTime(DateUtils.getNowDate());
        return youfanSupplierMapper.updateYoufanSupplier(youfanSupplier);
    }

    /**
     * 批量删除采购管理
     * 
     * @param ids 需要删除的采购管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanSupplierByIds(Long[] ids)
    {
        return youfanSupplierMapper.deleteYoufanSupplierByIds(ids);
    }

    /**
     * 删除采购管理信息
     * 
     * @param id 采购管理主键
     * @return 结果
     */
    @Override
    public int deleteYoufanSupplierById(Long id)
    {
        return youfanSupplierMapper.deleteYoufanSupplierById(id);
    }
}
