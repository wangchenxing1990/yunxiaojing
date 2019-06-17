package com.yun.xiao.jing.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MainActivity;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.LoginAction;
import com.yun.xiao.jing.preference.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity, String name, String mobile_prefix) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("mobile_prefix", mobile_prefix);
        activity.startActivity(intent);
    }

    private LoginAction loginAction;
    private String userToken = "";
    private String device = "";
    private String mobile_prefix = "";
    private String name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        loginAction = new LoginAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        mobile_prefix = intent.getStringExtra("mobile_prefix");
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化view
     */
    private TextView edit_input_name;
    private EditText edit_input_password;
    private TextView text_view_login;

    private void initView() {
        edit_input_name = findViewById(R.id.edit_input_name);
        edit_input_password = findViewById(R.id.edit_input_password);
        text_view_login = findViewById(R.id.text_view_login);
        text_view_login.setOnClickListener(this);
        edit_input_name.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_login:
                clickLoginUser();
                break;
        }
    }

    private void clickLoginUser() {
        name = edit_input_name.getText().toString().trim();
        String password = edit_input_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        loginAction.loginUserQueryInformation(name, mobile_prefix, password, "android", userToken, device, new RequestCallback() {
            @Override
            public void onResult(final int code, String result, Throwable var3) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonInfo = jsonObject.getJSONObject("info");
                    String token = jsonInfo.getString("token");

                    String headImg = jsonInfo.getString("headimg");
                    String username = jsonInfo.getString("username");
                    String password = jsonInfo.getString("password");
                    String imaccount = jsonInfo.getString("imaccount");
                    String imtoken = jsonInfo.getString("imtoken");
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserToken(token);
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount(imaccount);
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken(imtoken);
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserName(username);
                    if (TextUtils.isEmpty(headImg)) {
                        AddUserHeaderImgActivity.start(LoginActivity.this);
                    } else if (TextUtils.isEmpty(username)) {
                        IntroduceActivity.start(LoginActivity.this);
                    } else if (TextUtils.isEmpty(password)) {
                        SetPasswordActivity.start(LoginActivity.this);
                    } else {
                        Log.i("登录云信", "登陆云新");
                        com.netease.nimlib.sdk.auth.LoginInfo info = new com.netease.nimlib.sdk.auth.LoginInfo(imaccount, imtoken); // config...
                        com.netease.nimlib.sdk.RequestCallback<com.netease.nimlib.sdk.auth.LoginInfo> callback =
                                new com.netease.nimlib.sdk.RequestCallback<com.netease.nimlib.sdk.auth.LoginInfo>() {
                                    @Override
                                    public void onSuccess(LoginInfo loginInfo) {
                                        Log.i("登录云信成功", "account:::::" + loginInfo.getAccount() + "  appkey:::" + loginInfo.getAppKey() + "  token:::" + loginInfo.getToken());
                                        MainActivity.start(LoginActivity.this);
                                        finish();
                                    }

                                    @Override
                                    public void onFailed(int i) {
                                        Log.i("登录云信失败", "account:::::" + i);
                                    }

                                    @Override
                                    public void onException(Throwable throwable) {
                                        Log.i("登录云信异常", "account:::::" + throwable);
                                    }
                                };
                        NIMClient.getService(AuthService.class).login(info)
                                .setCallback(callback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed() {

            }
        });
    }
}
