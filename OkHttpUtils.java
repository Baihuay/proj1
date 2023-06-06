package com.example.proj4.net;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    private OkHttpUtils(){}
    private static OkHttpUtils instance = new OkHttpUtils();
    private OkHttpClient okHttpClient = new OkHttpClient();

    public static OkHttpUtils getInstance() {
        return instance;
    }

    public String doGet(String url) throws Exception{
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}