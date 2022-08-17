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
import com.youfan.bussiness.domain.YoufanSupplier;
import com.youfan.bussiness.service.IYoufanSupplierService;
import com.youfan.common.utils.poi.ExcelUtil;
import com.youfan.common.core.page.TableDataInfo;

/**
 * 采购管理Controller
 * 
 * @author youfan
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/bussiness/supplier")
public class YoufanSupplierController extends BaseController
{
    @Autowired
    private IYoufanSupplierService youfanSupplierService;

    /**
     * 查询采购管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(YoufanSupplier youfanSupplier)
    {
        startPage();
        List<YoufanSupplier> list = youfanSupplierService.selectYoufanSupplierList(youfanSupplier);
        return getDataTable(list);
    }

    /**
     * 导出采购管理列表
     */
    @Log(title = "采购管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YoufanSupplier youfanSupplier)
    {
        List<YoufanSupplier> list = youfanSupplierService.selectYoufanSupplierList(youfanSupplier);
        ExcelUtil<YoufanSupplier> util = new ExcelUtil<YoufanSupplier>(YoufanSupplier.class);
        util.exportExcel(response, list, "采购管理数据");
    }

    /**
     * 获取采购管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(youfanSupplierService.selectYoufanSupplierById(id));
    }

    /**
     * 新增采购管理
     */
    @Log(title = "采购管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YoufanSupplier youfanSupplier)
    {
        return toAjax(youfanSupplierService.insertYoufanSupplier(youfanSupplier));
    }

    /**
     * 修改采购管理
     */
    @Log(title = "采购管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YoufanSupplier youfanSupplier)
    {
        return toAjax(youfanSupplierService.updateYoufanSupplier(youfanSupplier));
    }

    /**
     * 删除采购管理
     */
    @Log(title = "采购管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(youfanSupplierService.deleteYoufanSupplierByIds(ids));
    }
}
