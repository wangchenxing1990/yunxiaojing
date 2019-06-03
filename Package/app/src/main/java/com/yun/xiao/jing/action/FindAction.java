package com.yun.xiao.jing.action;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.ApiParams;
import com.yun.xiao.jing.api.SignStringRequest;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FindAction extends BaseAction {
    public FindAction(Activity activity, View baseView) {
        super(activity, baseView);
    }

    private String requestCreateUrl = "";

    public void getUserList(String userToken, String device, String sex, String p, String page, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.GET_USER_LISTS;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("dating_preferences", sex);
        paramsMap.put("p", p);
        paramsMap.put("page", page);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.THE_USER_LIST_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    }
                    {//账号离线失败
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getOtherInfo(String userToken, String device, String token, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_USER_LIST;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("from_token", token);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.PERSONAL_INFORMATION_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    }
                    {//账号离线失败
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    /**
     * 主页获取浏览记录的次数的请求接口
     */
    public void getDataBrowse(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_PAGE_BROWSE_COUNT;
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
                    if (code == ApiCode.HOME_PAGE_USER_PAGE_VIEWS) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    }
                    {//账号离线失败
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getDataInformation(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.PERSONAL_HOME_PAGE;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put(ApiParams.USER_TOKEN, userToken);
        headerMap.put(ApiParams.MOBILE_DEVICE, device);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("个人主页信息", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.PERSONAL_HOME_PAGE_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    }
                    {//账号离线失败
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }


    public void getDataMeConcern(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_FOCUS_ON_COUNT;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui19901", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.TOTAL_STATISTICS) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getDataFansConcern(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_FANS_COUNT;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui19902", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.TOTAL_STATISTICS) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getBrowseCount(String userToken, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_PAGE_BROWSE;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui19903", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.TOTAL_STATISTICS) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getFindDiscoveryData(String userToken, String device, String type, String p, String page, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_DYNAMIC_LISTS;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("type", type);
        paramsMap.put("p", p);
        paramsMap.put("page", page);
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("发现界面返回的数据", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.DYNAMIC_LIST_DATA) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void submitSelectUserService(String userToken, String device, String token, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_FOCUS_ON;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("follow_token", token);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990Sellection", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.FOUCS_ON_SUCCESS) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void gainUserPageBrowseList(String userToken, String device, String p, String page, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.USER_PAGE_BROWSE_LIST;
        final HashMap<String, String> headerMap = new HashMap<>();

        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("p", p);
        paramsMap.put("page", page);

        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990Sellection", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.HOME_BROWSING_IS_SUCCESSFULLY) {//账号离线成功
                        requestCallback.onResult(code, response, null);
                    } else {//账号离线失败
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerMap;
            }
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }
}
