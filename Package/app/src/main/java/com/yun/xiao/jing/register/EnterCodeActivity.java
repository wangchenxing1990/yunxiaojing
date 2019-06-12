package com.yun.xiao.jing.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.defineView.GridPwdView;
import com.yun.xiao.jing.interfaces.NumKeyboard;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class EnterCodeActivity extends AppCompatActivity implements View.OnClickListener {
    public static void startActivity(Activity activity, String phone) {
        Intent intent = new Intent(activity, EnterCodeActivity.class);
        intent.putExtra(ApiCode.PHONE, phone);
        activity.startActivity(intent);
    }

    private RegisterAction mAction;
    private TextView text_button_view, text_two;
    private NumKeyboard num_keyboard_layout;
    private GridPwdView join_game_grid_pwd_view;
    String phone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        mAction = new RegisterAction(this, null);
        phone = getIntent().getStringExtra(ApiCode.PHONE);
        setContentView(R.layout.activity_enter_code);
        initView();
        text_two.setText(phone);
    }

    private void getData() {
        String gameCode = join_game_grid_pwd_view.getPassWord();
        String device = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        mAction.getVerifyMessageCode(phone, gameCode, device, "86", new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Log.i("短信验证码获取的数据::::::", result);
                try {
                    JSONObject json = new JSONObject(result);
                    JSONObject info = json.getJSONObject("info");
                    String token = info.getString("token");
                    String imaccount = info.getString("imaccount");
                    String imtoken = info.getString("imtoken");
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserToken(token);
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserIMAccount(imaccount);
                    UserPreferences.getInstance(ChessApp.sAppContext).setUserIMToken(imtoken);
//                    IntroduceActivity.start(EnterCodeActivity.this);
//                    SetPasswordActivity.start(EnterCodeActivity.this);
                    AddUserHeaderImgActivity.start(EnterCodeActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void initView() {
        text_button_view = findViewById(R.id.text_button_view);
        text_two = findViewById(R.id.text_two);
        num_keyboard_layout = findViewById(R.id.num_keyboard_layout);
        join_game_grid_pwd_view = findViewById(R.id.join_game_grid_pwd_view);
        join_game_grid_pwd_view.setPasswordVisibility(true);
        num_keyboard_layout.setNumKeyboardClick(join_game_grid_pwd_view);
        text_button_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_button_view:
                getData();//获取网络数据
//                IntroduceActivity.start(this);
                break;
        }
    }
}
