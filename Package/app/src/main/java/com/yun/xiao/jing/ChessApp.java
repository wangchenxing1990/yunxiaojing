package com.yun.xiao.jing;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.yun.xiao.jing.api.HttpManager;

public class ChessApp extends Application {
    public static ChessApp sAppContext;
    public static RequestQueue sRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext=this;
        sRequestQueue = HttpManager.getInstance(sAppContext);
    }
}
