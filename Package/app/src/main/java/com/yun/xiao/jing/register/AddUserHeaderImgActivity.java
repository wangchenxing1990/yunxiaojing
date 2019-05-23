package com.yun.xiao.jing.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.interfaces.RequestCallback;

public class AddUserHeaderImgActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddUserHeaderImgActivity.class);
        activity.startActivity(intent);
    }
    private ImageView add_header_phone;
    private RegisterAction mAction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAction=new RegisterAction(this,null);
        setContentView(R.layout.activity_add_header_img);
        initView();
    }

    private void initView() {
        add_header_phone=findViewById(R.id.add_header_phone);
        add_header_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_header_phone:
                break;
        }
    }
    private void updateAddHeaderImg(){
        mAction.addHeaderImgInfo("","","","",new RequestCallback(){

            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }
}
