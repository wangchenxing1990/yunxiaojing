package com.yun.xiao.jing.action;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.SignStringRequest;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginAction extends BaseAction {
    private String requestCreateUrl = "";

    public LoginAction(Activity activity, View baseView) {
        super(activity, baseView);
    }

    public void loginOutAccount(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.ACCOUNT_IS_OFF_LINE;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile_device", device);
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.ACCOUNT_OFF_LINE_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.ACCOUNT_OFF_LINE_FAILED) {//账号离线失败
                        requestCallback.onFailed();
                    }
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

    public void deleteAccount(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_DELETE_ACCOUNT;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.ACCOUNT_OFF_LINE_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.ACCOUNT_OFF_LINE_FAILED) {//账号离线失败
                        requestCallback.onFailed();
                    }
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
//                DialogMaker.dismissProgressDialog();
                if (requestCallback != null) {
                    requestCallback.onFailed();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void loginUserQueryInformation(String mobile, String mobile_prefix, String password, String mobile_type, String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.VERIFY_LOGIN_PASSWORD;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> paramsMap = new HashMap<>();

        paramsMap.put("mobile", mobile);
        paramsMap.put("mobile_prefix", mobile_prefix);
        paramsMap.put("password", password);
        paramsMap.put("mobile_type", mobile_type);
        paramsMap.put("user_token", userToken);
        paramsMap.put("mobile_device", device);
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990login", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.PASSWORD_IS_CORRECT) {//登陆成功
                        requestCallback.onResult(code, response, null);
                    } else {//登陆失败
                        requestCallback.onFailed();
                    }
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

//            @Override
//            public Map<String, String> getPa() throws AuthFailureError {
//                return headerMap;
//            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }
}
