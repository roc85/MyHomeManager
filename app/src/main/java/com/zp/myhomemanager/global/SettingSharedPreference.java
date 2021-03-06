package com.zp.myhomemanager.global;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/11/14 14:54
 * Version : V1.0
 * Introductions : 用于存放配置参数
 */

public class SettingSharedPreference {
    public static String getDataString(Context c , String tag)
    {
        String res="";
        SharedPreferences pre=c.getSharedPreferences("setting", Context.MODE_PRIVATE);
        res = pre.getString(tag, "");
        return res;
    }

    public static void setDataString(Context c , String tag , String con)
    {
        SharedPreferences.Editor edi=
                c.getSharedPreferences("setting", Context.MODE_PRIVATE).edit();
        edi.putString(tag, con);
        edi.commit();
    }

    public static int getDataInt(Context c , String tag)
    {
        int res=0;
        SharedPreferences pre=c.getSharedPreferences("setting", Context.MODE_PRIVATE);
        res = pre.getInt(tag, 0);
        return res;
    }

    public static void setDataInt(Context c , String tag , int con)
    {
        SharedPreferences.Editor edi=
                c.getSharedPreferences("setting", Context.MODE_PRIVATE).edit();
        edi.putInt(tag, con);
        edi.commit();
    }

    public static boolean getDataBoolean(Context c , String tag, boolean defaultValue)
    {
        boolean res = false;
        SharedPreferences pre=c.getSharedPreferences("setting", Context.MODE_PRIVATE);
        res = pre.getBoolean(tag, defaultValue);
        return res;
    }

    public static void setDataBoolean(Context c , String tag , boolean bo)
    {
        SharedPreferences.Editor edi=
                c.getSharedPreferences("setting", Context.MODE_PRIVATE).edit();
        edi.putBoolean(tag, bo);
        edi.commit();
    }

}
