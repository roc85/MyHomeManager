package com.zp.myhomemanager.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDateUtil {
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String TALK_YYYY_MM_DD = "yyyy年MM月dd日";
    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";
    public static final String TALK_HH_MM_SS = "HH点mm分";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年
    /**
     * Log输出标识
     **/
    private static final String TAG = TimeDateUtil.class.getSimpleName();

    /**
     * * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚         *
     * * @param date
     * * @return
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
//            return r + "年前";
            return formatDateTime(date);
        }
        if (diff > month) {
            r = (diff / month);
//            return r + "个月前";
            return formatDateTime(date);
        }
        if (diff > day) {
            r = (diff / day);
//            return r + "天前";
            return formatDateTime(date);
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String formatTime(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_HH_MM);
        Date date = new Date(dateL);
        return sdf.format(date);
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化         *
     * * @param dateL         *            日期
     * * @return
     */

    public static String formatDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatDateTime(long datelong) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        return sdf.format(datelong);
    }

    public static String formatDateTime(long datelong,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(datelong);
    }

    public static String getNowDateTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");//设置日期格式
        return df.format(System.currentTimeMillis());
    }
    /**
     * 将日期字符串转成日期         *
     * * @param strDate         *            字符串日期
     * * @return java.util.date日期类型
     */
    public static Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date returnDate = null;

        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            Log.v(TAG, "parseDate failed !");
        }

        return returnDate;
    }

    public static Date parseDate(String strDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(DF_HH_MM);
        Date returnDate = null;

        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            Log.v(TAG, "parseDate failed !");
        }

        return returnDate;
    }

    /**
     * 获取系统当前日期         *          * @return
     */
    public static Date gainCurrentDate() {
        return new Date();
    }

    /**
     * 验证日期是否比当前日期早         *
     * * @param target1         *            比较时间1
     * * @param target2         *            比较时间2
     * * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = TimeDateUtil.formatDateTime(target1);
            String target2DateTime = TimeDateUtil.formatDateTime(target2);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e1) {
            System.out.println("比较失败，原因：" + e1.getMessage());
        }
        return flag;
    }

    /**
     * 对日期进行增加操作         *
     * * @param target         *            需要进行运算的日期
     * * @param hour         *            小时
     * * @return
     */
    public static Date addDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }
        return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 对日期进行相减操作         *
     * * @param target         *            需要进行运算的日期
     * * @param hour         *            小时
     * * @return
     */
    public static Date subDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }
        return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
    }

    public static String getWeek(long time) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(time));

        int year  = cd.get(Calendar.YEAR); //获取年份
        int month = cd.get(Calendar.MONTH); //获取月份
        int day   = cd.get(Calendar.DAY_OF_MONTH); //获取日期
        int week  = cd.get(Calendar.DAY_OF_WEEK); //获取星期

        String weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = "星期日";
                break;
            case Calendar.MONDAY:
                weekString = "星期一";
                break;
            case Calendar.TUESDAY:
                weekString = "星期二";
                break;
            case Calendar.WEDNESDAY:
                weekString = "星期三";
                break;
            case Calendar.THURSDAY:
                weekString = "星期四";
                break;
            case Calendar.FRIDAY:
                weekString = "星期五";
                break;
            default:
                weekString = "星期六";
                break;

        }

        return weekString;
    }
}
