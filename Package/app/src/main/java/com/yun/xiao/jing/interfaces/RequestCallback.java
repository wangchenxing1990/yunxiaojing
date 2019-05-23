package com.yun.xiao.jing.interfaces;

/**
 *
 */
public interface RequestCallback {
    //通用请求接口
    void onResult(int code, String result, Throwable var3);

    void onFailed();
}
