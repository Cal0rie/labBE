package com.youfan.bussiness.service.impl;

import com.youfan.bussiness.domain.YoufanLockers;
import com.youfan.bussiness.mapper.YoufanLockersMapper;
import com.youfan.bussiness.service.IYoufanLockersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备管理Service业务层处理
 *
 * @author
 * @date 2022-07-23s
 */
@Service
public class YoufanLockersServiceImpl implements IYoufanLockersService
{
    @Autowired(required = false)
    private YoufanLockersMapper youfanLockersMapper;

    @Override
    public YoufanLockers selectYoufanLockersById(Long id) {
        return youfanLockersMapper.selectYoufanLockersById(id);
    }

    @Override
    public List<YoufanLockers> selectYoufanLockersList(YoufanLockers lockers) {
        return youfanLockersMapper.selectYoufanLockersList(lockers);
    }

    @Override
    public int insertYoufanLockers(YoufanLockers lockers) {
        return youfanLockersMapper.insertYoufanLockers(lockers);
    }

    @Override
    public int updateYoufanLockers(YoufanLockers lockers) {
        return youfanLockersMapper.updateYoufanLockers(lockers);
    }

    @Override
    public int deleteYoufanLockersById(Long id) {
        return youfanLockersMapper.deleteYoufanLockersById(id);
    }

    @Override
    public int deleteYoufanLockersByIds(Long[] ids) {
        return youfanLockersMapper.deleteYoufanLockersByIds(ids);
    }
}
