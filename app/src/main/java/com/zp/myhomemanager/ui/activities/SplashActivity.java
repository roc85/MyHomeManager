package com.zp.myhomemanager.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.global.SettingSharedPreference;

import java.util.ArrayList;

import static com.zp.myhomemanager.global.GlobalParams.MODE;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initPermission();

    }


    //region 6.0以上权限申请相关

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
        else
        {
            LaunchMain();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        LaunchMain();

    }
    //endregion

    //启动相关service



    //启动相关页面
    private void LaunchMain()
    {
        SetServiceRunning();

        int mode = SettingSharedPreference.getDataInt(SplashActivity.this, MODE);
        if(mode == 0)
        {
            //家庭模式
            startActivity(new Intent(this,MainActivity.class));
        }
        else if(mode == 1)
        {
            //办公室工作模式
            startActivity(new Intent(this,MainWorkActivity.class));
        }
        else if(mode == 2)
        {
            //日常模式
            startActivity(new Intent(this,MainLifeActivity.class));
        }

        finish();
    }
}
