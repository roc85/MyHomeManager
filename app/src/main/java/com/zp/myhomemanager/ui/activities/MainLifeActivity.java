package com.zp.myhomemanager.ui.activities;

import android.os.Bundle;

import com.zp.myhomemanager.R;

public class MainLifeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentMode = 2;

        mContext = this;

        setContentView(R.layout.activity_main_life);
    }
}
