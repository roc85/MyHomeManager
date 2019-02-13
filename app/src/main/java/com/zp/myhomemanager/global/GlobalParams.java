package com.zp.myhomemanager.global;

public interface GlobalParams {

    public static final String[] WAKE_UP_WORDS = {"你好管家"};

    //Mob数据服务key
    public static final String MOB_APP_KEY = "28e65aa9bf136";


    //指令参数
    public static enum ORDER_TYPE {ERROR, TIME, DATE, TRAIN, WEATHER, EXIT, SWITCH_MODE};

    public static final String  ORDER_TODAY = "今天";

    public static final String[]  WEATHER_QUERY_ORDER = {"查询","天气"};

    public static final String  MOB_WEATHER_URL = "http://apicloud.mob.com/v1/weather/query?key="+MOB_APP_KEY+"&city=";

    public static final String[]  TRAIN_QUERY_ORDER = {"查询","的火车"};

    public static final String  MOB_TRAIN_URL = "http://apicloud.mob.com/train/tickets/queryByStationToStation?key="+
            MOB_APP_KEY+"&start=";

    public static final String MOB_TRAIN_URL2 = "&end=";

    public static final String NOW_TIME_ORDER = "现在几点";

    public static final String[] NOW_DATE_ORDER = {"今天几号","今天周几","今天星期几"};

    public static final String[]  ORDER_BYEBYE = {"再见","拜拜","休息"};

    public static final String[]  ORDER_SWITCH_MODE = {"切换家庭模式","切换办公室模式","切换日常模式"};

    //时间参数
    public static final int WAKE_UP_WAIT_TIME =2 * 60 * 1000; //屏幕亮起后无操作自动关屏时间

    //全局存储变量标识
    public static final String MODE = "mode"; // 工作模式

    public static final String BAIDU_ZHIDAO_MODE = "baidu_zhidao_mode"; // 是否启用百度知道进行搜索模式



}
