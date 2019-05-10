package com.ipanel.web.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/** 
 * @remark	时间转换
 * @author  fangg 
 * @createTime 2015-7-6 下午03:28:06 
 */
public class TimeUtil {
	
	public static Timestamp getCurrTime(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static String getTimeString(Timestamp time,String format) {
		format = format == null || "".equals(format) ? "yyyy-MM-dd HH:mm:ss" : format;
		SimpleDateFormat simpledate = new SimpleDateFormat(format);
		return simpledate.format(time);
	}
	
	public static String getTimeString(String time,String format){
		format = format == null || "".equals(format) ? "yyyy-MM-dd HH:mm:ss" : format;
		Timestamp t = Timestamp.valueOf(time);
		return getTimeString(t, format);
	}
	/**
	 * 时间转换
	 * @return
	 */
	public static Timestamp parseDate(String dateStr){
		Timestamp dateTime=new Timestamp(System.currentTimeMillis());
		if(StringUtils.isNotEmpty(dateStr)){
			 dateTime=Timestamp.valueOf(dateStr);
		}
		return dateTime;
	}
	
	/**
	 * 开始时间类型转换
	 * @author fangg
	 * @parameter 参数
	 * @return 返回值
	 */
	
	public static Timestamp parseStartDate(String dateStr){
		   String[] strArray = dateStr.split("/");  
		    if(strArray.length == 3){  
		    	//月，日，年
		    	int month=Integer.valueOf(strArray[0]);
		    	int day=Integer.valueOf(strArray[1]);
		    	int year=Integer.valueOf(strArray[2]);
		        Timestamp date=new Timestamp(year-1900,month-1,day,0,0,0,0);
		        return date;
		    }else{  
		        return new Timestamp(System.currentTimeMillis());  
		    }  
		 
	}
	/**	
	 * 结束时间转换
	 * @author fangg
	 * @parameter 参数
	 * @return 返回值
	 */

	public static Timestamp parseDate2(String dateStr){
		Timestamp dateTime=null;
		String[] dtStr=dateStr.split(" ");
		if(dtStr.length==2){
			String[] dateArray = dtStr[0].split("-"); //必须用/截取
			String[] timeArray  =dtStr[1].split(":");
		    if(dateArray.length == 3){  
		    	//月，日，年
		    	int month=Integer.valueOf(dateArray[0]);
		    	int year=Integer.valueOf(dateArray[1]);
		    	int day=Integer.valueOf(dateArray[2]);
		    	
		    	if(timeArray.length == 3){
				    	int hour=Integer.valueOf(timeArray[0]);
				    	int min=Integer.parseInt(timeArray[1]);
				    	int sec=Integer.parseInt(timeArray[2]);
				    	dateTime=new Timestamp(year-64,month,day,hour,min,sec,0);
				     //	dateTime=new Timestamp(year-1900,month-1,day,hour,min,sec,0);
			  	} 
		    }
		  
		}
		return dateTime; 
	}
	
	/**
	 * 获取距离当前day天之前日期
	 * @remark
	 * @author yezm
	 * @createTIme 2016-6-2 下午12:28:09
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getBeforeDate(int day,String format){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day * -1);
		format = format == null || "".equals("") ? "yyyy-MM-dd HH:mm:ss" : format;
		return  new SimpleDateFormat(format).format(c.getTime());
	}
	
	public static String getBeforeDate(int day,Object...format){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day * -1);
		String f = format == null || format.length == 0 ? "yyyy-MM-dd HH:mm:ss" : format[0].toString();
		return  new SimpleDateFormat(f).format(c.getTime());
	}
	
	 //获得当期时间
	public static String getCurrentTime(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today=sdf.format(date);
		return today;
	}
	//获得三天前的日期
	public static String getThreeDaysBefore(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,   -3);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String yesterday=sdf.format(calendar.getTime());
		return yesterday;
	}
	//获得昨天的日期
	public static String getYesterday(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,   -1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String yesterday=sdf.format(calendar.getTime());
		return yesterday;
	}
	//获得前天的日期
	public static String getTheDayBeforeYesterDay(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,   -2);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String theDayBeforeYesterDay=sdf.format(calendar.getTime());
		return theDayBeforeYesterDay;
	}
	//获得今天的日期
	public static String getToday(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(date);
		return today;
	}
	//获得三天后的日期时间
	public static String get3DaysAfter() {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,   3);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayTime=sdf.format(calendar.getTime());
		return dayTime;
	}
	//获得七天后的日期时间
	public static String get7DaysAfter() {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,   7);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayTime=sdf.format(calendar.getTime());
		return dayTime;
	}
	//获得n天前的日期时间
	public static String getDaysBefore(int num) {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,-num);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayTime=sdf.format(calendar.getTime());
		return dayTime;
	}
	//获得当前时间戳加六位随机数
	public static String getTimeStamp(){
		long time=System.currentTimeMillis();
		int randomStr=(int) (Math.random()*(999999-100000)+100000);
		String timeStamp=String.valueOf(time)+String.valueOf(randomStr);
		return timeStamp;
	}
	
	public static void main(String[] args) {
		/*Timestamp time=Timestamp.valueOf("2018-11-23 18:12:30");
		System.out.println(time);*/
		//Timestamp time=TimeUtil.parseDate("2016-01-03 18:12:30");
		//System.out.println(TimeUtil.getTimeString(time,null));
		//System.out.println(getBeforeDate(1));
//		System.out.println(getTimeString("2016-01-01 10:10:10", "yyyy-MM-dd 00:00:00"));
		String curTime = TimeUtil.getTimeString(TimeUtil.getCurrTime(), "yyyyMMddHHmmss");
		String xmlFileName = "fangg";
		if(xmlFileName.indexOf(".")!=-1){
			xmlFileName = xmlFileName.substring(0,xmlFileName.lastIndexOf("."))+"_"+curTime+".xml";
			System.out.println(xmlFileName);
		}else{
			xmlFileName = xmlFileName+curTime+".xml";	
		}
		
		System.out.println(xmlFileName);
	}
	
}
