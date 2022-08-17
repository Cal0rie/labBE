package com.youfan.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author youfan
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String EXCEL_YYYY_MM_DD = "MM/dd/YY";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "MM/dd/yy","MM/dd/yyyy","yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyy.·MM,dd","yyyy,MM,dd","yyyy.MM-dd","HH:mm:ss","hh:mm:ss AM","hh:mm:ss PM",
            "hh时mm分ss秒","HH时mm分ss"};

    private  static Calendar c = Calendar.getInstance();
    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final String parseDateToStr2(final String format, final Date date) {
        if(date == null){
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date parseStrToDate(final String format, final String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate2(Object str) throws ParseException{
        if (str == null) {
            return null;
        }
            Date d = parseDate(str.toString(), parsePatterns);
            if(DateUtils.parseStrToDate("yyyy-MM-dd","9999-01-01").before(d)){
                log.error("字符串：{}日期格式错误。",str);
             return DateUtils.parseStrToDate("yyyy-MM-dd","9999-01-01");
            }
            return d;

    }

    public static Date parseDateBase(Object str){
        if (str == null) {
            return null;
        }
        try {
            Date d = parseDate(str.toString(), parsePatterns);
            if (DateUtils.parseStrToDate("yyyy-MM-dd hh:MM:dd", "9999-12-31 23:23:59").before(d)) {
                log.error("字符串：{}日期格式错误。", str);
                return DateUtils.parseStrToDate("yyyy-MM-dd hh:MM:dd", "9999-12-31 23:23:59");
            }

        return d;
        }catch (Exception e){
            log.error("日期转换失败,{}",str,e);
            return DateUtils.parseStrToDate("yyyy-MM-dd hh:MM:dd", "9999-12-31 23:23:59");
        }

    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
             // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }
    public static Date parseDate(Object str, String format) {
        if (str == null) {
            return null;
        }
        try {

            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两个时间差 天数
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoorDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
       return diff / nd;
    }

    /**
     * 计算年龄
     * @param endDate
     * @param nowDate
     * @return
     */
    public static Long getAge(Date endDate, Date nowDate){
        try {

            long datePoorDay = DateUtils.getDatePoorDay(endDate, nowDate);
            return  (long) (datePoorDay / 365.2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期时间参数去掉时间只保留日期
     *
     * @param dateTime 日期时间参数
     * @return 结果
     */
    public static Date getDate(Date dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        String s = sdf.format(dateTime);
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 补充时间结尾
     *
     * @param Date 日期
     * @return 结果
     */
    public static String replenishEndTime(String Date) {
        return Date + " 23:59:59";
    }

    /**
     * 时间转换
     * @param str 时间
     * @return Date
     */
    public static Date parseDateErrorMsg(Object str) throws ParseException {
        if (str == null || "".equals(str)) {
            return null;
        }
        return parseDate(str.toString(), parsePatterns);
    }

    public static String parseDate(String format,Object dataValue) throws ParseException {
        if(null == dataValue){
            return null;
        }
        if(dataValue instanceof Long){
            c.setTimeInMillis((Long) dataValue);
            return new SimpleDateFormat(format).format(c.getTime());
        }
        return null;
    }
}
