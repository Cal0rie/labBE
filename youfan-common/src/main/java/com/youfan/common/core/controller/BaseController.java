package com.youfan.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youfan.common.constant.HttpStatus;
import com.youfan.common.core.domain.AjaxResult;
import com.youfan.common.core.domain.model.LoginUser;
import com.youfan.common.core.page.PageDomain;
import com.youfan.common.core.page.TableDataInfo;
import com.youfan.common.core.page.TableSupport;
import com.youfan.common.utils.DateUtils;
import com.youfan.common.utils.PageUtils;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.common.utils.StringUtils;
import com.youfan.common.utils.sql.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 *
 * @author youfan
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    protected void startPage2() {
        PageUtils.startPage2();
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    protected TableDataInfo getDataTable(List<?> list, long total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    // 遗留代码报错暂时恢复
    protected Map<String,Object> orderByMap(Map<String,Object> map, String tableAlis, String orderColumn, String isAsc, boolean isChinese){

        if(StringUtils.isEmpty(orderColumn)){
            return map;
        }
        orderColumn = StringUtils.toUnderScoreCase(orderColumn);
        if(StringUtils.isEmpty(isAsc)){
            isAsc="";
        }
        if(isChinese){
            map.put("order",("order by CONVERT(if("+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))
                    +orderColumn+" is null or "+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))
                    +orderColumn+" ='','左左左',trim("+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))+orderColumn+")) using gbk) "+isAsc ));
        }else{
            map.put("order",("order by "+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))+orderColumn+" "+isAsc));
        }
        map.put("isAsc",isAsc);
        return map;
    }

    protected Map<String,Object> orderByMap(Map<String,Object> map,String tableAlis){
        if(!(null != map && null != map.get("order") && !StringUtils.isEmpty(map.get("order").toString().trim())
                && null !=tableAlis)  ){
            return map;
        }else {

            if(map.get("order").toString().trim().equals("updateTime")){
                map.put("order",("order by "+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))+"update_time desc"));
            }else if(map.get("order").toString().trim().equals("exportTime")){
                map.put("order",("order by "+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))+"export_time desc"));
            }else if(map.get("order").toString().trim().equals("idName")){
                map.put("order",("order by CONVERT(if("+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))
                        +"id_name is null or "+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))
                        +"id_name ='','左左左',trim("+(StringUtils.isBlank(tableAlis)?"":(tableAlis+"."))+"id_name)) using gbk)"

                ));
            }
            return map;
        }
    }

    /**
     * 返回成功
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 获取登录用户id
     */
    public Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取登录部门id
     */
    public Long getDeptId() {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取登录用户名
     */
    public String getUsername() {
        return getLoginUser().getUsername();
    }

}
