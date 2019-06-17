package com.yun.xiao.jing.action;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hss01248.dialog.StyledDialog;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.SignStringRequest;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.register.RegisterActivity;
import com.yun.xiao.jing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BlackAction extends BaseAction {
    public BlackAction(Activity activity, View baseView) {
        super(activity, baseView);
    }

    String requestCreateUrl = "";

    public void gainBlackUserDynamic(String userToken, String device, String dynamicToken, final RequestCallback requestCallback) {
        StyledDialog.buildLoading("").show();
        requestCreateUrl = ApiConstants.HOST + ApiConstants.SHIELDING_USER_DYNAMIC;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("shielding_token", dynamicToken);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990Sellection", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.BLACKED_USER_DYNAMIC_SUCCESS) {//
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
                        requestCallback.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StyledDialog.dismissLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    LogUtil.i(TAG, error.getMessage());
                }
//                Toast.makeText(ChessApp.sAppContext, R.string.club_create_failed, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
                StyledDialog.dismissLoading();
                if (requestCallback != null) {
                    requestCallback.onFailed();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    /**
     * 拉黑用户
     */
    public void otherBlackUser(String userToken, String device, String followToken, String follow_super, String like, final RequestCallback requestCallback) {
        StyledDialog.buildLoading("").show();
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_LIKE;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("follow_token", followToken);
        paramsMap.put("follow_super", follow_super);
        paramsMap.put("like", like);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990Sellection", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.LIKE_USER_SUCCESS) {//
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
                        requestCallback.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StyledDialog.dismissLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    LogUtil.i(TAG, error.getMessage());
                }
//                Toast.makeText(ChessApp.sAppContext, R.string.club_create_failed, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
                if (requestCallback != null) {
                    requestCallback.onFailed();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    /**
     *
     */
    public void submitReportMessage(String userToken, String device, String token, String text, final RequestCallback requestCallback) {
        StyledDialog.buildLoading("").show();
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_REPORT;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("report_token", token);
        paramsMap.put("text", text);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990Sellection", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.USER_TO_REPORT_SUCCESS) {//
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
                        requestCallback.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StyledDialog.dismissLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    LogUtil.i(TAG, error.getMessage());
                }
//                Toast.makeText(ChessApp.sAppContext, R.string.club_create_failed, Toast.LENGTH_SHORT).show();
//                DialogMaker.dismissProgressDialog();
                StyledDialog.dismissLoading();
                if (requestCallback != null) {
                    requestCallback.onFailed();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    /**
     * 建立好友关系
     */
    public void createUserSession(String userToken, String device, String token, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_SESSION;
        StyledDialog.buildLoading("").show();
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("to_token", token);
        Log.i("获取的数据", "" + userToken + "    device::::" + device + "   token");
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukuijinalihaoyou", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.ESTABLISHMENT_OF_IS_FRIENDSHIP) {//
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.ESTABLISHMENT_ALREADY_FRIENDS) {
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
                        requestCallback.onFailed();
                    }
                    StyledDialog.dismissLoading();
                    DialogMaker.dismissProgressDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    LogUtil.i(TAG, error.getMessage());
                }
//                Toast.makeText(ChessApp.sAppContext, R.string.club_create_failed, Toast.LENGTH_SHORT).show();
                DialogMaker.dismissProgressDialog();
                StyledDialog.dismissLoading();
                if (requestCallback != null) {
                    requestCallback.onFailed();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paramsMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }
}
