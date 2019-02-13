package com.zp.myhomemanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.zp.myhomemanager.R;
import com.zp.myhomemanager.beans.ZhidaoData;

public class WebFragment extends BaseFragment {

    private Context context;
    private ZhidaoData myBean;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {

        webView = (WebView) view.findViewById(R.id.webView);

        SetDatas(myBean);
    }

    public void SetDatas(ZhidaoData zdd) {
        if (zdd != null && context != null) {
            myBean = zdd;
            webView.loadUrl("https://zhidao.baidu.com"+myBean.getUrlPath());
//            webView.loadUrl("http://www.xinkebiao.com.cn/userfiles/ptzy/txqw/动物1.mp3");
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
