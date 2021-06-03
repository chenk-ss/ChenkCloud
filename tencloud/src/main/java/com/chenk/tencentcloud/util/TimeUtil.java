package com.chenk.tencentcloud.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author chenke
 * @since 2021/3/22
 */
public class TimeUtil {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_DATETIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATETIME_SPECIAL = "yyyyMMddHHmmss";

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);

    private static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat(PATTERN_DATETIME);

    private static final SimpleDateFormat FORMAT_DATETIME_SECOND = new SimpleDateFormat(PATTERN_DATETIME_SECOND);

    private static final SimpleDateFormat FORMAT_DATETIME_SPECIAL = new SimpleDateFormat(PATTERN_DATETIME_SPECIAL);

    private static final DateTimeFormatter LOCALTIME_FORMAT_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE);

    private static final DateTimeFormatter LOCALTIME_FORMAT_DATETIME = DateTimeFormatter.ofPattern(PATTERN_DATETIME);

    private static final DateTimeFormatter LOCALTIME_FORMAT_SECOND = DateTimeFormatter.ofPattern(PATTERN_DATETIME_SECOND);

    private static final DateTimeFormatter LOCALTIME_FORMAT_SPECIAL = DateTimeFormatter.ofPattern(PATTERN_DATETIME_SPECIAL);

    /**
     * 获取时间戳(毫秒)
     */
    public static Long getNow() {
        return System.currentTimeMillis();
    }

    /**
     * 返回自定义String类型时间
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, PATTERN_DATETIME);
    }

    /**
     * 返回自定义String类型时间
     */
    public static String dateToStr(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        switch (pattern) {
            case PATTERN_DATETIME_SECOND:
                return FORMAT_DATETIME_SECOND.format(date);
            case PATTERN_DATETIME:
                return FORMAT_DATETIME.format(date);
            case PATTERN_DATETIME_SPECIAL:
                return FORMAT_DATETIME_SPECIAL.format(date);
            case PATTERN_DATE:
                return FORMAT_DATE.format(date);
            default:
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                format.setTimeZone(new SimpleTimeZone(0, "GMT"));
                return format.format(date);
        }
    }

    /**
     * 返回自定义String类型时间
     */
    public static String localTimeToStr(LocalDateTime date) {
        return localTimeToStr(date, PATTERN_DATETIME);
    }

    /**
     * 返回自定义String类型时间
     */
    public static String localTimeToStr(LocalDateTime date, String pattern) {
        if (null == date) {
            return "";
        }
        switch (pattern) {
            case PATTERN_DATETIME_SECOND:
                return LOCALTIME_FORMAT_SECOND.format(date);
            case PATTERN_DATETIME:
                return LOCALTIME_FORMAT_DATETIME.format(date);
            case PATTERN_DATETIME_SPECIAL:
                return LOCALTIME_FORMAT_SPECIAL.format(date);
            case PATTERN_DATE:
                return LOCALTIME_FORMAT_DATE.format(date);
            default:
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATETIME_SPECIAL);
                return dateTimeFormatter.format(date);
        }
    }

    /**
     * 返回自定义String类型时间
     */
    public static String secondToDate(Long time, String pattern) {
        if (null == time) {
            return "";
        }
        Date date = new Date(time);
        return dateToStr(date, pattern);
    }

    /**
     * 获取当天的零点时间戳
     *
     * @return 当天的零点时间戳
     */
    public static Long getTodayStartTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当天的零点时间戳
     *
     * @return 当天的零点时间戳
     */
    public static Long getTodayEndTime() {
        //设置时区
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

    /**
     * 时间格式转换
     *
     * @param dataStr 时间字符串
     * @return 时间对象
     */
    public static Date parseDate(String dataStr) {
        if (StringUtils.isBlank(dataStr)) {
            return null;
        }
        try {
            return DateUtils.parseDate(dataStr, PATTERN_DATETIME_SECOND, PATTERN_DATE, PATTERN_DATETIME, PATTERN_DATETIME_SPECIAL);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间格式转换
     *
     * @param dataStr 时间字符串
     * @return 时间对象
     */
    public static LocalDateTime parseLocalDate(String dataStr) {
        if (StringUtils.isBlank(dataStr)) {
            return null;
        }
        return LocalDateTime.parse(dataStr, LOCALTIME_FORMAT_SECOND);
    }

    /**
     * 计算两个时间的天数差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int countDayBetween(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return 0;
        }
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return Math.abs(days);
    }

}
