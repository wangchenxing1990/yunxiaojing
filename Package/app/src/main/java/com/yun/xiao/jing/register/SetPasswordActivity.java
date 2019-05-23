package com.yun.xiao.jing.register;

import android.app.Activity;
import android.content.Intent;
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

import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MainActivity;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

public class SetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SetPasswordActivity.class);
        activity.startActivity(intent);
    }

    private RegisterAction mAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction = new RegisterAction(this, null);
        setContentView(R.layout.activity_set_password);
        initView();
    }

    private EditText edit_input_password, edit_check_password;
    private TextView text_button_continue;

    private void initView() {
        edit_input_password = findViewById(R.id.edit_input_password);
        edit_check_password = findViewById(R.id.edit_check_password);
        text_button_continue = findViewById(R.id.text_button_continue);
        text_button_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_button_continue:
                updateSetPassword();
                break;
        }
    }

    private void updateSetPassword() {
        String password = edit_input_password.getText().toString().trim();
        String passwordCheck = edit_check_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(ChessApp.sAppContext, R.string.password_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordCheck)) {
            Toast.makeText(ChessApp.sAppContext, R.string.passwordCheck_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordCheck)) {
            Toast.makeText(ChessApp.sAppContext, R.string.password_no_passwordCheck, Toast.LENGTH_SHORT).show();
            return;
        }
        String token= UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Log.i("TAGTaG","token::"+token+"device:::"+device);
        mAction.updatePasswordInfo(token, device, passwordCheck, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                MainActivity.start(SetPasswordActivity.this);
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
