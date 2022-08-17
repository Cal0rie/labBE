package com.youfan.web.controller.monitor;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youfan.common.annotation.Log;
import com.youfan.common.core.controller.BaseController;
import com.youfan.common.core.domain.AjaxResult;
import com.youfan.common.core.page.TableDataInfo;
import com.youfan.common.enums.BusinessType;
import com.youfan.system.domain.SysOperLog;
import com.youfan.system.service.ISysOperLogService;

/**
 * 操作日志记录
 * 
 * @author youfan
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController
{
    @Autowired
    private ISysOperLogService operLogService;




    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog)
    {
        if(operLog.getArgs1()!=null&&operLog.getArgs2()==null){
            operLog.setOperParam(operLog.getArgs1());
        }
        if(operLog.getArgs1()==null&&operLog.getArgs2()!=null){
            operLog.setOperParam(operLog.getArgs2());
        }
        if(operLog.getArgs1()!=null&&operLog.getArgs2()!=null){
            operLog.setOperParam(operLog.getArgs1()+operLog.getArgs2());
        }
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        for(SysOperLog sp:list){
           String jsonStr= sp.getOperParam();
           if("新增设备".equals(sp.getTitle())||"修改设备".equals(sp.getTitle())||"删除设备".equals(sp.getTitle())||"借用设备".equals(sp.getTitle())||"归还设备".equals(sp.getTitle())){

               System.out.println(sp.getTitle()+"的请求参数内容 ----"+jsonStr);
               JSONObject jsonObj = JSON.parseObject(jsonStr);

               String equipmentName= jsonObj.get("equipmentName").toString();
               String remark= jsonObj.get("remark").toString();//设备型号
               sp.setArgs1(equipmentName);
               sp.setArgs2(remark);
           }

        }
        return getDataTable(list);
    }



    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }
}
