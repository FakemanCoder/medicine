package com.medicine.sys.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	//对当前时间添加年月日
	public static String addNowDate(int year, int month, int day) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		System.out.println("当前日期:"+sf.format(c.getTime()));
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		return sf.format(c.getTime());
	}
	//String类型转Date
	public static Date strToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
	}
	
	//添加时间
	public static String addDate(String dateStr, int year, int month, int day) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = strToDate(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		return sf.format(c.getTime());
	}
	
	public static void main(String[] args) throws ParseException {
		String addDate = addDate("2019-03-06", 0, 0, 1);
		System.out.println(addDate);
	}
}
