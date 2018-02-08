package com.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 * Created by yangbin on 2017/6/12.
 */
public class DateUtils {
    public static final Timestamp ZERO_TIMESTAMP = new Timestamp(0);
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYY年MM月DD日HHMM = "yyyy年MM月dd日 HH:mm";

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 日期字符串转日期格式
     *
     * @author jiangmy
     * @date 2016-08-04 14:31:05
     * @since v1.0.0
     * @param str 日期字符串
     * @param pattern 日期字符串格式
     * @return java.util.Date格式
     */
    public static Date str2Date(String str, String pattern) {
        Date date = null;
        DateFormat format;
        try {
            if (str.length() < pattern.length()) {
                pattern = pattern.substring(0, str.length());
            } else if (str.length() > pattern.length()) {
                str = str.substring(0, pattern.length());
            }
            format = new SimpleDateFormat(pattern, Locale.CHINESE);
            date = format.parse(str);
        } catch (Exception e) {
            try {
                format = new SimpleDateFormat(pattern, Locale.ENGLISH);
                date = format.parse(str);
            } catch (Exception e2) {
            }
        }
        return date;
    }

    public static boolean vaildFormat(String str, String pattern) {
        DateFormat format;
        try {
            if (str.length() != pattern.length()) {
                return false;
            }

            format = new SimpleDateFormat(pattern, Locale.CHINESE);
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            logger.debug("字符：{}转换成时间格式：{}，失败", str, pattern);
            return false;
        }
        return true;
    }

    /**
     * 默认yyyy-MM-dd格式字符串转日期
     *
     * @author jiangmy
     * @date 2016-08-04 14:33:01
     * @since v1.0.0
     * @param str 字符串(格式yyyy-MM-dd)
     * @return java.util.Date格式
     */
    public static Date str2Date(String str) {
        return str2Date(str, FORMAT_DATE);
    }

    /**
     * 默认yyyy-MM-dd HH:mm:ss格式字符串转日期
     *
     * @author jiangmy
     * @date 2016-08-04 14:33:41
     * @since v1.0.0
     * @param str 字符串(格式yyyy-MM-dd HH:mm:ss)
     * @return java.util.Date格式
     */
    public static Date str2DateTime(String str) {
        return str2Date(str, FORMAT_DATETIME);
    }

    /**
     * 默认yyyy-MM-dd HH:mm:ss格式字符串转时间戳
     *
     * @author jiangmy
     * @date 2016-08-04 14:36:42
     * @since v1.0.0
     * @param str 字符串(格式yyyy-MM-dd HH:mm:ss)
     * @return java.sql.Timestamp格式
     */
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

    /**
     * java.util.Date格式 转 java.sql.Timestamp格式
     *
     * @author jiangmy
     * @date 2016-08-04 14:37:18
     * @since v1.0.0
     * @param date
     * @return java.sql.Timestamp格式
     */
    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 日期转字符串
     *
     * @author jiangmy
     * @date 2016-08-04 14:38:06
     * @since v1.0.0
     * @param date java.util.Date格式
     * @param pattern 目标字符串格式
     * @return String
     */
    public static String date2Str(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        String str = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            str = format.format(date);
            format = null;
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 日期时间转字符串
     *
     * @author jiangmy
     * @date 2016-08-04 14:43:57
     * @since v1.0.0
     * @param dt java.util.Date格式
     * @return String(yyyy-MM-dd HH:mm:ss)
     */
    public static String datetime2Str2(Date dt) {
        String tmstr = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", dt);
        return tmstr;
    }

    /**
     * 日期转字符串
     *
     * @author jiangmy
     * @date 2016-08-04 14:44:41
     * @since v1.0.0
     * @param date java.util.Date格式
     * @return String(yyyy-MM-dd)
     */
    public static String date2Str(Date date) {
        return date2Str(date, FORMAT_DATE);
    }

    public static String date2Str() {
        return date2Str(new Date());
    }

    /**
     * 日期时间转字符串
     *
     * @author jiangmy
     * @date 2016-08-04 14:45:08
     * @since v1.0.0
     * @param date java.util.Date格式
     * @return String(yyyy-MM-dd HH:mm:ss)
     */
    public static String datetime2Str(Date date) {
        return date2Str(date, FORMAT_DATETIME);
    }

    /**
     * 获取服务器当前系统时间
     *
     * @author jiangmy
     * @date 2016-08-04 14:45:08
     * @since v1.0.0
     * @return String(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDateTime() {
        return date2Str(new Date(), FORMAT_DATETIME);
    }

    /**
     * 获取服务器当前系统时间(格式FORMAT_YYYYMMDDHHMMSS)
     *
     * @author xiemh
     * @date 2016-08-04 14:45:08
     * @since v1.0.0
     * @return String(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDateTimeNum() {
        return date2Str(new Date(), FORMAT_YYYYMMDDHHMMSS);
    }

    public static String getCurrentDate() {
        return date2Str(new Date(), FORMAT_DATE);
    }

    /**
     * 获取当前年份
     *
     * @author buyi
     * @date 2016-12-28 16:28:26
     * @since v1.0.0
     * @return int(yyyy)
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 当前时间戳
     *
     * @author jiangmy
     * @date 2016-08-04 14:48:04
     * @since v1.0.0
     * @return java.sql.Timestamp (yyyy-MM-dd HH:mm:ss)
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 当前日期0点的时间戳
     *
     * @author jiangmy
     * @date 2016-08-04 14:50:06
     * @since v1.0.0
     * @return java.sql.Timestamp (yyyy-MM-dd 00:00:00)
     */
    public static Timestamp nowDay() {
        return toTimestamp(today());
    }

    /**
     * 当前日期0点的Date
     *
     * @author jiangmy
     * @date 2016-08-04 14:50:48
     * @since v1.0.0
     * @return java.util.Date (yyyy-MM-dd 00:00:00)
     */
    public static Date today() {
        return formatDate(new Date(), FORMAT_DATE);
    }

    /**
     * 格式化日期(去掉时间)
     *
     * @author jiangmy
     * @date 2016-08-04 14:51:15
     * @since v1.0.0
     * @param date
     * @return java.util.Date (yyyy-MM-dd 00:00:00)
     */
    public static Date formatDate(Date date) {
        return formatDate(date, FORMAT_DATE);
    }

    /**
     * 格式化时间戳(去掉时间)
     *
     * @author jiangmy
     * @date 2016-08-04 14:51:49
     * @since v1.0.0
     * @param date
     * @return java.sql.Timestamp (yyyy-MM-dd 00:00:00)
     */
    public static Timestamp formatDate(Timestamp date) {
        return toTimestamp(formatDate(date, FORMAT_DATE));
    }

    /**
     * 格式化日期
     *
     * @author jiangmy
     * @date 2016-08-04 14:57:02
     * @since v1.0.0
     * @param date 日期
     * @param pattern 格式
     * @return java.util.Date
     */
    public static Date formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(format.format(date));
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date add(Date date, TimeUnit unit, long amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MILLISECOND, (int) unit.toMillis(amount));
            return c.getTime();
        } catch (Exception e) {
        }
        return null;
    }

    public static Date add(Date date, int field, int amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(field, amount);
            return c.getTime();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 设置时分秒
     *
     * @author buyi
     * @date 2017-04-20 17:23:39
     * @since v1.0.0
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date set(Date date, int hour, int minute, int second) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, second);
            return c.getTime();
        } catch (Exception e) {
        }
        return null;
    }

    public static Date add(Date date, int year, int month, int day, int hour, int minute, int second) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, year);
            c.add(Calendar.MONTH, month);
            c.add(Calendar.DATE, day);
            c.add(Calendar.HOUR, hour);
            c.add(Calendar.MINUTE, minute);
            c.add(Calendar.SECOND, second);
            return c.getTime();
        } catch (Exception e) {
        }
        return null;
    }

    public static Timestamp add(Timestamp date, int field, int amount) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(field, amount);
            return new Timestamp(c.getTimeInMillis());
        } catch (Exception e) {
        }
        return null;
    }

    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    public static Date addWeek(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    public static Date addSecond(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 获取date所在月的第一天0点
     *
     * @author jiangmy
     * @date 2016-08-04 15:00:02
     * @since v1.0.0
     * @param date
     * @return
     */
    public static Date getMonthHead(Date date) {
        return formatDate(date, "yyyy-MM-01");
    }

    /**
     * 获取date所在月的最后一天23:59:59
     *
     * @author jiangmy
     * @date 2016-08-04 15:00:52
     * @since v1.0.0
     * @param date
     * @param pattern
     * @return
     */
    public static Date getMonthTail(Date date, String pattern) {
        return formatDate(addSecond(addMonth(getMonthHead(date), 1), -1), pattern);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date2Str(date1).equals(date2Str(date2));
    }

    /**
     * date1 - date2的时间差(舍弃小数部分,取整)
     *
     * @param date1
     * @param date2
     * @param unit
     * @return
     */
    public static long getTimeDiff(Date date1, Date date2, TimeUnit unit) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("date1:" + date1 + ", date2:" + date2 + " both cannot be null!!");
        }
        return unit.convert(date1.getTime() - date2.getTime(), TimeUnit.MILLISECONDS);
    }

    public static long getTimeDiff(Timestamp date1, Timestamp date2, TimeUnit unit) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("date1:" + date1 + ", date2:" + date2 + " both cannot be null!!");
        }
        return unit.convert(date1.getTime() - date2.getTime(), TimeUnit.MILLISECONDS);
    }

    public static Date addMinute(Date startTime, int i) {
        return add(startTime, Calendar.MINUTE, i);
    }

    /**
     * 时间戳毫秒转字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @author jiangmy
     * @date 2016-08-04 15:01:53
     * @since v1.0.0
     * @param t
     * @return
     */
    public static String milli2str(long t) {
        return DateUtils.datetime2Str(new Date(t));
    }

    /**
     * @author jiangmy
     * @date 2016-12-08 13:20:02
     * @param date
     * @return
     */
    public static Date lastSecondOfDay(Date date) {
        return DateUtils.add(DateUtils.formatDate(date), 0, 0, 1, 0, 0, -1);
    }

    public static Date lastSecondOfToday() {
        return DateUtils.add(DateUtils.formatDate(new Date()), 0, 0, 1, 0, 0, -1);
    }

    /**
     * 获取年份的最后一天日期
     */
    public static Date lastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * @author jiangmy
     * @date 2017-04-25 10:27:33
     * @since v1.0.0
     * @param date
     * @return
     */
    public static int getMonthDayCount(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 求指定日期距离今天的天数
     *
     * @author qinqz
     * @date 2017-05-09 19:09:37
     * @since v1.0.0
     * @param endDate
     * @return
     */
    public static long getDayLeft(Date endDate) {
        long timeLeft = 0L;
        try {
            Date now = new Date(System.currentTimeMillis());
            long lefttime = (endDate.getTime() - now.getTime()) / 1000;
            long day = lefttime / (24 * 60 * 60);
            return day;
        } catch (Exception e) {
        }
        return timeLeft;
    }

    /**
     * 求指定日期距离今天的剩余的时间(倒计时)
     *
     * @author qinqz
     * @date 2017-05-09 19:09:37
     * @since v1.0.0
     * @param endDate
     * @return
     */
    public static String getTimeLeft(Date endDate) {
        String timeLeft = "";
        try {
            Date now = new Date(System.currentTimeMillis());
            long lefttime = (endDate.getTime() - now.getTime()) / 1000;
            long day = lefttime / (24 * 60 * 60);
            long hour = lefttime / (60 * 60) - day * 24;
            long minute = lefttime / 60 - day * 24 * 60 - hour * 60;
            long second = lefttime - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60;
            timeLeft = (day + "天" + hour + "小时" + minute + "分" + second + "秒");
        } catch (Exception e) {
        }
        return timeLeft;
    }

    public static void main(String[] args) {
        System.out.println(lastSecondOfDay(new Date()));
        System.out.println(getCurrentDateTime());
        System.out.println(getMonthDayCount(new Date()));
        System.out.println(getMonthDayCount(DateUtils.str2Date("2017-02-03")));

    }
}
