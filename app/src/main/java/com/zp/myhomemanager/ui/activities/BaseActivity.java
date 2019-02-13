package com.zp.myhomemanager.ui.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.SwitchModeBean;
import com.zp.myhomemanager.global.SettingSharedPreference;
import com.zp.myhomemanager.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.zp.myhomemanager.global.GlobalParams.MODE;
import static com.zp.myhomemanager.global.GlobalParams.ORDER_SWITCH_MODE;

public class BaseActivity extends AppCompatActivity {

    protected int currentMode;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initEventBus();
    }

    private void initEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(final SwitchModeBean swb) {
        if (swb.getMode() == currentMode) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getResources().getString(R.string.hint))
                .setMessage("是否" + ORDER_SWITCH_MODE[swb.getMode()] + "?");
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SettingSharedPreference.setDataInt(mContext, MODE, swb.getMode());
                if (swb.getMode() == 1) {
                    //办公室工作模式
                    startActivity(new Intent(mContext, MainWorkActivity.class));
                } else if (swb.getMode() == 0) {
                    //家庭模式
                    startActivity(new Intent(mContext, MainActivity.class));
                } else if (swb.getMode() == 2) {
                    //日常模式
                    startActivity(new Intent(mContext, MainLifeActivity.class));
                }

                finish();
            }
        }).setNegativeButton(getResources().getString(R.string.esc), null).create().show();

    }

    //打印Toast
    protected void ShowToast(final String src) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, src, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * 判断服务是否启动,context上下文对象 ，className服务的name
     */
    public void SetServiceRunning() {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {

        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals("com.zp.myhomemanager.service.MyService") == true) {
                isRunning = true;
                break;
            }
        }

        if (!isRunning) {
            mContext.startService(new Intent(mContext, MyService.class));
        }
    }
}
