package com.kargo.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class DateUtil 
{

    private static final String yyyyMMdd = "yyyyMMdd";
    private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
  
    public static DateFormat getDateFormat(String formart)   
    {          
            return new SimpleDateFormat(formart);  
    } 

    public static String format(Date date,String format) throws ParseException {
        return getDateFormat(format).format(date);
    }

    public static Date parse(String strDate,String format)
    {
    	Date d = null;
        try {
			d = getDateFormat(format).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return d;
    }
    
    
    /**
     * Returns the Current Datetime in yyyyMMddHHmmss format.
     *
     * @return Date - current Date Time in yyyyMMddHHmmss format.
     * @throws RPSValidationException - if given date could not be converted
     *                 into yyyyMMddHHmmss format.
     */
    public static String getTransactionDateTimeAsCurrentTime() throws Exception {
    	try {
    	    Calendar cal = Calendar.getInstance(); // today
    	    return format(cal.getTime(),yyyyMMddHHmmss);
    	} catch (Exception e) {
    	    throw new Exception("GET TRANSACTION DATE TIME ERROR");
    	}
        }
    
    public static String getCurrentDate() throws Exception {
    	try {
    	    Calendar cal = Calendar.getInstance(); // today
    	    return format(cal.getTime(),yyyyMMdd);
    	} catch (Exception e) {
    	    throw new Exception("GET DATE STRING ERROR");
    	}
        }
    
    
    public static String toGMTDate(Date date)
    {
    	try 
    	{
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
		    format.setCalendar(cal);
		    return format.format(date);
		} 
    	catch(Exception e)
    	{
			
    	}
    	return "";
    }
    
    public static Long getLongGMTDate(Date date)
    {
    	String str = toGMTDate(date);
    	Date d = DateUtil.parse(str, "yyyy-MM-dd HH:mm:ss");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	return cal.getTimeInMillis();
    }
    
    public static Long getCurrentLongGMTDate()
    {
    	return getLongGMTDate(new Date());
    }
    
    public static void main(String[] args) throws Exception 
    {
    	long utc = DateUtil.getCurrentLongGMTDate();
    	System.out.println(utc);
    	long now = System.currentTimeMillis();
    	System.out.println((now-utc)/1000.0/3600.0);
    	
    	Calendar cal = Calendar.getInstance();
    	TimeZone timeZone = cal.getTimeZone();
    	System.out.println(System.currentTimeMillis()-timeZone.getOffset(System.currentTimeMillis()));
    	System.out.println(timeZone.getDisplayName());
    	
	}
}
