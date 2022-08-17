package com.youfan.framework.aspectj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.youfan.common.utils.spring.SpringUtils;
import com.youfan.system.domain.Equipment;
import com.youfan.system.service.ISysLogininforService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import com.alibaba.fastjson.JSON;
import com.youfan.common.annotation.Log;
import com.youfan.common.core.domain.model.LoginUser;
import com.youfan.common.enums.BusinessStatus;
import com.youfan.common.enums.HttpMethod;
import com.youfan.common.utils.ServletUtils;
import com.youfan.common.utils.StringUtils;
import com.youfan.common.utils.ip.IpUtils;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.framework.manager.AsyncManager;
import com.youfan.framework.manager.factory.AsyncFactory;
import com.youfan.system.domain.SysOperLog;

/**
 * 操作日志记录处理
 * 
 * @author youfan
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult)
    {
        try
        {

            // 获取当前的用户
            LoginUser loginUser = SecurityUtils.getLoginUser();

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());

            if (loginUser != null)
            {
                operLog.setOperName(loginUser.getUsername());

                operLog.setOperUserStuno(loginUser.getUser().getStudentno());

            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception
    {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
            String paramStr= operLog.getOperParam();
            if(paramStr!=null){
                if("删除设备".equals(operLog.getTitle())){
                    int begin=paramStr.indexOf("=");
                    int end=paramStr.indexOf("}");
                    String params= paramStr.substring(begin+1,end);
                    System.out.println("parms======="+params);
                    if(params.indexOf(",")>0){
                        List<String> namelist=new ArrayList();
                        List<String> typelist=new ArrayList();
                        String namejson="";
                        String typejson="";
                        String ids[]=  params.split(",");
                        for(String idstr:ids){

                            // 插入数据
                            Equipment equipment= SpringUtils.getBean(ISysLogininforService.class).selectYoufanEquipmentById(Long.parseLong(idstr));
                            String eqname=  equipment.getEquipmentName();
                            String typename=equipment.getRemark();
                            if(!"".equals(namejson)){
                                namejson=namejson+"-"+eqname;
                            }else{
                                namejson=eqname;
                            }

                            if(!"".equals(typejson)){
                                typejson=typejson+"-"+typename;
                            }else{
                                typejson=typename;
                            }
                        }
                       // System.out.println("{\"phone\":\"" + number +"\"}");
                        params="{\"equipmentName\":\""+namejson+"\","+"\"remark\":\""+typejson+"\","+"\"ids\":\""+paramStr.substring(begin+1,end)+"\"}";
                        System.out.println("批量删除===="+params);
                    }else{
                        String equmentid=params;
                        Equipment equipment= SpringUtils.getBean(ISysLogininforService.class).selectYoufanEquipmentById(Long.parseLong(equmentid));
                        params="{\"equipmentName\":\""+equipment.getEquipmentName()+"\","+"\"remark\":\""+equipment.getRemark()+"\","+"\"ids\":\""+params+"\"}";
                        System.out.println("单个删除===="+params);
                    }

                    operLog.setOperParam(params);
                }
                if("借用设备".equals(operLog.getTitle())||"归还设备".equals(operLog.getTitle())){
                    int begin=paramStr.indexOf("=");
                    int end=paramStr.indexOf("}");
                    String params= paramStr.substring(begin+1,end);
                    System.out.println("parms======="+params);

                    String equmentid=params;
                    Equipment equipment= SpringUtils.getBean(ISysLogininforService.class).selectYoufanEquipmentById(Long.parseLong(equmentid));
                    params="{\"equipmentName\":\""+equipment.getEquipmentName()+"\","+"\"remark\":\""+equipment.getRemark()+"\","+"\"ids\":\""+params+"\"}";
                    System.out.println("借用或归还请求参数封装===="+params);

                    operLog.setOperParam(params);
                }
            }
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult))
        {
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     * 
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception
    {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                {
                    try
                    {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     * 
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

    public static void main(String[] args) {
        List<String> list=new ArrayList();
        list.add("abc");
        list.add("jjb");
        list.add("djg");
        list.add("glk");
        String str = JSONObject.toJSONString(list);
        System.out.println(str.replace("[","").replace("]",""));
    }
}
