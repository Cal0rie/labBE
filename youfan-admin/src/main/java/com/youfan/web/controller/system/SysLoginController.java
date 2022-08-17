package com.youfan.web.controller.system;

import java.util.List;
import java.util.Set;

import com.youfan.common.core.domain.model.LoginUser;
import com.youfan.system.domain.SysInitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.youfan.common.constant.Constants;
import com.youfan.common.core.domain.AjaxResult;
import com.youfan.common.core.domain.entity.SysMenu;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.core.domain.model.LoginBody;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.framework.web.service.SysLoginService;
import com.youfan.framework.web.service.SysPermissionService;
import com.youfan.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author youfan
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    @GetMapping("initDataSel")
    public AjaxResult initDataSel(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        List<SysInitData> initdatalist=  menuService.initDataSel();
        ajax.put("initdatalist", initdatalist);

       return ajax;
    }
}
