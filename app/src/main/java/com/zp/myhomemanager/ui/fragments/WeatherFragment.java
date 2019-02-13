package com.zp.myhomemanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.WeatherDataBean;
import com.zp.myhomemanager.ui.adapters.WeatherInfoAdapter;
import com.zp.myhomemanager.utils.MyLog;

public class WeatherFragment extends BaseFragment {


    private TextView textViewTitle;
    private GridView weatherGridBox;
    private WeatherInfoAdapter weatherInfoAdapter;
    private WeatherDataBean myBean;
    private Context context;
    private String cityName;
    private TextView textViewNowW;
    private TextView textViewNowT;
    private TextView textViewE;
    private TextView textViewI;
    private RelativeLayout nowWeatherBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            View view = inflater.inflate(R.layout.fragment_weather, container, false);
            initView(view, savedInstanceState);
            return view;
        } catch (Exception e) {
            MyLog.e(e.toString());
        }

        return null;
    }

    private void initView(View view, Bundle savedInstanceState) {

        textViewTitle = (TextView) view.findViewById(R.id.textViewWeatherTitle);
        weatherGridBox = (GridView) view.findViewById(R.id.WeatherGridBox);
        textViewNowW = (TextView) view.findViewById(R.id.textViewNowW);
        textViewNowT = (TextView) view.findViewById(R.id.textViewNowT);
        textViewE = (TextView) view.findViewById(R.id.textViewE);
        textViewI = (TextView) view.findViewById(R.id.textViewI);
        nowWeatherBox = (RelativeLayout) view.findViewById(R.id.nowWeatherBox);

        textViewE.setText("");
        textViewI.setText("");
        textViewNowW.setText("--");
        textViewNowT.setText("--");

        SetDatas(myBean);
    }

    public void SetDatas(WeatherDataBean wdb) {
        if (wdb != null && context != null) {
            myBean = wdb;
            cityName = wdb.getResult().get(0).getCity();
            wdb.getResult().get(0).getFuture().remove(0);
            weatherInfoAdapter = new WeatherInfoAdapter(getActivity(), wdb);
            weatherGridBox.setAdapter(weatherInfoAdapter);
            textViewTitle.setText(cityName);

            //当前天气信息
            WeatherDataBean.ResultBean.AirQualityBean aqb = wdb.getResult().get(0).getAirQuality();
            textViewE.setText(aqb.getQuality()+"\nPM2.5:"+aqb.getPm25()+"\nPM10 :"+aqb.getPm10());
            textViewI.setText(wdb.getResult().get(0).getHumidity()+"\n穿衣:"+wdb.getResult().get(0).getDressingIndex()+"\n户外:"+wdb.getResult().get(0).getExerciseIndex());
            textViewNowW.setText(wdb.getResult().get(0).getTemperature());
            textViewNowT.setText(wdb.getResult().get(0).getWeather()+"\n"+wdb.getResult().get(0).getWind());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            this.context = context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
