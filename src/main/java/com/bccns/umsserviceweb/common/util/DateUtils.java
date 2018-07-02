package com.bccns.umsserviceweb.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
 
public abstract class DateUtils {
	
	/**
	 * 현재 날짜를 yyyyMMdd 포맷으로 반환.
	 * @return String 현재 날짜.
	 */
	public static String getSysDate() {
		return getSysDate("yyyyMMdd");
	}
	
	/**
	 * 날짜 입력양식을 전달받아 현재 날짜를 반환.
	 * @param pattern 입력 양식.
	 * @return String 날짜 입력 양식.
	 */
	public static String getSysDate(String pattern) {
		return getDateString(new Date(), pattern);
	}
	
	/**
	 * Date 객체와 입력양식을 전달받아 날짜를 반환.
	 * @param date Date 객체.
	 * @param pattern 입력 양식.
	 * @return String 날짜.
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 숫자형태의 시간과 포맷을 전달받아 날짜를 반환.
	 * @param time 숫자형태의 시간
	 * @param pattern 시간 포맷
	 * @return String 날짜
	 */
	public static String getDateString(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = new Date(time);
		return sdf.format(d);
	}
	
	public static String getDateString() {
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
        return formatter.format(new java.util.Date());
    }
    public static int getNumberByPattern(String pattern) {
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
        String dateString = formatter.format(new java.util.Date());
        return Integer.parseInt(dateString);
    }
    public static String getStringByPattern(String pattern) {
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
        return formatter.format(new java.util.Date());
    }
    public static String getCurrentTimeToString() {
        java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
        return formatter.format(new java.util.Date());
    }
}
