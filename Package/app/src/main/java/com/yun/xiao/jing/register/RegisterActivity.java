package com.yun.xiao.jing.register;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MainActivity;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.util.LogUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_text_input;
    private TextView text_view_next;
    private RegisterAction mAction;
    private String token = "";
    private String device = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = new RegisterAction(this, null);
        token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        setContentView(R.layout.activity_register);
        initView();
        if (!TextUtils.isEmpty(token)) {
            checkUserLogin();
        }
    }

    private void checkUserLogin() {
        mAction.checkUserLogin(token, device, new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {
                MainActivity.start(RegisterActivity.this);
                finish();
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void initData() {
        final String phone = edit_text_input.getText().toString().trim();
        String id = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);

        mAction.getRegisterCode(phone, id, "86", new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                LogUtil.i("wangyukui1990", result);
                if (code == ApiCode.VERIFICATION_CODE) {//注册界面
                    EnterCodeActivity.startActivity(RegisterActivity.this, phone);
                } else if (code == ApiCode.USER_HAVE_REGISTER) {//登录界面

                } else if (code == ApiCode.USER_HAVE_PSW_EMPTY) {//介绍自己的界面
                    IntroduceActivity.start(RegisterActivity.this);
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
        switch (v.getId()) {
            case R.id.text_view_next:
                Toast.makeText(ChessApp.sAppContext, "获取网络数据", Toast.LENGTH_SHORT).show();
                initData();//获取网络数据
                break;
        }
    }
}
