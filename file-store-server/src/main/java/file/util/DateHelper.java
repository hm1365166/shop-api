package file.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式：yyyy年MM月dd日 HH:mm:ss
	 */
	public static final SimpleDateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	/**
	 * 格式：yyyyMMddHHmmss
	 */
	public static final SimpleDateFormat dateTimeFormat3 = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 格式：yyyy-MM-dd 00:00:00
	 */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 格式：yyyy-MM-dd 23:59:59
	 */
	public static final SimpleDateFormat maxDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	/**
	 * UK时间字符串格式
	 */
	public static final SimpleDateFormat dateFormatUK = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);

	/**
	 * 格式：HH:mm:ss
	 */
	public static final SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");

	public static final int _MIN = 60 * 1000;
	public static final String PATTERN_YYYY_MM = "yyyy-MM";
	public static final String PATTERN_DD = "dd";
	public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String PATTERN_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";
	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
	public static final String PATTERN_HH_MM = "HH:mm";

	public static String getPatternYyyyMmDd(long times) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(times);
		String patttern = String.valueOf(cal.get(Calendar.YEAR)+cal.get(Calendar.MONTH)+cal.get(Calendar.DATE));
		return patttern;
	}
	public static Timestamp getNow() {
		String time = dateTimeFormat.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	public static Timestamp NextDay(Timestamp date, int n) {
		return nextTime(date, n, Calendar.DATE);
	}

	public static Timestamp nextTime(Timestamp date, int n, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, n);
		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 *
	 * 获取指定时间n天前的时间--Timestamp
	 *
	 * @param date
	 * @param n
	 * @return
	 * @throws Exception
	 * @author zp
	 * @date 2015-11-11 10:43:21
	 */
	public static Timestamp LastDay(Timestamp date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, (0 - n));
		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 *
	 * 获取指定时间n天前的时间--Date
	 *
	 * @param date
	 * @param n
	 * @return
	 * @throws Exception
	 * @author zp
	 * @date 2015-11-11 10:45:25
	 */
	public static Date LastDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 0 - n);
		return cal.getTime();
	}

	/**
	 * 获取指定时间n个月前的时间--Date
	 *
	 * @param date
	 * @param n
	 * @return
	 * @author zp
	 * @date 2016-08-30 15:46:00
	 */
	public static Date LastMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0 - n);
		return cal.getTime();
	}

	/**
	 *
	 * 获取当天日期的timestamp
	 *
	 * @return
	 * @author zp
	 * @date 2015-11-11 10:44:46
	 */
	public static Timestamp getToday() {
		String time = dateFormat.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 *
	 * 获取当前时间的timestamp
	 *
	 * @return
	 * @author zp
	 * @date 2015-11-11 10:44:46
	 */
	public static Timestamp getCurrenTimestamp() {
		String time = dateTimeFormat.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 *
	 * 将java.util.Date 时间转成String类型 格式：yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 * @author zp
	 * @date 2015-11-23 10:32:02
	 */
	public static String getTimeStr(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 将 java.util.Date 转成 指定格式 字符串
	 *
	 * @param date
	 * @param formate
	 * @return
	 * @author zp
	 * @date 2016-06-07 15:03:46
	 */
	public static String getDateStr(Date date, String formate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		return dateFormat.format(date);
	}

	/**
	 * 将java.util.Date 时间转成String类型 的指定格式
	 *
	 * @param date
	 * @return
	 * @author liurh
	 * @date Jun 20, 2016 11:14:09 AM
	 */
	public static String format(Date date, String pattern) {
		try {
			if (null == pattern || "".equals(pattern)) {
				pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
			}
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将指定格式时间的String 转成java.util.Date
	 *
	 * @param date
	 * @return
	 * @author liurh
	 * @date Jun 20, 2016 11:14:09 AM
	 */
	public static Date parse(String str, String pattern) {
		try {
			if (null == pattern || "".equals(pattern)) {
				pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
			}
			return new SimpleDateFormat(pattern).parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * 获取指定时间的当天最大的时间，例如 "2015-12-15 12:00:00"-"2015-12-15 23:59:59"
	 *
	 * @param cld
	 * @return
	 * @author zp
	 * @date 2015-12-15 11:03:02
	 */
	public static Calendar getMaxTime(Calendar cld) {
		if (null == cld) {
			return cld;
		}
		int year = cld.get(Calendar.YEAR);
		int month = cld.get(Calendar.MONTH);
		int day = cld.get(Calendar.DATE);
		cld.set(year, month, day, 23, 59, 59);
		cld.set(Calendar.MILLISECOND, 0);
		return cld;
	}

	/**
	 *
	 * 获取指定时间的当天最大的时间，例如 "2015-12-15 12:00:00"-"2015-12-15 23:59:59"
	 *
	 * @param Timestamp
	 * @return
	 * @author zp
	 * @date 2015-12-15 11:03:02
	 */
	public static Timestamp getMaxTime(Timestamp timestamp) {
		if (null == timestamp) {
			return timestamp;
		}
		String time = maxDateFormat.format(timestamp);
		return Timestamp.valueOf(time);
	}

	/**
	 *
	 * 获取当天的最大的时间，例如 "2016-10-11 23:59:59"
	 * @return
	 * @author zp
	 * @date 2016-10-11 11:57:00
	 */
	public static Date getMaxCurrentTime() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		int year = cld.get(Calendar.YEAR);
		int month = cld.get(Calendar.MONTH);
		int day = cld.get(Calendar.DATE);
		cld.set(year, month, day, 23, 59, 59);
		return cld.getTime();
	}

	/**
	 * 根据传入的时间字符串生成对应的Timestamp对象
	 *
	 * @param dateString
	 * @return
	 * @throws Throwable
	 * @author heizhiqiang
	 * @date 2015-12-17 18:34:29
	 */
	public static Timestamp getTimestampByString(String dateString) throws Exception {
		return new Timestamp(dateTimeFormat.parse(dateString).getTime());
	}

	/**
	 *
	 * 根据传入的Timestamp对象生成对应的字符串（格式：yyyy-MM-dd HH:mm:ss）
	 *
	 * @param timestamp
	 * @return
	 * @throws Throwable
	 * @author zp
	 * @date 2016-01-06 14:00:51
	 */
	public static String getStringByTimestamp(Timestamp timestamp) throws Exception {
		return dateTimeFormat.format(timestamp);
	}

	/**
	 *
	 * 根据传入的Timestamp对象转成指定格式的字符串
	 * @param timestamp
	 * @param formate
	 * @return
	 * @throws Throwable
	 * @author yuanbs
	 * @date 2016-11-21 15:45:47
	 */
	public static String getStringByTimestamp(Timestamp timestamp, SimpleDateFormat formate) throws Exception {
		return formate.format(timestamp);
	}

	/**
	 *
	 * 根据传入的Timestamp对象生成对应的字符串（格式：yyyy-MM-dd）
	 *
	 * @param timestamp
	 * @return
	 * @throws Throwable
	 * @author zp
	 * @date 2016-04-25 13:45:10
	 */
	public static String getStringByTimestamp2(Timestamp timestamp) throws Exception {
		return dateFormat2.format(timestamp);
	}

	/**
	 *
	 * 将UK时间字符串转成Date
	 *
	 * @param str
	 *            UK时间字符串（例如：Wed Dec 16 21:25:04 CST 2015）
	 * @return Date
	 * @throws ParseException
	 * @author zp
	 * @date 2015-12-17 08:59:08
	 */
	public static Date getDateByUKStr(String str) throws ParseException {
		return dateFormatUK.parse(str);
	}

	/**
	 *
	 * 校验时分秒字符串是否格式正确
	 *
	 * @param timeStr
	 * @return
	 * @author zp
	 * @date 2016-03-16 15:16:10
	 */
	public static boolean isTimeStr(String timeStr) {
		boolean result = false;
		try {
			TimeFormat.parse(timeStr);
			result = true;
		} catch (ParseException e) {
		}
		return result;
	}

	/**
	 * 获取系统当前时间字符串(yyyy-MM-dd)
	 *
	 * @return
	 * @author zp
	 * @date 2016-06-01 09:00:01
	 */
	public static String getCurrentDateStr() {
		return dateFormat2.format(new Date());
	}

	/**
	 * 获取系统当前时间字符串(yyyy-MM-dd HH:mm:ss)
	 *
	 * @return
	 * @author zp
	 * @date 2016-06-01 09:00:31
	 */
	public static String getCurrentDateTimeStr() {
		return dateTimeFormat.format(new Date());
	}

	/**
	 *
	 * 获取系统当前时间字符串(yyyyMMddHHmmss)
	 * @return
	 * @author yuanbs
	 * @date 2016-12-05 17:55:37
	 */
	public static String getCurrentDateTimeStr1() {
		return dateTimeFormat3.format(new Date());
	}

	/**
	 * 给定的时候增加X分钟
	 *
	 * @param date
	 *            原始时间
	 * @param mins
	 *            增加的X分钟
	 * @return 增加的X分钟后的时间
	 * @author liurh
	 * @date Jun 17, 2016 10:24:47 AM
	 */
	public static Date addMins(Date date, int mins) {
		if (null == date || mins == 0) {
			return date;
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime() + mins * _MIN);
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * java.util.date 转换为 java.sql.date
	 *
	 * @param date
	 * @return
	 * @author liurh
	 * @date Jun 17, 2016 11:43:18 AM
	 */
	public static java.sql.Date date2sqldate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * java.sql.date 转换为 java.util.date
	 *
	 * @param date
	 * @return
	 * @author liurh
	 * @date Jun 17, 2016 5:40:10 PM
	 */
	public static Date sqldate2date(java.sql.Date date) {
		if (null == date) {
			return null;
		}
		try {
			return new Date(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前的自然年
	 *
	 * @return
	 * @author Liurh
	 * @date Jan 22, 2017 10:37:23 AM
	 */
	public static String getCurrentYearStr() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		int year = cld.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * 获取今年的第一天
	 *
	 * @return
	 * @author Liurh
	 * @date Jan 22, 2017 11:47:45 AM
	 */
	public static Timestamp getCurrentYearFirstDay() {
		String year = getCurrentYearStr();
		String time = year + "-01-01 00:00:00";
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 * 获取昨天的那个月的第一天
	 *
	 * @return
	 * @author Liurh
	 * @date Jan 22, 2017 11:47:45 AM
	 */
	public static Timestamp getFirstDayOfYesterdayMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		cld.add(Calendar.DATE, -1);
		int year = cld.get(Calendar.YEAR);
		int month = cld.get(Calendar.MONTH) + 1;
		String time = year + "-" + month + "-01 00:00:00";
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

/*
	public static Timestamp str2Timestamp(String str) {
		Timestamp rs = null;
		try {
			rs = Timestamp.valueOf(str);
		} catch (Exception e) {
			Date date = str2Date(str);
			if (date != null) {
				rs = new Timestamp(date.getTime());
			}
		}
		return rs;
	}
*/

}
