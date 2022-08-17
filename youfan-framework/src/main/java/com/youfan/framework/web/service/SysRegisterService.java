package com.youfan.framework.web.service;

import com.youfan.framework.config.DateUtil;
import com.youfan.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.youfan.common.constant.Constants;
import com.youfan.common.constant.UserConstants;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.core.domain.model.RegisterBody;
import com.youfan.common.core.redis.RedisCache;
import com.youfan.common.exception.user.CaptchaException;
import com.youfan.common.exception.user.CaptchaExpireException;
import com.youfan.common.utils.MessageUtils;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.common.utils.StringUtils;
import com.youfan.framework.manager.AsyncManager;
import com.youfan.framework.manager.factory.AsyncFactory;
import com.youfan.system.service.ISysConfigService;
import com.youfan.system.service.ISysUserService;

/**
 * 注册校验方法
 * 
 * @author youfan
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        String identity=registerBody.getIdentity();
        String phonenumber=registerBody.getPhonenumber();
        String college=registerBody.getCollege();
        String studentno=registerBody.getStudentno();

        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        if (captchaOnOff)
        {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(phonenumber))
        {
            msg = "手机号不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username)))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else if(StringUtils.isEmpty(identity)){
            msg = "身份不能为空";
        }
        else if(StringUtils.isEmpty(studentno)){
            msg = "学/工号不能为空";
        }
        else
        {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
            sysUser.setUserType("10");
            sysUser.setPhonenumber(phonenumber);
            sysUser.setCollege(college);
            sysUser.setIdentity(identity);
            /*if(identity.equals("学生")){
                sysUser.setStudentno("S"+ DateUtil.getUUIDint(8));
            }else{
                sysUser.setStudentno("T"+ DateUtil.getUUIDint(8));
            }*/
            sysUser.setStudentno(studentno);
            boolean regFlag = userService.registerUser(sysUser);



            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                //注册成功，同时关联默认权限
                Long usrid[]={sysUser.getUserId()};
                roleService.insertAuthUsers((long) 110,usrid);

                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
                        MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
