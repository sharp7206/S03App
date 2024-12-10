package com.app.s03.cmn.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author B07082 파일명 : DateTime.java 작성일 : 2008.02.01 오후 2:13:37
 * 
 * 주어진 변수에 맞는 날짜 형식을 반환해주는 메서드 모음 클래스<br>
 * 본 클래스의 메서드에서 쓰이는 날짜형식의 패턴에 관해서는 다음을 참조하세요 pattern 원하는 날짜 형식 패턴(아래 참조)
 * "yyyy-MM-dd".
 * 
 * <pre>
 *         Symbol   Meaning                  Example(KOREA Locale)   Example(US Locale)       
 *         ------   ---------------------    ----------------------  ------------------------ 
 *         G        era designator          	AD                       AD                     	
 *         y        year                    	02                       1996                   	
 *         M        month in year           	5                        July &amp; 07              	
 *         d        day in month            	3                        10                     	
 *         h        hour in am/pm (1&tilde;12)    	4                        12                     	
 *         H        hour in day (0&tilde;23)      	16                       0                      	
 *         m        minute in hour          	12                       30                     	
 *         s        second in minute        	58                       55                     	
 *         S        millisecond             	51                       978                    	
 *         E        day in week             	금                       Tuesday                	
 *         D        day in year             	123                      189                    	
 *         F        day of week in month    	1                        2 (2nd Wed in July)    	
 *         w        week in year            	18                       27                     	
 *         W        week in month           	1                        2                      	
 *         a        am/pm marker            	오후                     PM                     	
 *         k        hour in day (1&tilde;24)      	16                       24                     	
 *         K        hour in am/pm (0&tilde;11)    	4                        0                      	
 *         z        time zone               	KST                      Pacific Standard Time  	
 *         '        escape for text         	                                              	
 *         ''       single quote             '                     	 '
 *         
 *        example) 날짜 pattern에 따른 결과값 
 *          Format Pattern                         Result
 *          --------------                         -------
 *          &quot;yyyy.MM.dd G 'at' hh:mm:ss z&quot;    -&gt;&gt;  2002.05.03 AD at 04:15:54 KST
 *          &quot;EEE, MMM d, ''yy&quot;                -&gt;&gt;  금, 5월 3, '02
 *          &quot;h:mm a&quot;                          -&gt;&gt;  4:15 오후
 *          &quot;hh 'o''clock' a, zzzz&quot;           -&gt;&gt;  04 o'clock 오후, Korea Standard Time
 *          &quot;K:mm a, z&quot;                       -&gt;&gt;  4:15 오후, KST
 *          &quot;yyyyy.MMMMM.dd GGG hh:mm aaa&quot;    -&gt;&gt;  2002.5월.03 AD 04:15 오후
 * </pre>
 * 
 */

public class DateTime implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2473592401494634411L;

	public DateTime() {
	}

	/**
	 * 주어진 변수에 맞는 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @param pattern
	 *            원하는 날짜 형식 패턴
	 * @return String
	 * @see DateTime#getDateString
	 */

	public static String getDateString(String pattern) {
		return getDateString(pattern, java.util.Locale.KOREA);
	}

	/**
	 * 주어진 변수에 맞는 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @param pattern
	 *            원하는 날짜 형식 패턴
	 * @param locale
	 *            알아보려 하는 {@link java.util.Locale Locale}
	 * @return String
	 * @see DateTime#getDateString
	 */
	public static String getDateString(String pattern, java.util.Locale locale) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				pattern, locale);
		return formatter.format(new java.util.Date());

	}

	/**
	 * Timestamp를 반환해주는 메서드<br>
	 * 
	 * @return String 2002050305304886 : 연도(4자리) + 달(2) + 일(2) + 시간(2) + 분(2)+
	 *         초(2) + milisecond(3)
	 */

	public static String getTimeStamp() {
		return getDateString("yyyyMMddHHmmssSSS");
	}

	/**
	 * 일반적인 날짜 형식을 반환해주는 메서드<br>
	 * 
	 * @return String 2002-05-03 05:30:48 : 연도(4자리) + 달(2) + 일(2) + 시간(2) +
	 *         분(2)+ 초(2)
	 */

	public static String getGenDate() {
		return getDateString("yyyy-MM-dd HH:mm:ss");
	}

	public static String getString(java.sql.Timestamp ts, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(ts);
	}

	/**
	 * return month balance of two Dates for ordering.
	 * 
	 * @param fromDate
	 *            a String
	 * @param toDate
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int
	 */
	public static int monthsBetween(String fromDate, String toDate,
			String pattern) {
		return monthsBetween(getCalendar(fromDate, pattern), getCalendar(
				toDate, pattern));
	}

	/**
	 * return month balance of two Dates for ordering.
	 * 
	 * @param fromCalendar
	 * @param toCalendar
	 * @return int
	 */
	public static int monthsBetween(Calendar toCalendar, Calendar fromCalendar) {
		int balance = (fromCalendar.get(Calendar.YEAR) - toCalendar
				.get(Calendar.YEAR))
				* 12
				+ fromCalendar.get(Calendar.MONTH)
				- toCalendar.get(Calendar.MONTH);
		return balance;
	}

	/**
	 * return day balance of two Dates for ordering.
	 * 
	 * @param toDate
	 *            a String
	 * @param fromDate
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int
	 */
	public static int daysBetween(String toDate, String fromDate, String pattern) {
		try{
		java.util.Date date1 = getDate(fromDate, pattern);
		java.util.Date date2 = getDate(toDate, pattern);
		
		return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
		}catch(Exception e){
			return 0;
		}

	}

	/**
	 * Compares two Dates for ordering.
	 * 
	 * @param date1
	 *            a String
	 * @param date2
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return int the return value 0 if the argument Date is equal to this
	 *         Date; a value less than 0 if this Date is before the Date
	 *         argument; and a value greater than 0 if this Date is after the
	 *         Date argument
	 */
	public static int compare(String date1, String date2, String pattern) {
		return compare(date1, date2, pattern, java.util.Locale.KOREA);

	}

	/**
	 * Compares two Dates for ordering.
	 * 
	 * @param date1
	 *            a String
	 * @param date2
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param Locale
	 * @return int the return value 0 if the argument Date is equal to this
	 *         Date; a value less than 0 if this Date is before the Date
	 *         argument; and a value greater than 0 if this Date is after the
	 *         Date argument
	 */
	public static int compare(String date1, String date2, String pattern,
			Locale locale) {
		java.util.Date dt1 = getDate(date1, pattern, locale);
		java.util.Date dt2 = getDate(date2, pattern, locale);
		return dt1.compareTo(dt2);

	}

	/**
	 * Return a last day of Week as a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param day_of_week
	 *            int a day of week <br>
	 *            For example,
	 *            Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY,Calendar.SUNDAY
	 * @return java.lang.String
	 */
	public static String nextDay(String date, String pattern, int day_of_week) {
		return calendar2String(
				nextDay(getCalendar(date, pattern), day_of_week), pattern);
	}

	/**
	 * return a last day of Week as a Calendar class
	 * 
	 * @param calendar
	 *            Calendar class
	 * @param day_of_week
	 *            int a day of week <br>
	 *            For example,
	 *            Calendar.SUNDAY,Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,Calendar.FRIDAY,Calendar.SATURDAY,Calendar.SUNDAY
	 * @return java.util.Calendar
	 */
	public static Calendar nextDay(Calendar calendar, int day_of_week) {
		Calendar c = (Calendar) calendar.clone();
		calendar.set(Calendar.DAY_OF_WEEK, day_of_week);
		if (calendar.before(c)) {
			calendar.add(Calendar.DAY_OF_MONTH, 7);
		}

		return calendar;
	}

	/**
	 * return last day of Month as a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String lastDay(String date, String pattern) {
		return calendar2String(lastDay(getCalendar(date, pattern)), pattern);
	}

	/**
	 * return last day of Month as a Calendar class
	 * 
	 * @param calendar
	 *            Calendar class
	 * @return java.util.Calendar
	 */
	public static Calendar lastDay(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar;
	}

	/**
	 * return increase or decrease Day field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String addDays(String date, String pattern, int size)
			throws java.text.ParseException {
		return calendar2String(addDays(getCalendar(date, pattern), size),
				pattern);
	}

	/**
	 * return increase or decrease Day field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addDays(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.DAY_OF_MONTH, size);
	}

	/**
	 * return increase or decrease Month field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */

	public static String addMonths(String date, String pattern, int size)
			throws java.text.ParseException {
		return calendar2String(addMonths(getCalendar(date, pattern), size),
				pattern);
	}

	/**
	 * return increase or decrease Month field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addMonths(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.MONTH, size);
	}

	/**
	 * return increase or decrease Year field a String class
	 * 
	 * @param date
	 *            a String
	 * @param pattern
	 *            String representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */

	public static String addYears(String date, String pattern, int size)
			throws java.text.ParseException {
		return calendar2String(addYears(getCalendar(date, pattern), size),
				pattern);
	}

	/**
	 * return increase or decrease Year field a Calendar class
	 * 
	 * @param calendar
	 *            Calendar
	 * @param size
	 *            int
	 * @return java.util.Calendar
	 */
	public static Calendar addYears(Calendar calendar, int size) {
		return changeCalendar(calendar, Calendar.YEAR, size);
	}

	/**
	 * return increase or decrease sysdate as a String class
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example, "MONTH,
	 *            DAY_OF_MONTH".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String add(String pattern, int field, int size) {
		return changeString(getSysdate(pattern), pattern, field, size);
	}

	/**
	 * return sysdate as a specific pattern
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String getSysdate(String pattern) {
		return getSysdate(pattern, java.util.Locale.KOREA);
	}

	/**
	 * return sysdate as a specific pattern
	 * 
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param Locale
	 * @return java.lang.String
	 */
	public static String getSysdate(String pattern, Locale locale) {
		return calendar2String(Calendar.getInstance(locale), pattern);
	}

	/**
	 * return date as a String class
	 * 
	 * @param date
	 *            date string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example, "MONTH,
	 *            DAY_OF_MONTH".
	 * @param size
	 *            int
	 * @return java.lang.String
	 */
	public static String changeString(String date, String pattern, int field,
			int size) {
		return calendar2String(changeCalendar(getCalendar(date, pattern),
				field, size), pattern);
	}

	/**
	 * return date as a Calender class
	 * 
	 * @param date
	 *            java.util.Calender string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param field
	 *            string increase/decrease field. For example, "MONTH,
	 *            DAY_OF_MONTH".
	 * @return java.util.Calender
	 */
	public static Calendar changeCalendar(Calendar calendar, int field, int size) {
		calendar.add(field, size);
		return calendar;

	}

	/**
	 * check a valid date format or not
	 * 
	 * @param date
	 *            date string you want to check
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return boolean
	 */
	public static boolean isDate(String date, String pattern) {
		return isDate(date, pattern, java.util.Locale.KOREA);
	}

	/**
	 * check a valid date format or not
	 * 
	 * @param date
	 *            date string you want to check
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return boolean
	 */
	public static boolean isDate(String date, String pattern, Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		Calendar calendar = df.getCalendar();
		try {
			calendar.setTime(df.parse(date));
		} catch (java.text.ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * return Calender class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Calender
	 */
	public static Calendar getCalendar(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern,
				java.util.Locale.KOREA);
		Calendar calendar = df.getCalendar();
		try {
			calendar.setTime(df.parse(date));
		} catch (java.text.ParseException pe) {
		}
		return calendar;
	}

	/**
	 * return Date class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Date
	 */
	public static java.util.Date getDate(String date, String pattern) {

		return getDate(date, pattern, java.util.Locale.KOREA);
	}

	/**
	 * return Date class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @param locale
	 * @return java.util.Date
	 */

	public static java.util.Date getDate(String date, String pattern,
			Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		java.util.Date dt = null;
		try {
			dt = df.parse(date);
		} catch (java.text.ParseException pe) {
		}
		return dt;
	}

	/**
	 * return change Calendar class to String as specific format
	 * 
	 * @param calendar
	 *            Calendar class you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String calendar2String(Calendar calendar, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern,
				java.util.Locale.KOREA);
		return df.format(calendar.getTime());
	}

	/**
	 * return java.sql.Timestamp class as specific date as a specific format
	 * 
	 * @param date
	 *            string you want to change
	 * @param pattern
	 *            string representation of the date format. For example,
	 *            "yyyy-MM-dd".
	 * @return java.util.Date
	 */
	public static java.sql.Timestamp getTimestamp(String date, String pattern) {
		if (ConChar.isNull(date)) {
			return null;
		} else {
			return new Timestamp(getDate(date, pattern).getTime());
		}
	}

	/**
	 * String 타입의 날자를 받아 원하는 pattern으로 변형 시키는 메서드
	 * 
	 * @param date
	 *            string 바꾸려고 하는 날자 형식
	 * @param pattern
	 *            string input 날자 패턴. 예) "yyyy-MM-dd".
	 * @param pattern
	 *            string output 날자 패턴. 예) "yyyy-MM-dd".
	 * @return java.lang.String
	 */
	public static String changePatten(String date, String in_pattern,
			String out_pattern) {
		if (ConChar.isNull(date)) {
			return null;
		} else {
			return calendar2String(getCalendar(date, in_pattern), out_pattern);
		}
	}

	public static String getWhatDay() {
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		switch (day_of_week) {
		case 1:
			return "일";
		case 2:
			return "월";
		case 3:
			return "화";
		case 4:
			return "수";
		case 5:
			return "목"; 
		case 6:
			return "금";
		case 7:
			return "토";
		}
		return "";
	}

}
