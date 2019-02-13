package com.zp.myhomemanager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.WeatherDataBean;
import com.zp.myhomemanager.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfoAdapter extends BaseAdapter {

    private WeatherDataBean myBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeatherDataBean.ResultBean.FutureBean> dataList = new ArrayList<>();

    static class ViewHolder {
        static TextView tvDate, tvInfos;
        static ImageView imgIcon;
    }

    ViewHolder viewHolder = null;

    public WeatherInfoAdapter(Context context, WeatherDataBean wdb) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        setMyBean(wdb);
    }

    private void setMyBean(WeatherDataBean wdb) {
        this.myBean = wdb;
        dataList = wdb.getResult().get(0).getFuture();

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
            viewHolder = new ViewHolder();
            try {
                convertView = mInflater.inflate(R.layout.weather_info_item, null);

            } catch (Exception e) {
                MyLog.e(e.toString());
            }

            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.info_date);
            viewHolder.tvInfos = (TextView) convertView.findViewById(R.id.info_text);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.info_icon);
//            convertView.setTag(viewHolder);


            WeatherDataBean.ResultBean.FutureBean fb = dataList.get(position);
            viewHolder.tvDate.setText(fb.getWeek());
            viewHolder.tvInfos.setText(fb.getDayTime() + "\n" + fb.getTemperature() + "\n" + fb.getWind());

            viewHolder.imgIcon.setImageResource(R.drawable.ic_launcher_foreground);
            viewHolder.imgIcon.setVisibility(View.GONE);
//        }


        return convertView;
    }
}
