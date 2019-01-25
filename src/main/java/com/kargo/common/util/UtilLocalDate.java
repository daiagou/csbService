package com.kargo.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 线程安全的时间转换
 * @author abner.zhang
 *
 */
public class UtilLocalDate {
	
	public static final DateTimeFormatter yyyy_MM_dd_dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter yyyyMMdd_dateFormat = DateTimeFormat.forPattern("yyyyMMdd");
	public static final DateTimeFormatter yyyy_MM_dd_hh_mm_ss_dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter yyyy_MM_dd_hh_mm_dateFormat_chinese = DateTimeFormat.forPattern("yyyy年MM月dd日HH:mm");
	public static final DateTimeFormatter yyyy_MM_dd_hh_mm_dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	public static final DateTimeFormatter yyyyMMdd_hh_mm_ss_dateFormat = DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss");
	public static final DateTimeFormatter MM_dd_dateFormat = DateTimeFormat.forPattern("MM月dd日");
	public static final DateTimeFormatter M_d_HH_MM_dateFormat = DateTimeFormat.forPattern("M月d日HH:mm");
	public static final DateTimeFormatter yyyy_MM_ddThh_mm_ss_dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");//相当于LocalDateTime.now()的格式
	public static final DateTimeFormatter yyyyMMddhhmmss_dateFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
	
	
	/**
	 * 根据格式转换时间为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
	}
	
	/**
	 * 根据格式转换字符串为时间
	 * @param timeString
	 * @param format
	 * @return
	 */
	public static Date parse(String timeString,String format){
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
		DateTime dateTime = DateTime.parse(timeString, dateTimeFormatter);
		return dateTime.toDate();
	}
	
	/**
	 * 根据格式转换字符串为时间
	 * @param timeString
	 * @param format
	 * @return
	 */
	public static Date parse(String timeString,DateTimeFormatter format){
		DateTime dateTime = DateTime.parse(timeString, format);
		return dateTime.toDate();
	}

	public static Date parse(String time) throws ParseException {

		DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dateTime = DateTime.parse(time, df);
		return dateTime.toDate();
	}

	public static String format(Date date,DateTimeFormatter dateTimeFormatter){
	    DateTime dateTime = new DateTime(date);
	    return dateTime.toString(dateTimeFormatter);
	}
	
	
	public static Date parseDate(String time,DateTimeFormatter dateTimeFormat){
	    DateTime  dateTime = DateTime.parse(time,dateTimeFormat);
	    return dateTime.toDate();
	}
	
	/**
	 * 生成当前时间yyyyMMddHHmmssSSS字符串
	 * @return
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateTime dateTime = new DateTime(date);
		DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
		return dateTime.toString(df);
	}
	
	/**
	 * 生成唯一的订单号，产品code + yyyyMMddHHmmssSSS + 6位随机数
	 *
	 * @return
	 */
	public static String getOrderNo(String code,int randomLength) {
		if(code==null){
			code="";
		}
		return code + getOrderNum() + getRandomStr(randomLength);
	}

	/**
	 * 生成多少位的随机数
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length){
		return String.valueOf((int)((Math.random()*9+1)*Math.pow(10,length-1)));
	}

	

	public static void main(String[] args) {
		Date date = UtilLocalDate.parseDate("20160925", UtilLocalDate.yyyyMMdd_dateFormat);
		UtilLocalDate.format(date, UtilLocalDate.yyyyMMdd_hh_mm_ss_dateFormat);
	}

	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}
	
}
