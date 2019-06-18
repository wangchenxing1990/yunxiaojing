package com.yun.xiao.jing.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttp {
    private static OkHttpClient okHttpClient = null;
    private static MyOkHttp mInstance;
    private OkHttpClient.Builder mOkHttpClient;

    public static MyOkHttp getInstance() {
        synchronized (MyOkHttp.class) {
            if (okHttpClient == null) {
                mInstance = new MyOkHttp();
            }
        }
        return mInstance;
    }

    private MyOkHttp() {
        mOkHttpClient = new OkHttpClient.Builder();
        mOkHttpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .build();
    }

    public static void getCall(){
        Request request=new Request.Builder()
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    static class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }

}
