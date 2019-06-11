package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.BlackAction;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

public class ReportUserActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Activity activity, String token, String type) {
        Intent intent = new Intent();
        intent.setClass(activity, ReportUserActivity.class);
        intent.putExtra("other_token", token);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private BlackAction blackAction;
    private String userToken = "";
    private String device = "";
    private String token = "";
    private String type = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        blackAction = new BlackAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        Intent intent = getIntent();
        token = intent.getStringExtra("other_token");
        type = intent.getStringExtra("type");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
        initView();
    }

    private TextView text_view_submit, text_view_one, text_view_two,
            text_view_zero, text_view_ten, text_view_eleven, text_view_nine,
            text_view_eight, text_view_seven, text_view_six, text_view_five,
            text_view_fore, text_view_three, text_view_one_two, text_view_one_three, text_view_one_fore;
    private FrameLayout frame_layout_back;

    private void initView() {
        frame_layout_back = findViewById(R.id.frame_layout_back);
        text_view_one = findViewById(R.id.text_view_one);
        text_view_two = findViewById(R.id.text_view_two);
        text_view_three = findViewById(R.id.text_view_three);
        text_view_fore = findViewById(R.id.text_view_fore);
        text_view_five = findViewById(R.id.text_view_five);
        text_view_six = findViewById(R.id.text_view_six);
        text_view_seven = findViewById(R.id.text_view_seven);
        text_view_eight = findViewById(R.id.text_view_eight);
        text_view_nine = findViewById(R.id.text_view_nine);
        text_view_ten = findViewById(R.id.text_view_ten);
        text_view_eleven = findViewById(R.id.text_view_eleven);
        text_view_zero = findViewById(R.id.text_view_zero);
        text_view_one_two = findViewById(R.id.text_view_one_two);
        text_view_one_three = findViewById(R.id.text_view_one_three);
        text_view_one_fore = findViewById(R.id.text_view_one_fore);
        text_view_submit = findViewById(R.id.text_view_submit);

        text_view_one.setOnClickListener(this);
        text_view_two.setOnClickListener(this);
        text_view_three.setOnClickListener(this);
        text_view_fore.setOnClickListener(this);
        text_view_five.setOnClickListener(this);
        text_view_six.setOnClickListener(this);
        text_view_seven.setOnClickListener(this);
        text_view_eight.setOnClickListener(this);
        text_view_nine.setOnClickListener(this);
        text_view_ten.setOnClickListener(this);
        text_view_eleven.setOnClickListener(this);
        text_view_zero.setOnClickListener(this);
        text_view_one_two.setOnClickListener(this);
        text_view_one_three.setOnClickListener(this);
        text_view_one_fore.setOnClickListener(this);
        text_view_zero.setOnClickListener(this);
        text_view_zero.setOnClickListener(this);
        text_view_submit.setOnClickListener(this);
        frame_layout_back.setOnClickListener(this);

    }

    boolean one, two, three, fore, five, six, seven, eight, nine, ten, eleven, zero, oneTwo, oneThree, oneFore;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_layout_back:
                finish();
                break;
            case R.id.text_view_one:
                if (!one) {
                    text_view_one.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    one = true;
                } else {
                    text_view_one.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    one = false;
                }
                break;
            case R.id.text_view_two:
                if (!two) {
                    text_view_two.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    two = true;
                } else {
                    text_view_two.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    two = false;
                }
                break;
            case R.id.text_view_three:
                if (!three) {
                    text_view_three.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    three = true;
                } else {
                    text_view_three.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    three = false;
                }
                break;
            case R.id.text_view_fore:
                if (!fore) {
                    text_view_fore.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    fore = true;
                } else {
                    text_view_fore.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    fore = false;
                }
                break;
            case R.id.text_view_five:
                if (!five) {
                    text_view_five.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    five = true;
                } else {
                    text_view_five.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    five = false;
                }
                break;
            case R.id.text_view_six:
                if (!six) {
                    text_view_six.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    six = true;
                } else {
                    text_view_six.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    six = false;
                }
                break;
            case R.id.text_view_seven:
                if (!seven) {
                    text_view_seven.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    seven = true;
                } else {
                    text_view_seven.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    seven = false;
                }
                break;
            case R.id.text_view_eight:
                if (!eight) {
                    text_view_eight.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    eight = true;
                } else {
                    text_view_eight.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    eight = false;
                }
                break;
            case R.id.text_view_nine:
                if (!nine) {
                    text_view_nine.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    nine = true;
                } else {
                    text_view_nine.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    nine = false;
                }
                break;
            case R.id.text_view_ten:
                if (!ten) {
                    text_view_ten.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    ten = true;
                } else {
                    text_view_ten.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    ten = false;
                }
                break;
            case R.id.text_view_eleven:
                if (!eleven) {
                    text_view_eleven.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    eleven = true;
                } else {
                    text_view_eleven.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    eleven = false;
                }
                break;
            case R.id.text_view_zero:
                if (!zero) {
                    text_view_zero.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    zero = true;
                } else {
                    text_view_zero.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    zero = false;
                }
                break;
            case R.id.text_view_one_two:
                if (!oneTwo) {
                    text_view_one_two.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    oneTwo = true;
                } else {
                    text_view_one_two.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    oneTwo = false;
                }
                break;
            case R.id.text_view_one_three:
                if (!oneThree) {
                    text_view_one_three.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    oneThree = true;
                } else {
                    text_view_one_three.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    oneThree = false;
                }
                break;
            case R.id.text_view_one_fore:
                if (!oneFore) {
                    text_view_one_fore.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_press));
                    oneFore = true;
                } else {
                    text_view_one_fore.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawable_report_normal));
                    oneFore = false;
                }
                break;
            case R.id.text_view_submit:
                submitReportToService();
                break;
        }
    }

    /**
     * 提交举报的信息
     */
    private void submitReportToService() {
        String text = getTextString();
        blackAction.submitReportMessage(userToken, device, token, text, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                if (code == ApiCode.USER_TO_REPORT_SUCCESS) {
                    if (type != null && type.equals("otherInfo")) {
                        Intent intent = new Intent();
                        intent.setAction("com.yun.xiao.jing");
                        sendBroadcast(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(ApiCode.USER_TO_REPORT_SUCCESS_STRING);
                        sendBroadcast(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private String getTextString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(one ? R.string.report_one : "");
        stringBuilder.append(two ? R.string.report_two : "");
        stringBuilder.append(three ? R.string.report_three : "");
        stringBuilder.append(fore ? R.string.report_fore : "");
        stringBuilder.append(five ? R.string.report_five : "");
        stringBuilder.append(six ? R.string.report_six : "");
        stringBuilder.append(seven ? R.string.report_seven : "");
        stringBuilder.append(eight ? R.string.report_eight : "");
        stringBuilder.append(nine ? R.string.report_nine : "");
        stringBuilder.append(ten ? R.string.report_ten : "");
        stringBuilder.append(eleven ? R.string.report_elven : "");
        stringBuilder.append(zero ? R.string.report_zero : "");
        stringBuilder.append(oneTwo ? R.string.report_one_two : "");
        stringBuilder.append(oneThree ? R.string.report_one_three : "");
        stringBuilder.append(oneFore ? R.string.report_one_fore : "");
        return String.valueOf(stringBuilder);
    }
}
