package com.zp.myhomemanager.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.myhomemanager.R;

public class WeatherInfoView extends RelativeLayout {

    private TextView tvDate, tvInfo;
    private ImageView imgIcon;

    public WeatherInfoView(Context context) {
        super(context);
    }

    public WeatherInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.weather_info_layout, this, true);
        tvDate = (TextView) findViewById(R.id.info_date);
        imgIcon = (ImageView) findViewById(R.id.info_icon);
        tvInfo = (TextView) findViewById(R.id.info_text);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WeatherInfoView);
        if (attributes != null) {

            //日期信息
            int dateTextColor = attributes.getColor(R.styleable.WeatherInfoView_date_textcolor, Color.BLACK);
            tvDate.setTextColor(dateTextColor);

            float dateTextSize = attributes.getDimension(R.styleable.WeatherInfoView_date_textsize, 18);
            tvDate.setTextSize(dateTextSize);

            String dateText = attributes.getString(R.styleable.WeatherInfoView_date_text);
            if(!TextUtils.isEmpty(dateText))
            {
                tvDate.setText(dateText);
            }

            //图标
            int imgIconSrc = attributes.getResourceId(R.styleable.WeatherInfoView_icon_src, -1);
            if(imgIconSrc != -1)
            {
                imgIcon.setImageResource(imgIconSrc);
            }
            else
            {
                imgIcon.setVisibility(INVISIBLE);
            }

            //天气信息
            int infoTextColor = attributes.getColor(R.styleable.WeatherInfoView_info_textcolor, Color.BLACK);
            tvDate.setTextColor(dateTextColor);

            float infoTextSize = attributes.getDimension(R.styleable.WeatherInfoView_info_textsize, 18);
            tvInfo.setTextSize(infoTextSize);

            String infoText = attributes.getString(R.styleable.WeatherInfoView_info_text);
            if(!TextUtils.isEmpty(infoText))
            {
                tvInfo.setText(infoText);
            }

            attributes.recycle();
        }
    }

    public WeatherInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeatherInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
