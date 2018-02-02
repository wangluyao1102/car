package com.car.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Android操作日期工具类
 * 
 * @author blue
 * 
 */
public class JDateKit
{

	/**
	 * 将Date转化成String
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(Date date)
	{
		return dateToStr("yyyy-MM-dd", date);
	}

	/**
	 * 将Date转化成String
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(String template, Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.CHINA);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 返回当月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDay()
	{
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		return gcLast.getTime();
	}

	/**
	 * 根据时间字符串得到日期
	 * 
	 * @author andrew
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByDateStr(String dateStr)
	{
		return getDateByDateStr("yyyy-MM-dd", dateStr);
	}

	/**
	 * 根据指定格式的时间字符串得到日期
	 * 
	 * @author andrew
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByDateStr(String template, String dateStr)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
		ParsePosition pos = new ParsePosition(0);
		return sdf.parse(dateStr, pos);
	}

	/**
	 * 根据时间字符串得到Calendar
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Calendar getCalendarByDateStr(String dateStr)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar calendar = Calendar.getInstance();
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = sdf.parse(dateStr, pos);
		calendar.setTime(strtodate);
		return calendar;
	}

	/**
	 * 得到选择的日期的近几天
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getLatelyDate(Date date, int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException
	{
		return daysBetween(smdate, bdate, "yyyy-MM-dd");
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate, String template) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = Math.abs((time2 - time1)) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
