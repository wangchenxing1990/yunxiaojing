package com.yun.xiao.jing.register;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.util.C;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MainActivity;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_text_input;
    private TextView text_view_next;
    private RegisterAction mAction;
    private String token = "";
    private String device = "";
    private static final int BASIC_PERMISSION_REQUEST_CODE = 100;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private static final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        mAction = new RegisterAction(this, null);
        token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        setContentView(R.layout.activity_register);
        initView();
        requestBasicPermission();
        Log.i("检测是否可以自动登录", "tokentoken:::" + token);
    }

    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(RegisterActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        try {
//            ToastHelper.showToast(this, "授权成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        try {
//            ToastHelper.showToast(this, "未全部授权，部分功能可能无法正常运行！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }


    private void loginNeteaseNim(String imaccount, String imtoken) {
        LoginInfo loginInfo = new LoginInfo(imaccount, imtoken);
        com.netease.nimlib.sdk.RequestCallback<LoginInfo> call = new com.netease.nimlib.sdk.RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                MainActivity.start(RegisterActivity.this);
                finish();
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        };
        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(call);
    }

    /**
     * 检测用户登录
     */
    private void initData() {
        final String phone = edit_text_input.getText().toString().trim();
        String id = UserPreferences.getDevice();
         if (TextUtils.isEmpty(phone)){
             Toast.makeText(ChessApp.sAppContext,"请输入手机号码",Toast.LENGTH_SHORT).show();
             return;
         }
        mAction.getRegisterCode(phone, id, "86", new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Log.i("检查是否已经注册", result);
                if (code == ApiCode.SMS_IS_SUCCESSFULLY) {//注册界面
                    EnterCodeActivity.startActivity(RegisterActivity.this, phone);
                } else if (code == ApiCode.USER_HAVE_REGISTER) {//登录界面
                    LoginActivity.start(RegisterActivity.this, phone, "86");
                    finish();
                } else if (code == ApiCode.USER_HAVE_PSW_EMPTY) {//介绍自己的界面
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonInfo = jsonObject.getJSONObject("info");
                        String token = jsonInfo.getString("token");
                        String headImg = jsonInfo.getString("headimg");
                        String username = jsonInfo.getString("username");
                        String password = jsonInfo.getString("password");
                        String imtoken = jsonInfo.getString("imtoken");
                        String imaccount = jsonInfo.getString("imaccount");

                        UserPreferences.getInstance(ChessApp.sAppContext).setUserToken(token);
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount(imaccount);
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken(imtoken);
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserName(username);
                        if (TextUtils.isEmpty(headImg)) {
                            AddUserHeaderImgActivity.start(RegisterActivity.this);
                        } else if (TextUtils.isEmpty(username)) {
                            IntroduceActivity.start(RegisterActivity.this);
                        } else if (TextUtils.isEmpty(password)) {
                            SetPasswordActivity.start(RegisterActivity.this);
                        } else {
                            MainActivity.start(RegisterActivity.this);
                            ChessApp.removeActivity(RegisterActivity.this);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (code == ApiCode.VERIFICATION_CODE) {
                    EnterCodeActivity.startActivity(RegisterActivity.this, edit_text_input.getText().toString().trim());
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void initView() {
        edit_text_input = findViewById(R.id.edit_text_input);
        text_view_next = findViewById(R.id.text_view_next);
        text_view_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.text_view_next) {
                initData();//获取网络数据
        }
    }
}
