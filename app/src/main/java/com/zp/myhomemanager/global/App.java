package com.zp.myhomemanager.global;

import android.app.Application;

public class App extends Application {

    private String localCityName = "南京";

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public String getLocalCityName() {
        return localCityName;
    }

    public void setLocalCityName(String localCityName) {
        this.localCityName = localCityName;
    }
}
