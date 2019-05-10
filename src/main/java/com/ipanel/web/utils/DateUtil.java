package com.ipanel.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class DateUtil {
	public static final FastDateFormat DATE_FORMAT_FOR_SIMPLE=FastDateFormat.getInstance("yyyy-MM-dd");
	public static final FastDateFormat DATE_FORMAT_FOR_DETAIL=FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	public static final FastDateFormat DATE_FORMAT_FOR_TIME=FastDateFormat.getInstance("HH:mm:ss");
	
	public static String formatTimeForSimple(Date date){
		return date==null?null:DATE_FORMAT_FOR_SIMPLE.format(date);
	}
	public static String formatTimeForDetail(Date date){
		return date==null?null:DATE_FORMAT_FOR_DETAIL.format(date);
	}
	public static String formatTimeForTime(Date date){
		return date==null?null:DATE_FORMAT_FOR_TIME.format(date);
	}
	
	public static String[] getFormatDate(String begintime,String duration) throws ParseException {
		String[] str=new String[2];
		SimpleDateFormat dfBegintime=new SimpleDateFormat("yyyyMMddHHmmss");
		Date dateBegintime=dfBegintime.parse(begintime);
		str[0]=formatTimeForDetail(dateBegintime);
		SimpleDateFormat dfDuration=new SimpleDateFormat("HHmmss");
		Date dateDuration=dfDuration.parse(duration);
		long durationSM=dateDuration.getHours()*3600*1000+dateDuration.getMinutes()*60*1000+dateDuration.getSeconds()*1000;
		long endtimeSM=dateBegintime.getTime()+durationSM;
		Date dateEndtime=new Date(endtimeSM);
		str[1]=formatTimeForDetail(dateEndtime);
		return str;
		
	}
	
	public static String getTodayStartTime(){
		return DATE_FORMAT_FOR_SIMPLE.format(new Date())+" 00:00:00";
	}
	
	public static String getTodayEndTime(){
		return DATE_FORMAT_FOR_SIMPLE.format(new Date())+" 23:59:59";
	}
	
	public static int compareTime(String currentTime,String compareTime) throws ParseException{
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(sdf.parse(currentTime));
		long currentMS=calendar.getTimeInMillis();
		calendar.setTime(sdf.parse(compareTime));
		long compareMS=calendar.getTimeInMillis();
		long day=(currentMS-compareMS)/(1000*3600*24);
		return Integer.parseInt(String.valueOf(day));
	}
	
}
