package cn.dlj1.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年6月2日
 */
public class DateUtils {

    /**
     * 1秒
     */
    public static final long SECOND = 1000;
    /**
     * 1分钟
     */
    public static final long MINUTE = SECOND * 60;
    /**
     * 1小时
     */
    public static final long HOUR = MINUTE * 60;
    /**
     * 1天
     */
    public static final long DAY = HOUR * 24;

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 将一个字符串类型时间 转换为 Date类型<br>
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDateTime(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间
     *
     * @param datetimeString
     * @return
     */
    public static Date getDateTime(String datetimeString) {
        return getDateTime(datetimeString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将Date类型转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDateString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取时间类型字符串
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        return getDateString(date, "yyyy-MM-dd");
    }

    /**
     * 获取 时间 日期类型 字符串<br>
     *
     * @param date
     * @return
     */
    public static String getDateTimeString(Date date) {
        return getDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 一个日期之上 加减几天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 左边的时间比右边的时间大 size 毫秒
     *
     * @param left
     * @param right
     * @param size
     * @return
     */
    public static boolean leftToRightBig(Date left, Date right, long size) {
        if (left.getTime() > (right.getTime() + size)) {
            return true;
        }
        return false;
    }
}
