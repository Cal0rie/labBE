package com.youfan.bussiness.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.bussiness.domain.YoufanLockers;
import com.youfan.bussiness.service.IYoufanEquipmentService;
import com.youfan.bussiness.service.IYoufanLockersService;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.core.domain.model.LoginUser;
import com.youfan.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youfan.common.annotation.Log;
import com.youfan.common.core.controller.BaseController;
import com.youfan.common.core.domain.AjaxResult;
import com.youfan.common.enums.BusinessType;
import com.youfan.bussiness.domain.YoufanApplyRepair;
import com.youfan.bussiness.service.IYoufanApplyRepairService;
import com.youfan.common.utils.poi.ExcelUtil;
import com.youfan.common.core.page.TableDataInfo;

/**
 * 报修管理Controller
 * 
 * @author youfan
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/bussiness/repair")
public class YoufanApplyRepairController extends BaseController
{
    @Autowired
    private IYoufanApplyRepairService youfanApplyRepairService;

    @Autowired
    private IYoufanEquipmentService youfanEquipmentService;
    @Autowired
    private IYoufanLockersService youfanLockersService;

    /**
     * 查询报修管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(YoufanApplyRepair youfanApplyRepair)
    {
        startPage();
        List<YoufanApplyRepair> list = youfanApplyRepairService.selectYoufanApplyRepairList(youfanApplyRepair);
        return getDataTable(list);
    }

    /**
     * 设备列表
     */
    @Log(title = "设备列表", businessType = BusinessType.USE)
    @GetMapping("/getEquipmentlist/{id}")
    public AjaxResult getEquipmentlist(@PathVariable("id") Long id)
    {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        YoufanEquipment youfanEquipment=new YoufanEquipment();
        List<YoufanEquipment> list = youfanEquipmentService.selectYoufanEquipmentList(youfanEquipment);

        ajax.put("equipmentlist", list);
        return ajax;
    }


    /**
     * 设备列表
     */
    @GetMapping("/lockersList/{id}")
    public AjaxResult lockersList(@PathVariable("id") Long id)
    {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        YoufanLockers youfanLockers=new YoufanLockers();
        List<YoufanLockers> list = youfanLockersService.selectYoufanLockersList(youfanLockers);

        ajax.put("lockerslist", list);
        return ajax;
    }

    /**
     * 导出报修管理列表
     */
    @Log(title = "报修管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YoufanApplyRepair youfanApplyRepair)
    {
        List<YoufanApplyRepair> list = youfanApplyRepairService.selectYoufanApplyRepairList(youfanApplyRepair);
        ExcelUtil<YoufanApplyRepair> util = new ExcelUtil<YoufanApplyRepair>(YoufanApplyRepair.class);
        util.exportExcel(response, list, "报修管理数据");
    }

    /**
     * 获取报修管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(youfanApplyRepairService.selectYoufanApplyRepairById(id));
    }

    /**
     * 新增报修管理
     */
    @Log(title = "新增报修", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YoufanApplyRepair youfanApplyRepair)
    {
        youfanApplyRepair.setHandleResult("待维修");
        return toAjax(youfanApplyRepairService.insertYoufanApplyRepair(youfanApplyRepair));
    }

    /**
     * 修改报修管理
     */
    @Log(title = "报修处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YoufanApplyRepair youfanApplyRepair)
    {
        return toAjax(youfanApplyRepairService.updateYoufanApplyRepair(youfanApplyRepair));
    }

    /**
     * 删除报修管理
     */
    @Log(title = "报修管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(youfanApplyRepairService.deleteYoufanApplyRepairByIds(ids));
    }
}
