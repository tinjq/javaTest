package com.tin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private final static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 日期转字符串	格式：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return sdf.get().format(date);
	}

	/**
	 * 获取当前时间，格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNow() {
		return formatDate(new Date());
	}
	
	/**
	 * 日期转字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 字符串转日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String date, String pattern) {
		return parseDate(date, pattern, true);
	}

	/**
	 * 解析字符串为日期
	 * @param date
	 * @param pattern
	 * @param lenient
	 * 				true:宽松解析，false:严格解析
	 * @return
	 */
	public static Date parseDate(String date, String pattern, boolean lenient) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(lenient);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		System.out.println(parseDate("2018-08-31 03:32:54", "yyyy-MM-dd HH:mm:ss", false));
	}

}
