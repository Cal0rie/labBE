package com.youfan.bussiness.controller;

import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.bussiness.domain.YoufanLockers;
import com.youfan.bussiness.service.IYoufanEquipmentService;
import com.youfan.bussiness.service.IYoufanLockersService;
import com.youfan.common.annotation.Log;
import com.youfan.common.core.controller.BaseController;
import com.youfan.common.core.domain.AjaxResult;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.core.page.TableDataInfo;
import com.youfan.common.enums.BusinessType;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备管理Controller
 * 
 * @author youfan
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/bussiness/lockers")
public class YoufanLockersController extends BaseController
{
    @Autowired
    private IYoufanLockersService youfanLockersService;

    /**
     * 查询设备管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(YoufanLockers youfanLockers)
    {
        startPage();
        List<YoufanLockers> list = youfanLockersService.selectYoufanLockersList(youfanLockers);
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @Log(title = "储物柜管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YoufanLockers youfanLockers)
    {
        List<YoufanLockers> list = youfanLockersService.selectYoufanLockersList(youfanLockers);
        ExcelUtil<YoufanLockers> util = new ExcelUtil<YoufanLockers>(YoufanLockers.class);
        util.exportExcel(response, list, "储物柜数据");
    }

    /**
     * 获取设备管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(youfanLockersService.selectYoufanLockersById(id));
    }

    /**
     * 新增设备管理
     */
    @Log(title = "储物柜管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YoufanLockers youfanLockers)
    {
        return toAjax(youfanLockersService.insertYoufanLockers(youfanLockers));
    }

    /**
     * 修改设备管理
     */
    @Log(title = "储物柜管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YoufanLockers youfanLockers)
    {
        return toAjax(youfanLockersService.updateYoufanLockers(youfanLockers));
    }




    /**
     * 删除设备管理
     */
    @Log(title = "储物柜管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(youfanLockersService.deleteYoufanLockersByIds(ids));
    }
}
