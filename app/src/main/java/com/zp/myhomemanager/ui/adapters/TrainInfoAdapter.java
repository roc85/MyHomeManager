package com.zp.myhomemanager.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.TrainDataBean;
import com.zp.myhomemanager.utils.MyLog;
import com.zp.myhomemanager.utils.TimeDateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TrainInfoAdapter extends BaseAdapter {

    private TrainDataBean myBean;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<TrainDataBean.ResultBean> dataList = new ArrayList<>();

    static class ViewHolder {
        static TextView tvTitle, tvType,tvTrain,tvPrice;
    }

    ViewHolder viewHolder = null;

    public TrainInfoAdapter(Context context, TrainDataBean tdb) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        setMyBean(tdb);
    }

    private void setMyBean(TrainDataBean tdb) {
        this.myBean = tdb;
        dataList = tdb.getResult();

        //List排序
        Collections.sort(dataList, new Comparator<TrainDataBean.ResultBean>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(TrainDataBean.ResultBean lhs, TrainDataBean.ResultBean rhs) {
                Date date1 = TimeDateUtil.parseDate(lhs.getStartTime(),TimeDateUtil.DF_HH_MM);
                Date date2 = TimeDateUtil.parseDate(rhs.getStartTime(),TimeDateUtil.DF_HH_MM);
                MyLog.i("日期比较", lhs.getStartTime()+"**"+rhs.getStartTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.after(date2)) {
                    MyLog.i("比较结果", "1");
                    return 1;
                }
                MyLog.i("比较结果", "-1");
                return -1;
            }
        });
        MyLog.i("比较结果", "-1");
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
                convertView = mInflater.inflate(R.layout.train_info_item, null);

            } catch (Exception e) {
                MyLog.e(e.toString());
            }

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.textViewType);
            viewHolder.tvTrain = (TextView) convertView.findViewById(R.id.textStart);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.textPrice);

//            convertView.setTag(viewHolder);

            TrainDataBean.ResultBean rb = dataList.get(position);
            viewHolder.tvTitle.setText(rb.getStationTrainCode());
            viewHolder.tvType.setText(rb.getTrainClassName());
            viewHolder.tvTrain.setText(rb.getStartStationName()+"("+rb.getStartTime()+")---"+rb.getEndStationName()+"("+rb.getArriveTime()+")");

            if(rb.getTrainClassName().startsWith("高") || rb.getTrainClassName().startsWith("动"))
            {
                viewHolder.tvTitle.setTextColor(0xFFff4081);
                viewHolder.tvPrice.setText("商务:"+(TextUtils.isEmpty(rb.getPricesw())?"--":rb.getPricesw())+" ");
                viewHolder.tvPrice.append("一等:"+(TextUtils.isEmpty(rb.getPriceyd())?"--":rb.getPriceyd())+" ");
                viewHolder.tvPrice.append("二等:"+(TextUtils.isEmpty(rb.getPriceed())?"--":rb.getPriceed())+" ");
            }
            else
            {
                viewHolder.tvPrice.setText("硬卧:"+(TextUtils.isEmpty(rb.getPriceyw())?"--":rb.getPriceyw())+" ");
                viewHolder.tvPrice.append("硬座:"+(TextUtils.isEmpty(rb.getPriceyz())?"--":rb.getPriceyz())+" ");
                viewHolder.tvPrice.append("无座:"+(TextUtils.isEmpty(rb.getPricewz())?"--":rb.getPricewz())+" ");
            }
//        }
//        else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }



        return convertView;
    }
}
