package com.neuqer.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chengkun
 * @since 2018/12/31 14:50
 */
public class DateUtil {

    /**
     * 获取时间戳
     * 输出结果:1438692801766
     */
    public static long getTimeStamp(Date date) {
        return date.getTime();
    }

    /**
     * 获取格式化的时间
     * 输出格式：2015-08-04 20:55:35
     */
    public static String getFormatDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取格式化的时间
     * 输出格式：2015-08-04 20:55:35
     *
     * @param date date
     * @return 格式化时间
     */
    public static String getFormatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取格式化的时间(精确到分钟)
     * 输出格式：2015-08-04 20:55:35
     *
     * @param date date
     * @return 格式化时间
     */
    public static String getFormatDateMinutes(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取格式化的时间(精确到分钟)
     * 输出格式：2015-08-04 20:55:35
     *
     * @param date date
     * @return 格式化时间
     */
    public static String getFormatDateDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将时间戳转化为标准时间
     * 输出：Tue Oct 07 12:04:36 CST 2014
     */
    public static void timestampToDate() {
        long times = 1412654676572L;
        Date date = new Date(times);
        System.out.println(date);
    }

    /**
     * 将时间戳转换为标注时间(精确到分钟)
     *
     * @param stamp 时间戳破
     * @return 格式化的时间
     */
    public static String stampToDateMinutes(long stamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(stamp);
        res = simpleDateFormat.format(date);
        return res;
    }
}
