package com.zp.myhomemanager.ui.activities;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.FaceSwitchBean;
import com.zp.myhomemanager.beans.TestStrBean;
import com.zp.myhomemanager.beans.TrainDataBean;
import com.zp.myhomemanager.beans.WeatherDataBean;
import com.zp.myhomemanager.beans.ZhidaoData;
import com.zp.myhomemanager.ui.fragments.BaseFragment;
import com.zp.myhomemanager.ui.fragments.TrainFragment;
import com.zp.myhomemanager.ui.fragments.WeatherFragment;
import com.zp.myhomemanager.ui.fragments.WebFragment;
import com.zp.myhomemanager.ui.views.HomeFaceView;
import com.zp.myhomemanager.utils.NetUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout infoFragmentBox;
    private RelativeLayout showInfosBox;
    private HomeFaceView homeFaceView;

    private BaseFragment[] fragments;
    private int fragmentIndex = 0;
    private WeatherFragment weatherFragment;
    private TrainFragment trainFragment;
    private WebFragment webFragment;

    private long faceAnimStartTime;
    private int faceAnimTime;

    public static final int WEATHER_ORDER = 1001;
    public static final int TRAIN_ORDER = 1002;
    public static final int ZHIDAO_SHOW_ORDER = 1003;

    public static final int HIDE_INFOS_BOX = 404;
    public static final int FACE_SWITCH = 405;

    private ImageButton imageButtonSet;
    private ImageButton imageButtonHelp;
    private LinearLayout settingBox;
    private Chronometer mainTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentMode = 0;

        mContext = this;

        setContentView(R.layout.activity_main);

        initView();

//        initEventBusBus();

    }

//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
//
//    private void initEventBus() {
//        EventBus.getDefault().register(this);
//    }

    @Subscribe
    public void onEvent(TestStrBean testStrBean) {
        ShowToast(testStrBean.getMsg());
    }

    @Subscribe
    public void onEvent(WeatherDataBean wdb) {
        Message msg = new Message();
        msg.what = WEATHER_ORDER;
        msg.obj = wdb;
        mHandler.sendMessage(msg);

    }

    @Subscribe
    public void onEvent(TrainDataBean tdb) {
        Message msg = new Message();
        msg.what = TRAIN_ORDER;
        msg.obj = tdb;
        mHandler.sendMessage(msg);

    }

    @Subscribe
    public void onEvent(ZhidaoData zdd) {
        Message msg = new Message();
        msg.what = ZHIDAO_SHOW_ORDER;
        msg.obj = zdd;
        mHandler.sendMessage(msg);

    }

    @Subscribe
    public void onEvent(FaceSwitchBean fsb) {
        Message msg = new Message();
        msg.what = FACE_SWITCH;
        msg.obj = fsb;
        mHandler.sendMessage(msg);

    }

    //初始化控件及各个Fragment
    private void initView() {
        infoFragmentBox = (FrameLayout) findViewById(R.id.infoFragmentBox);
        showInfosBox = (RelativeLayout) findViewById(R.id.showInfosBox);
        homeFaceView = (HomeFaceView) findViewById(R.id.homeFaceView);

        weatherFragment = new WeatherFragment();
        trainFragment = new TrainFragment();
        webFragment = new WebFragment();
        fragments = new BaseFragment[]{weatherFragment, trainFragment, webFragment};
        FragmentTransaction trx = getFragmentManager().beginTransaction();

        trx
                .add(R.id.infoFragmentBox, weatherFragment)
                .add(R.id.infoFragmentBox, trainFragment)
                .add(R.id.infoFragmentBox, webFragment)
                .show(weatherFragment)
                .hide(trainFragment)
                .hide(webFragment)
                .commit();

        showInfosBox.setVisibility(View.GONE);
        imageButtonSet = (ImageButton) findViewById(R.id.imageButtonSet);
        imageButtonSet.setOnClickListener(this);
        imageButtonHelp = (ImageButton) findViewById(R.id.imageButtonHelp);
        imageButtonHelp.setOnClickListener(this);
        settingBox = (LinearLayout) findViewById(R.id.settingBox);

        mainTimer = (Chronometer) findViewById(R.id.mainTimer);
        mainTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if(homeFaceView.getStatus() == HomeFaceView.FACE_ANIM.NORMAL)
                {
                    float rndValue = (float) Math.random();
                    if(rndValue<0.1)
                    {
                        homeFaceView.SetFaceAnim(HomeFaceView.FACE_ANIM.MOVE);
                        faceAnimStartTime = System.currentTimeMillis();
                        faceAnimTime = 7 * 1000;
                    }
                    else if(rndValue<0.2)
                    {
                        homeFaceView.SetFaceAnim(HomeFaceView.FACE_ANIM.UNHAPPY);
                        faceAnimStartTime = System.currentTimeMillis();
                        faceAnimTime = 3 * 1000;
                    }
                }
                else if(homeFaceView.getStatus() != HomeFaceView.FACE_ANIM.NORMAL &&
                        homeFaceView.getStatus() != HomeFaceView.FACE_ANIM.LOOK_RIGHT &&
                        homeFaceView.getStatus() != HomeFaceView.FACE_ANIM.LISTEN &&
                        System.currentTimeMillis() - faceAnimStartTime > faceAnimTime)
                {
                    homeFaceView.SetFaceAnim(HomeFaceView.FACE_ANIM.NORMAL);

                }
            }
        });

        mainTimer.start();
    }

    private void ShowFragment(BaseFragment bf) {

        FragmentTransaction trx = getFragmentManager().beginTransaction();
        trx.hide(fragments[fragmentIndex]);
        trx.show(bf).commit();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WEATHER_ORDER:
                    weatherFragment.SetDatas((WeatherDataBean) msg.obj);
                    ShowFragment(weatherFragment);
                    fragmentIndex = 0;
                    DisplayInfosBox(true);
                    mHandler.sendEmptyMessageDelayed(HIDE_INFOS_BOX, 60 * 1000);
                    break;

                case TRAIN_ORDER:
                    trainFragment.SetDatas((TrainDataBean) msg.obj);
                    ShowFragment(trainFragment);
                    fragmentIndex = 1;
                    DisplayInfosBox(true);
                    mHandler.sendEmptyMessageDelayed(HIDE_INFOS_BOX, 60 * 1000);
                    break;

                case HIDE_INFOS_BOX:
                    DisplayInfosBox(false);
                    break;

                case ZHIDAO_SHOW_ORDER:
                    webFragment.SetDatas((ZhidaoData) msg.obj);
                    ShowFragment(webFragment);
                    fragmentIndex = 2;
                    DisplayInfosBox(true);
                    mHandler.sendEmptyMessageDelayed(HIDE_INFOS_BOX, 2 * 60 * 1000);
                    break;

                case FACE_SWITCH:
                    if(homeFaceView.getStatus() == HomeFaceView.FACE_ANIM.LOOK_RIGHT)
                    {

                    }
                    else
                    {
                        homeFaceView.SetFaceAnim(((FaceSwitchBean)msg.obj).getFace_anim());
                    }


                    break;
            }
        }
    };

    private void DisplayInfosBox(boolean showIt) {
        if (showIt) {
            showInfosBox.setVisibility(View.VISIBLE);
            showInfosBox.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.pop_up_show));
            homeFaceView.SetFaceAnim(HomeFaceView.FACE_ANIM.LOOK_RIGHT);
            homeFaceView.updateCanvas();
        } else {
            showInfosBox.setVisibility(View.GONE);
            showInfosBox.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.pop_up_hide));
            homeFaceView.SetFaceAnim(HomeFaceView.FACE_ANIM.NORMAL);
            homeFaceView.updateCanvas();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonSet:
                startActivity(new Intent(MainActivity.this, SetActivity.class));
                finish();
                break;
            case R.id.imageButtonHelp:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
        }
    }

    private void GetHtml(final String keyword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> resList = new ArrayList<>();
                    String res =
                            NetUtil.getHTML("https://zhidao.baidu.com/search?ie=utf-8&word=" + keyword, "UTF-8");
                    int index = res.indexOf("<p>");
                    while (index >= 0) {
                        res = res.substring(index);
                        index = res.indexOf("</p>");
                        resList.add(res.substring(3, index));
                        res = res.substring(index + 5);

                        index = res.indexOf("<p>");
                    }
                    Log.e("", res);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetServiceRunning();
    }
}
