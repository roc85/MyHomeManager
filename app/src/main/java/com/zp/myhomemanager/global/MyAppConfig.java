package com.zp.myhomemanager.global;

public class MyAppConfig {

    public static MyAppConfig instance;

    public static synchronized MyAppConfig getInstance()
    {
        if(instance == null)
        {
            instance = new MyAppConfig();
        }
        return instance;
    }


}
