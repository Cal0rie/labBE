package com.youfan.bussiness.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youfan.bussiness.domain.YoufanLockers;
import com.youfan.bussiness.service.IYoufanLockersService;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.framework.config.DateUtil;
import com.youfan.system.domain.SysOperLog;
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
import com.youfan.bussiness.domain.YoufanEquipment;
import com.youfan.bussiness.service.IYoufanEquipmentService;
import com.youfan.common.utils.poi.ExcelUtil;
import com.youfan.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 * 
 * @author 
 * @date 2022-07-23
 */
@RestController
@RequestMapping("/bussiness/equipment")
public class YoufanEquipmentController extends BaseController
{
    @Autowired
    private IYoufanEquipmentService youfanEquipmentService;

    @Autowired
    private IYoufanLockersService youfanLockersService;
       /**
     * 查询设备管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(YoufanEquipment youfanEquipment)
    {
        startPage();
        List<YoufanEquipment> list = youfanEquipmentService.selectYoufanEquipmentList(youfanEquipment);
        return getDataTable(list);
    }

    /**
     * 查询设备管理列表
     */
    @GetMapping("/Equipmentlist")
    public TableDataInfo Equipmentlist(YoufanEquipment youfanEquipment)
    {
        startPage();
        List<YoufanEquipment> list = youfanEquipmentService.selectUseEquipmentList();
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @Log(title = "导出设备记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YoufanEquipment youfanEquipment)
    {
        List<YoufanEquipment> list = youfanEquipmentService.selectYoufanEquipmentList(youfanEquipment);
        ExcelUtil<YoufanEquipment> util = new ExcelUtil<YoufanEquipment>(YoufanEquipment.class);
        util.exportExcel(response, list, "设备管理数据");
    }

    /**
     * 获取设备管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(youfanEquipmentService.selectYoufanEquipmentById(id));
    }

    /**
     * 新增设备管理
     */
    @Log(title = "新增设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YoufanEquipment youfanEquipment)
    {
        youfanEquipment.setEquipmentNum("1");
        youfanEquipment.setEquipmentDept(" ");
        youfanEquipment.setUseTimes(0);
        //储物柜中柜内设备剩余数量减去1
        YoufanLockers youfanLockers=new YoufanLockers();
        youfanLockers.setLockersCode(youfanEquipment.getLockersCode());
        List<YoufanLockers> list = youfanLockersService.selectYoufanLockersList(youfanLockers);
        if(list.size()>0){
            YoufanLockers yl=list.get(0);
           int total= Integer.parseInt(yl.getLockersSurplus());
           if(total>=1){
               total--;
           }
           yl.setLockersSurplus(total+"");
           youfanLockersService.updateYoufanLockers(yl);
        }
        return toAjax(youfanEquipmentService.insertYoufanEquipment(youfanEquipment));
    }

    /**
     * 修改设备管理
     */
    @Log(title = "修改设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YoufanEquipment youfanEquipment)
    {
        return toAjax(youfanEquipmentService.updateYoufanEquipment(youfanEquipment));
    }



    /**
     * 删除设备管理
     */
    @Log(title = "删除设备", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        //删除之前先校验状态是否被借用
        for(long id:ids){
           YoufanEquipment equipment=  youfanEquipmentService.selectYoufanEquipmentById(id);
           String status= equipment.getEquipmentStatus();
           if("1".equals(status)||"2".equals(status)){
              return error("设备状态不可删除，可能使用或者维护当中！");
           }
        }
        return toAjax(youfanEquipmentService.deleteYoufanEquipmentByIds(ids));
    }

    /**
     * 借用设备
     */
    @Log(title = "借用设备", businessType = BusinessType.USE)
    @GetMapping("/use/{id}")
    public AjaxResult use(@PathVariable("id") Long id)
    {

        SysUser user = SecurityUtils.getLoginUser().getUser();
        YoufanEquipment equipment= youfanEquipmentService.selectYoufanEquipmentById(id);
        equipment.setEquipmentDept(user.getUserName());
        equipment.setEquipmentStatus("1");

        int times=equipment.getUseTimes();
        times=times+1;
        equipment.setUseTimes(times);
        try{
            equipment.setUseTime(DateUtil.getDateFormat());
        }catch (Exception e){
            e.printStackTrace();
        }
        return toAjax(youfanEquipmentService.updateYoufanEquipment(equipment));
    }
    /**
     * 归还设备
     */
    @Log(title = "归还设备", businessType = BusinessType.USE)
    @GetMapping("/back/{id}")
    public AjaxResult back(@PathVariable("id") Long id)
    {

        SysUser user = SecurityUtils.getLoginUser().getUser();
        YoufanEquipment equipment= youfanEquipmentService.selectYoufanEquipmentById(id);
        equipment.setEquipmentDept(" ");
        equipment.setEquipmentStatus("0");
        return toAjax(youfanEquipmentService.updateYoufanEquipment(equipment));
    }


}
