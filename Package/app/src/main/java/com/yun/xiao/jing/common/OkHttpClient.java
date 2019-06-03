package com.yun.xiao.jing.common;

public class OkHttpClient {
    public OkHttpClient(){

    }
    private static OkHttpClient okHttpClient;
    public static synchronized OkHttpClient getInstance(){
        if (okHttpClient==null){
            okHttpClient= new OkHttpClient();
        }
        return okHttpClient;
    }

    public void submitToPhotoToService(){

    }

}
