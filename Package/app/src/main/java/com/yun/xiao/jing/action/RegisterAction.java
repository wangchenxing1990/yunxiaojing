package com.yun.xiao.jing.action;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.NetWork;
import com.yun.xiao.jing.api.SignStringRequest;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.util.LogUtil;
import com.yun.xiao.jing.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterAction extends BaseAction {

    public RegisterAction(Activity activity, View baseView) {
        super(activity, baseView);

    }

    private String requestCreateUrl = "";

    public void getRegisterCode(String mobile, String mobile_device, String mobile_prefix, final RequestCallback requestCallback) {
//        if (!NetworkUtil.isNetAvailable(ChessApp.sAppContext)) {
//            return;
//        }
        requestCreateUrl = ApiConstants.HOST + ApiConstants.CHECK_LOGIN;
        Log.i("wangyukui1990", requestCreateUrl);
//        final HashMap<String, String> paramsMap = NetWork.getRequestCommonParams(ChessApp.sAppContext);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile", mobile);
        paramsMap.put("mobile_device", mobile_device);
        paramsMap.put("mobile_prefix", mobile_prefix);
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.TOKEN_NO_EXIST) {//100token不存在或无效token
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.TOKEN_NO_QUERY) {//101token未查询到用户信息请重新登录
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.PHONE_IS_EMPTY) {//102手机号码参数为空
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.PHONE_UMBER_IS_EMPTY) {//103手机号码不能为空
                        Toast.makeText(ChessApp.sAppContext, R.string.text_phone_no_empty, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.MOBILE_DEVICE_EMPTY) {//104手机设备不能为空
                        Toast.makeText(ChessApp.sAppContext, R.string.text_device_no_empty, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.SMS_IS_SUCCESSFULLY) {//105短信发送成功
                        Toast.makeText(ChessApp.sAppContext, R.string.text_message_send_successfully, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.SMS_IS_FAILED) {//106短信发送失败
                        Toast.makeText(ChessApp.sAppContext, R.string.text_message_send_failed, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.PHONE_PREFIX_EMPTY) {//107手机号前缀地域参数为空
                        Toast.makeText(ChessApp.sAppContext, R.string.text_phone_prefix_empty, Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.PHONE_PREFIX_NO_EMPTY) {//108手机号前缀地域不能为空
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.VERIFICATION_CODE) {//109返回测试短信验证码
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.USER_HAVE_REGISTER) {//520用户已注册请登录
//                         Toast.makeText(ChessApp.sAppContext,"",Toast.LENGTH_SHORT).show();
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.USER_HAVE_PSW_EMPTY) {//521用户已注册但密码为空
                        requestCallback.onResult(code, response, null);
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
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void getVerifyMessageCode(String mobile, String mobile_code, String mobile_device, String mobile_prefix, final RequestCallback requestCallback) {

        requestCreateUrl = ApiConstants.HOST + ApiConstants.VERIFY_MOBILE_CODE;
        Log.i("wangyukui1990", requestCreateUrl);
//        final HashMap<String, String> paramsMap = NetWork.getRequestCommonParams(ChessApp.sAppContext);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile", mobile);
        paramsMap.put("mobile_device", mobile_device);
        paramsMap.put("mobile_prefix", mobile_prefix);
        paramsMap.put("mobile_code", mobile_code);
        paramsMap.put("mobile_type", "Android");
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.SMS_CODE_NO_EMPTY) {//110短信验证码不能为空
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.SMS_CODE_INVALID) {//111短信验证码无效或已过期
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.SMS_CODE_ERROR) {//112短信验证码错误
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.USER_CODE_SUCCESSFULLY) {//120获取用户信息成功
                        Toast.makeText(ChessApp.sAppContext, R.string.text_phone_no_empty, Toast.LENGTH_SHORT).show();
                        requestCallback.onResult(code, response, null);
                        return;
                    } else if (code == ApiCode.SYSTEM_CODE_WRITE_FAILED) {//121系统写入用户失败
                        Toast.makeText(ChessApp.sAppContext, R.string.text_device_no_empty, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.SYSTEM_CODE_WRITE_USER_INFORMATION_FAILED) {//122系统写入用户信息失败
                        Toast.makeText(ChessApp.sAppContext, R.string.text_message_send_successfully, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.SYSTEM_CODE_WRITE_USER_TOKEN_FAILED) {//123系统写入用户token失败
                        Toast.makeText(ChessApp.sAppContext, R.string.text_message_send_failed, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code == ApiCode.NETEASE_CREATE_TOKEN_FAILED) {//124网易token创建失败
                        Toast.makeText(ChessApp.sAppContext, R.string.text_phone_prefix_empty, Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.UPDATE_CREATE_TOKEN_FAILED) {//125更新用户网易token失败
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.USER_CREATE_FAILED) {//126用户创建失败
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.GET_USER_INFORMATION_FAILED) {//127获取用户信息失败
//                         Toast.makeText(ChessApp.sAppContext,"",Toast.LENGTH_SHORT).show();
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.USER_ALREADY_EXISTS) {//128用户已经存在
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
                    } else if (code == ApiCode.LOGIN_DEVICE_FAILED) {//129登陆设备记录失败
                        Toast.makeText(ChessApp.sAppContext, "", Toast.LENGTH_SHORT).show();
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
        };
        signRequest.setTag(requestCreateUrl);
        ChessApp.sRequestQueue.add(signRequest);
    }

    public void updateInformation(String username, String birthday, int sex, String token, String device, final RequestCallback requestCallback) {

        requestCreateUrl = ApiConstants.HOST + ApiConstants.UPDATE_USER_INFO;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("user-token", token);
        headerMap.put("mobile-device", device);
        final HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("username", username);
        paramsMap.put("birthday", birthday);
        paramsMap.put("sex", String.valueOf(sex));
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.USER_INFORMATION_UPDATE_SUCCESSFULLY) {//110短信验证码不能为空
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.USER_INFORMATION_UPDATE_FAILED) {//111短信验证码无效或已过期
                        requestCallback.onFailed();
                    } else if (code == ApiCode.USER_NAME_IS_EMPTY) {//112短信验证码错误
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

    public void updatePasswordInfo(String userToken, String device, String password, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.ADD_USER_PASSWORD;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();
        final HashMap<String, String> paramsMap = new HashMap<>();
        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        paramsMap.put("password", password);
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.USER_PASSWORD_UPDATED_SUCCESSFULLY) {
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.USER_PASSWORD_UPDATED_FAILED) {
                        requestCallback.onFailed();
                    } else if (code == ApiCode.USER_PASSWORD_IS_EMPTY) {
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

    public void addHeaderImgInfo(String userToken, String device, String imgurl, String imgtype, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.ADD_USER_PASSWORD;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();
        final HashMap<String, String> paramsMap = new HashMap<>();
        headerMap.put("user-token", userToken);
        headerMap.put("mobile-device", device);
        paramsMap.put("imgurl", imgurl);
        paramsMap.put("imgtype", imgtype);
        Log.i("wangyukui1990", requestCreateUrl + paramsMap.toString());
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.IMAGE_UPLOAD_SUCCESSFULLY) {//上传图片成功
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.IMAGE_UPLOAD_FAILED) {//上传图片失败
                        requestCallback.onFailed();
                    } else if (code == ApiCode.IMAGE_SERVICE_FAILED) {//保存到服务器本地图片失败
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

    public void checkUserLogin(String token, String device, final RequestCallback requestCallback) {
        requestCreateUrl = ApiConstants.HOST + ApiConstants.CHECK_USER_LOGIN;
        Log.i("wangyukui1990", requestCreateUrl);
        final HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("user-token", token);
        headerMap.put("mobile-device", device);
        Log.i("wangyukui1990", requestCreateUrl);
        SignStringRequest signRequest = new SignStringRequest(Request.Method.POST, requestCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("wangyukui1990", response);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    if (code == ApiCode.USER_CODE_SUCCESSFULLY) {//token不存在或无效token
                        requestCallback.onResult(code, response, null);
                    } else if (code == ApiCode.IMAGE_UPLOAD_FAILED) {//token未查询到用户信息请重新登录
                        requestCallback.onFailed();
                    } else if (code == ApiCode.IMAGE_SERVICE_FAILED) {//账号在该设备已经离线
                        requestCallback.onFailed();
                    }else if(code == ApiCode.USER_TIME_EXPIRED_UPDATE_FAILED){
                        requestCallback.onFailed();
                    }else if(code == ApiCode.USER_TIME_EXPIRATION){
                        requestCallback.onResult(code, response, null);
                    }else if(code == ApiCode.USER_ALREADY_OFFLINE){
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
}
