package com.youfan.framework.config;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;



/**
 * @description 时间格式化工厂类
 * @author xuzhixiang
 * 
 */
public final class DateUtil {

	/**
	 * @description 时间格式化
	 * @return 时间
	 * @throws ParseException
	 */
	public static Date getDateFormat() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		Date date = sdf.parse(time);
		return date;
	}

	/**
	 * @description 时间格式化
	 * @return 时间
	 * @throws ParseException
	 */
	public static Date getDateFormatYYMMDD() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		Date date = sdf.parse(time);
		return date;
	}
	
	public static long getTimes(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * @description 时间格式化
	 * @param times
	 * @return 时间
	 * @throws ParseException
	 */
	public static Date getDateFormat(String times) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String time= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new
		// Date());
		Date date = sdf.parse(times);
		return date;
	}

	/**
	 * @description 时间格式化
	 * @param times
	 * @return 时间
	 * @throws ParseException
	 */
	public static Date getDateFormat(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String time= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new
		// Date());
		String datestr = sdf.format(date);
		//System.out.println("343434="+datestr);
		date=DateUtil.strToDate(datestr);
		return date;
	}
	
	
	
	public static String getDateFormatStr(){
		Date d=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(d); 
        return dateNowStr;
	}
	
	
	public static String getDateFormatStrYYMMDDHHmmSS(){
		Date d=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d); 
        return dateNowStr;
	}
	
	public static String getDateFormatYMD(){
		Date d=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String dateNowStr = sdf.format(d); 
        return dateNowStr;
	}
	/**
	 * @description 时间格式化
	 * @param times
	 * @return 时间
	 * @throws ParseException
	 */
	public static Date getDateFormatForSelectTime(String times)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(times);
		return date;
	}

	/**
	 * @description 时间格式化
	 * @param times
	 * @return 时间加5年
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static Date getDateFormat(int times) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		Date date = sdf.parse(time);
		int year = date.getYear();
		year = year + 5;
		date.setYear(year);
		return date;
	}

	public static Date getTime(String times) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String time= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new
		// Date());
		Date date = null;
		try {
			date = sdf.parse(times);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			
		}
		return date;
	}

	/**
	 * 
	 * @description 比较2个日期的大小(他们的日期格式可以不同)
	 * @param
	 * @return boolean
	 */
	public static boolean isBefore(Date date1, Date date2)
			throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String sDate1 = fmt.format(date1);
		String sDate2 = fmt.format(date2);
		try {
			Date dDate1 = fmt.parse(sDate1);
			Date dDate2 = fmt.parse(sDate2);
			return dDate1.before(dDate2);

		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return true;
	}

	
	 public static boolean compare_date(Date DATE1, Date DATE2) {
	         boolean flag=false;
	         DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	 		 String sDate1 = fmt.format(DATE1);
	 		 String sDate2 = fmt.format(DATE2);
	 		
	         try {
	        	 Date dDate1 = fmt.parse(sDate1);
				 Date dDate2 = fmt.parse(sDate2);
	            if (dDate1.getTime() > dDate2.getTime()) {
	                System.out.println("已经逾期");
	                flag=false;
	            } else if (dDate1.getTime() < dDate2.getTime()) {
	                System.out.println("还未逾期");
	                flag= true;
	            } else {
	            	flag= true;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	         return flag;
	    }
	
	 
	 
	 
	/**
	 * @description 将字符型转换为指定格式日期型
	 * 
	 * @param _date
	 *            需要转换成日期的字符串
	 * @param format
	 *            与需要转换成日期的字符串相匹配的格式
	 * @return
	 */
	public static Date stringToDate(String _date, String format)
			throws Exception {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(_date);
		} catch (ParseException e) {
			System.out.println("日期转换失败");
			
			
		}
		return date;
	}

	/**
	 * @description 将日期型转换为指定格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (null == date) {
			return "";
		}
		if ("".equalsIgnoreCase(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String timestampToStr(Timestamp timestamp) {
		return timestampToStr(timestamp, null);
	}

	public static String timestampToStr(Timestamp timestamp, String aMask) {
		String ret = null;
		String mask = aMask;
		if ((mask == null) || ("".equals(mask)))
			mask = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		ret = sdf.format(timestamp);
		return ret;
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, null);
	}

	public static String dateToStr(Date date, String aMask) {
		if (date == null) {
			return "";
		}
		String ret = null;
		String mask = aMask;
		if ((mask == null) || ("".equals(mask)))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		ret = sdf.format(date);
		return ret;
	}

	public static Date strToDate(String date) {
		if (("".equals(date)) || (date == null))
			return null;
		return strToDate(date, null);
	}

	public static Date strToDate(String date, String aMask) {
		Date ret = null;
		String mask = aMask;
		if ((mask == null) || ("".equals(mask)))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		try {
			ret = sdf.parse(date);
		} catch (ParseException localParseException) {
		}

		return ret;
	}

	public static Date strToDateLong(String strDate) {
		     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     ParsePosition pos = new ParsePosition(0);
		     Date strtodate = formatter.parse(strDate, pos);
		     return strtodate;
		 }
	
	public static Calendar getToday() {
		return new GregorianCalendar();
	}

	public static String getYear() {
		Calendar calendar = getToday();
		return String.valueOf(calendar.get(1));
	}

	public static String addDay(Date date, int day, String aMask) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(5, -1);
		String result = dateToStr(ca.getTime(), aMask);
		return result;
	}

	public static boolean equalsDate(Date atime, Date btime) {
		GregorianCalendar c1 = new GregorianCalendar();
		GregorianCalendar c2 = new GregorianCalendar();
		c1.setTime(atime);
		c2.setTime(btime);

		return c1.compareTo(c2) == 0;
	}

	public static boolean equalsDate(String atime, String btime) {
		return equalsDate(strToDate(atime), strToDate(btime));
	}

	public static Date getCurrentDateTime() {
		Calendar calNow = Calendar.getInstance();
		Date dtNow = calNow.getTime();

		return dtNow;
	}
	
	public static String getStringDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

	public static int diffTime(Date date1, Date date2) {
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(date1);
		GregorianCalendar c2 = new GregorianCalendar();
		c2.setTime(date2);
		double timeLong = c2.getTimeInMillis() - c1.getTimeInMillis();
		return (int) timeLong / 1000;
	}

	public static int diffDayTime(Date fromDate, Date toDate) {
		if (toDate == null) {
			return 0;
		}

		fromDate = strToDate(dateToStr(fromDate));
		toDate = strToDate(dateToStr(toDate));

		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(fromDate);
		GregorianCalendar c2 = new GregorianCalendar();
		c2.setTime(toDate);
		long timeLong = c2.getTimeInMillis() - c1.getTimeInMillis();

		return (int) (timeLong / 1000L / 86400L);
	}
    
	public static String calRepayment_time(int days){
		    Date d = new Date();  
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	        String date = format.format(d);  
	        System.out.println("现在的日期是：" + date);  
	         
	        Calendar ca = Calendar.getInstance();  
	        ca.add(Calendar.DATE, days);// days为增加的天数，可以改变的  
	        d = ca.getTime();  
	        String backTime = format.format(d)+" 23:59:59";  
	        System.out.println("增加天数以后的日期：" + backTime);
	        
	        
	        return backTime;
	}
	
	/**
	 * @desc 根据指定的天数作为参数得出某一天的日期
	 * @param days
	 * @return
	 */
	public static String calRepayment_time2(int days){
	    Date d = new Date();  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        //String date = format.format(d);  
        Calendar ca = Calendar.getInstance();  
        ca.add(Calendar.DATE, days);// days为增加的天数，可以改变的  
        d = ca.getTime();  
        String backTime = format.format(d);  
       
        
        
        return backTime;
}
	
	
	/**
	 * @desc 获取秒数时间戳
	 * @param date
	 * @return
	 */
	public static int getSecondTimestamp(Date date){  
	    if (null == date) {  
	        return 0;  
	    }  
	    String timestamp = String.valueOf(date.getTime());  
	    int length = timestamp.length();  
	    if (length > 3) {  
	        return Integer.valueOf(timestamp.substring(0,length-3));  
	    } else {  
	        return 0;  
	    }  
	}  
	
	public static String getUUIDint(int len){
		
		Random random = new Random();
	    String result="";
	    for(int i=0;i<len;i++){
	        //首字母不能为0
	        result += (random.nextInt(len+1)+1);
	    }
	    
	    return result;
	}
	
	public static void main(String[] args) {
		String tag="4345345,345345345,3453457,";
		int len=tag.lastIndexOf(",");
		System.out.println(tag.substring(0, len));
	}

}
