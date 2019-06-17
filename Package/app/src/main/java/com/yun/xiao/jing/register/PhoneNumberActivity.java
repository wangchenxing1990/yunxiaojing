package com.yun.xiao.jing.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text_view_phone;
    private RegisterAction mAction;
    private String token = "";
    private String device = "";

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PhoneNumberActivity.class);
        intent.putExtra("login_out", "login_out");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private RelativeLayout relative_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String loginOut = getIntent().getStringExtra("login_out");
        mAction = new RegisterAction(this, null);
        token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        setContentView(R.layout.activity_phone_number);
        relative_layout = findViewById(R.id.relative_layout);
        text_view_phone = findViewById(R.id.text_view_phone);
        text_view_phone.setOnClickListener(this);
        if (loginOut != null && loginOut.equals("login_out")) {
            relative_layout.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(token)) {//没有token//检测用户是否登录
            checkUserLogin();
        } else {
            relative_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_phone:
                RegisterActivity.start(this);
                break;
        }
    }

    private void checkUserLogin() {
        mAction.checkUserLogin(token, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Log.i("检查是否自动登录", result);
                if (code == ApiCode.USER_CODE_SUCCESSFULLY) {//token没有过期
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
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken(imtoken);
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount(imaccount);
                        UserPreferences.getInstance(ChessApp.sAppContext).setUserName(username);
                        if (TextUtils.isEmpty(headImg)) {
                            AddUserHeaderImgActivity.start(PhoneNumberActivity.this);
                        } else if (TextUtils.isEmpty(username)) {
                            IntroduceActivity.start(PhoneNumberActivity.this);
                        } else if (TextUtils.isEmpty(password)) {
                            SetPasswordActivity.start(PhoneNumberActivity.this);
                        } else {
                            loginNeteaseNim(imaccount, imtoken);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (code == ApiCode.VERIFICATION_CODE) {
//                    EnterCodeActivity.startActivity(PhoneNumberActivity.this,edit_text_input.getText().toString().trim());
                } else if (code == ApiCode.USER_ALREADY_OFFLINE) {
                    relative_layout.setVisibility(View.VISIBLE);
                } else if (code == ApiCode.USER_TIME_EXPIRATION) {
                    RegisterActivity.start(PhoneNumberActivity.this);
                } else if (code == ApiCode.GET_USER_INFORMATION_FAILED) {
                    RegisterActivity.start(PhoneNumberActivity.this);
                } else if (code == ApiCode.TOKEN_NO_QUERY) {
                    RegisterActivity.start(PhoneNumberActivity.this);
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void loginNeteaseNim(String imaccount, String imtoken) {
        LoginInfo loginInfo = new LoginInfo(imaccount, imtoken);
        com.netease.nimlib.sdk.RequestCallback<LoginInfo> call = new com.netease.nimlib.sdk.RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Log.i("登录云信成功", "登录云信成功");
                MainActivity.start(PhoneNumberActivity.this);
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
}
