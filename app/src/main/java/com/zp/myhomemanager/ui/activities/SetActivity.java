package com.zp.myhomemanager.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.global.SettingSharedPreference;

import static com.zp.myhomemanager.global.GlobalParams.BAIDU_ZHIDAO_MODE;
import static com.zp.myhomemanager.global.GlobalParams.MODE;

public class SetActivity extends AppCompatActivity {

    private int mode;

    private ImageView imageViewExit;
    private TextView textViewSetFlag;
    private RelativeLayout setToolBox;
    private TextView textViewModeFlag;
    private RadioButton radioButtonHome;
    private RadioButton radioButtonWork;
    private RadioButton radioButtonLife;
    private RadioGroup modeChooseBox;
    private TextView textBaiduzhidao;
    private CheckBox checkBaiduzhidao;
    private RelativeLayout baiduzhidaoBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();

        initListener();
    }

    private void initListener() {

        radioButtonHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingSharedPreference.setDataInt(SetActivity.this, MODE, 0);
                }
            }
        });
        radioButtonWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingSharedPreference.setDataInt(SetActivity.this, MODE, 1);
                }
            }
        });
        radioButtonLife.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SettingSharedPreference.setDataInt(SetActivity.this, MODE, 2);
                }
            }
        });

        imageViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchMain();
            }
        });

        checkBaiduzhidao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingSharedPreference.setDataBoolean(SetActivity.this, BAIDU_ZHIDAO_MODE, isChecked);
            }
        });
    }

    private void initView() {
        imageViewExit = (ImageView) findViewById(R.id.imageViewExit);
        textViewSetFlag = (TextView) findViewById(R.id.textViewSetFlag);
        setToolBox = (RelativeLayout) findViewById(R.id.setToolBox);
        textViewModeFlag = (TextView) findViewById(R.id.textViewModeFlag);
        radioButtonHome = (RadioButton) findViewById(R.id.radioButtonHome);
        radioButtonWork = (RadioButton) findViewById(R.id.radioButtonWork);
        radioButtonLife = (RadioButton) findViewById(R.id.radioButtonLife);
        modeChooseBox = (RadioGroup) findViewById(R.id.modeChooseBox);
        textBaiduzhidao = (TextView) findViewById(R.id.textBaiduzhidao);
        checkBaiduzhidao = (CheckBox) findViewById(R.id.checkBaiduzhidao);
        baiduzhidaoBox = (RelativeLayout) findViewById(R.id.baiduzhidaoBox);

        mode = SettingSharedPreference.getDataInt(SetActivity.this, MODE);
        if (mode == 0) {
            radioButtonHome.setChecked(true);
        } else if (mode == 1) {
            radioButtonWork.setChecked(true);
        } else if (mode == 2) {
            radioButtonLife.setChecked(true);
        }

        checkBaiduzhidao.setChecked(SettingSharedPreference.getDataBoolean(SetActivity.this, BAIDU_ZHIDAO_MODE, true));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LaunchMain();

    }

    //启动相关页面
    private void LaunchMain() {
        mode = SettingSharedPreference.getDataInt(SetActivity.this, MODE);
        if (mode == 0) {
            //家庭模式
            startActivity(new Intent(this, MainActivity.class));
        } else if (mode == 1) {
            //办公室工作模式
            startActivity(new Intent(this, MainWorkActivity.class));
        } else if (mode == 2) {
            //日常模式
            startActivity(new Intent(this, MainLifeActivity.class));
        }

        finish();
    }
}
