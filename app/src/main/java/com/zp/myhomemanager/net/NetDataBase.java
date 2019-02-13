package com.zp.myhomemanager.net;

import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetDataBase {

    private static final OkHttpClient client = new OkHttpClient();

    public static void TransDatas(String _ip, JSONObject src, Callback callback)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        if(src != null)
        {
            RequestBody body = RequestBody.create(JSON, src.toString());
            Request request = new Request.Builder()
                    .url(_ip)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callback);
        }
        else
        {
            Request request = new Request.Builder()
                    .url(_ip)
                    .build();
            client.newCall(request).enqueue(callback);
        }
    }
}
