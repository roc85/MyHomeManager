package com.zp.myhomemanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.TrainDataBean;
import com.zp.myhomemanager.ui.adapters.TrainInfoAdapter;

public class TrainFragment extends BaseFragment {

    private ListView infosList;
    private TrainInfoAdapter trainInfoAdapter;
    private TrainDataBean myBean;
    private Context context;
    private String cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_train, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        infosList = (ListView) view.findViewById(R.id.infosList);

        SetDatas(myBean);
    }

    public void SetDatas(TrainDataBean tdb)
    {
        if(tdb != null && context != null)
        {
            myBean = tdb;
            trainInfoAdapter = new TrainInfoAdapter(getActivity(), tdb);
            infosList.setAdapter(trainInfoAdapter);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null)
        {
            this.context = context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
