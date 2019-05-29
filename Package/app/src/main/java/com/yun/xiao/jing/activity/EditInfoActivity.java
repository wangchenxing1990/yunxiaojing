package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yun.xiao.jing.R;

import java.nio.channels.InterruptedByTimeoutException;

public class EditInfoActivity extends AppCompatActivity {
    public static void start(Activity activity){
        Intent intent=new Intent(activity,EditInfoActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
    }
}
