package com.zp.myhomemanager.ui.activities;

import android.os.Bundle;

import com.zp.myhomemanager.R;

public class MainWorkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentMode = 1;

        mContext = this;

        setContentView(R.layout.activity_main_work);
    }
}
