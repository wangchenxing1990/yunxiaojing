package com.yun.xiao.jing.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.util.DateTool;
import com.yun.xiao.jing.util.LogUtil;

import java.lang.ref.WeakReference;
import java.util.Date;

public class IntroduceActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView button_text_continue;
    private EditText edit_text;
    private FrameLayout frame_layout_fale;
    private FrameLayout frame_layout_female;
    private ImageView image_female, image_fale;
    private RegisterAction mAction;
    private int sex = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, IntroduceActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        mAction = new RegisterAction(this, null);
        setContentView(R.layout.activity_introduce);
        initData();
        initView();

    }

    private Drawable fale;
    private Drawable female;
    private Drawable normal;
    private TextView text_view_time;

    private void initData() {
        fale = getResources().getDrawable(R.drawable.background_fale_drawable);
        female = getResources().getDrawable(R.drawable.drawable_female_background);
        normal = getResources().getDrawable(R.drawable.register_background);
    }

    private void initView() {
        frame_layout_female = findViewById(R.id.frame_layout_female);
        frame_layout_fale = findViewById(R.id.frame_layout_fale);
        button_text_continue = findViewById(R.id.button_text_continue);
        image_fale = findViewById(R.id.image_fele);
        image_female = findViewById(R.id.image_female);
        edit_text = findViewById(R.id.edit_text);
        text_view_time = findViewById(R.id.text_view_time);

        frame_layout_fale.setOnClickListener(this);
        frame_layout_female.setOnClickListener(this);
        button_text_continue.setOnClickListener(this);
        text_view_time.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_text_continue:
                updateIntroduceInformation();
                break;
            case R.id.frame_layout_fale:
                hideSoftInput(v);//隐藏软键盘
                frame_layout_fale.setBackgroundDrawable(fale);
                frame_layout_female.setBackgroundDrawable(normal);
                image_fale.setImageResource(R.mipmap.icon_male_normal);
                image_female.setImageResource(R.mipmap.icon_female);
                sex = 1;
                break;
            case R.id.frame_layout_female:
                hideSoftInput(v);//隐藏软键盘
                frame_layout_fale.setBackgroundDrawable(normal);
                frame_layout_female.setBackgroundDrawable(female);
                image_fale.setImageResource(R.mipmap.icon_male);
                image_female.setImageResource(R.mipmap.icon_female_normal);
                sex = 2;
                break;
            case R.id.text_view_time:
                hideSoftInput(v);//隐藏软键盘
                pikerTimeView();
                break;
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param v
     */
    public void hideSoftInput(View v) {
        if (isSoftShowing()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    String birthday = "";

    private void pikerTimeView() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(IntroduceActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthday = DateTool.getTime(date);
                long year = DateTool.compareTime(birthday);
                Log.i("data", date.toString());
                if (year >= 18) {
                    text_view_time.setText(birthday);
                } else {
                    Toast.makeText(ChessApp.sAppContext, "年龄必须大于18周岁", Toast.LENGTH_SHORT).show();
                    Log.i("年龄必须大于18周岁data", date.toString());
                }
            }
        }).build();
        pvTime.show();
    }

    private void updateIntroduceInformation() {
        String username = edit_text.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(ChessApp.sAppContext, R.string.input_name_info, Toast.LENGTH_SHORT).show();
            return;
        }
        if (sex == 0) {
            Toast.makeText(ChessApp.sAppContext, R.string.select_sex, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(ChessApp.sAppContext, R.string.select_birthday, Toast.LENGTH_SHORT).show();
            return;
        }
        String token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Log.i("tagtag", "token:::  " + token + "device::: " + device);
        mAction.updateInformation(username, birthday, sex, token, device, new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {
                SetPasswordActivity.start(IntroduceActivity.this);
            }

            @Override
            public void onFailed() {
                Toast.makeText(ChessApp.sAppContext, R.string.userinfo_update_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
