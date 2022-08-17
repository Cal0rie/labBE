package com.youfan.bussiness.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.youfan.bussiness.domain.YoufanPatrol;
import com.youfan.bussiness.service.IYoufanPatrolService;
import com.youfan.common.utils.poi.ExcelUtil;
import com.youfan.common.core.page.TableDataInfo;

/**
 * 巡检管理Controller
 * 
 * @author youfan
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/bussiness/patrol")
public class YoufanPatrolController extends BaseController
{
    @Autowired
    private IYoufanPatrolService youfanPatrolService;

    /**
     * 查询巡检管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(YoufanPatrol youfanPatrol)
    {
        startPage();
        List<YoufanPatrol> list = youfanPatrolService.selectYoufanPatrolList(youfanPatrol);
        return getDataTable(list);
    }

    /**
     * 导出巡检管理列表
     */
    @Log(title = "巡检管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YoufanPatrol youfanPatrol)
    {
        List<YoufanPatrol> list = youfanPatrolService.selectYoufanPatrolList(youfanPatrol);
        ExcelUtil<YoufanPatrol> util = new ExcelUtil<YoufanPatrol>(YoufanPatrol.class);
        util.exportExcel(response, list, "巡检管理数据");
    }

    /**
     * 获取巡检管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(youfanPatrolService.selectYoufanPatrolById(id));
    }

    /**
     * 新增巡检管理
     */
    @Log(title = "巡检管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YoufanPatrol youfanPatrol)
    {
        return toAjax(youfanPatrolService.insertYoufanPatrol(youfanPatrol));
    }

    /**
     * 修改巡检管理
     */
    @Log(title = "巡检管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YoufanPatrol youfanPatrol)
    {
        return toAjax(youfanPatrolService.updateYoufanPatrol(youfanPatrol));
    }

    /**
     * 删除巡检管理
     */
    @Log(title = "巡检管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(youfanPatrolService.deleteYoufanPatrolByIds(ids));
    }
}
