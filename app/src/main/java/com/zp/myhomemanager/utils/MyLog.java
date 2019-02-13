package com.zp.myhomemanager.utils;

import android.util.Log;

public class MyLog {

    private static int LOG_LEVEL = 3 ;// log打印等级 0~5:数字越小，打印越多

    public static final String BASE_TAG = "我的管家 ";

    public static void i(String tag, String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.i(BASE_TAG+tag, info);

        }
    }

    public static void i(String tag, String info, int lv)
    {
        if(LOG_LEVEL < lv)
        {
            Log.i(BASE_TAG+tag, info);

        }
    }

    public static void i(String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.i(BASE_TAG, info);

        }
    }

    public static void e(String tag, String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.e(BASE_TAG+tag, info);

        }
    }

    public static void e(String tag, String info, int lv)
    {
        if(LOG_LEVEL < lv)
        {
            Log.e(BASE_TAG+tag, info);

        }
    }

    public static void e(String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.e(BASE_TAG, info);

        }
    }

    public static void w(String tag, String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.w(BASE_TAG+tag, info);

        }
    }

    public static void w(String tag, String info, int lv)
    {
        if(LOG_LEVEL < lv)
        {
            Log.w(BASE_TAG+tag, info);

        }
    }

    public static void w(String info)
    {
        if(LOG_LEVEL < 5)
        {
            Log.w(BASE_TAG, info);

        }
    }
}
