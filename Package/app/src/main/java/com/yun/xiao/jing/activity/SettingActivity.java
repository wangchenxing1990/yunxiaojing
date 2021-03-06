package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.netease.nim.uikit.impl.cache.DataCacheManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.LoginAction;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.fragment.MeFragment;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.register.PhoneNumberActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    private LoginAction loginAction;
    private String token;
    private String device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loginAction = new LoginAction(this, null);
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        setContentView(R.layout.activity_setting);
        initView();
        token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
    }

    private FrameLayout frame_layout;
    private TextView text_about_us, text_terms_service, text_contanct_us, text_log_out, text_delete;

    private void initView() {
        frame_layout = findViewById(R.id.frame_layout);
        text_about_us = findViewById(R.id.text_about_us);
        text_terms_service = findViewById(R.id.text_terms_service);
        text_contanct_us = findViewById(R.id.text_contanct_us);

        text_log_out = findViewById(R.id.text_log_out);
        text_delete = findViewById(R.id.text_delete);

        frame_layout.setOnClickListener(this);
        text_about_us.setOnClickListener(this);
        text_terms_service.setOnClickListener(this);
        text_contanct_us.setOnClickListener(this);
        text_log_out.setOnClickListener(this);
        text_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_layout:
                finish();
                break;
            case R.id.text_about_us:
                WebActivity.start(this, ApiCode.WEB_ABOUT_US);
                break;
            case R.id.text_terms_service:
                WebActivity.start(this, ApiCode.WEB_SUGER_PRIVACY);
                break;
            case R.id.text_contanct_us:
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse(ApiConstants.URL_EMAIL));
                startActivity(data);
                break;
            case R.id.text_log_out:
                loginOutAccount();//登出账号
                break;
            case R.id.text_delete:
                deleteHaveAccount();//删除账号
                break;
        }
    }

    private void deleteHaveAccount() {
        loginAction.deleteAccount(token, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                DataCacheManager.clearDataCache();
                UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount("");
                UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken("");
                UserPreferences.getInstance(ChessApp.sAppContext).setUserToken("");
                finish();
                PhoneNumberActivity.start(SettingActivity.this);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void loginOutAccount() {
        loginAction.loginOutAccount(token, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                NIMClient.getService(AuthService.class).logout();
                UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount("");
                UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken("");
                UserPreferences.getInstance(ChessApp.sAppContext).setUserToken("");
                finish();
                PhoneNumberActivity.start(SettingActivity.this);
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
