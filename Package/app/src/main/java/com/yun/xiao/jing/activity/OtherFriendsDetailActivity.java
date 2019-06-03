package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.defineView.CircleTransform;
import com.yun.xiao.jing.util.DateTool;
import com.yun.xiao.jing.util.NumberTool;

import org.w3c.dom.Text;

public class OtherFriendsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frame_layout_back;
    private ImageView image_view_left;
    private TextView text_name, text_time, text_content,text_fans,text_selection,text_message;

    public static void start(Activity activity, FindInfoBean.InfoBean infoBean) {
        Intent intent = new Intent(activity, OtherFriendsDetailActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("infoBean",infoBean);
        intent.putExtra("infoBean", infoBean);
        activity.startActivity(intent);
    }

    FindInfoBean.InfoBean infoBean;
    private Drawable selectDrawable;
    private Drawable fansDrawable;
    private Drawable messageDrawable;
    private Drawable selectDrawableNormal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoBean = (FindInfoBean.InfoBean) getIntent().getSerializableExtra("infoBean");
        setContentView(R.layout.activity_friends_detail);
        initDrawable();
        initView();
        initData();
    }

    private void initDrawable() {
        selectDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_one);
        selectDrawableNormal = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_one_normal);
        fansDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_find_fans);
        messageDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_message_normal);
        selectDrawableNormal.setBounds(0, 0, 30, 30);
        selectDrawable.setBounds(0, 0, 30, 30);
        fansDrawable.setBounds(0, 0, 30, 30);
        messageDrawable.setBounds(0, 0, 30, 30);
    }


    private void initView() {
        frame_layout_back = findViewById(R.id.frame_layout_back);
        image_view_left = findViewById(R.id.image_view_left);
        text_name = findViewById(R.id.text_name);
        text_time = findViewById(R.id.text_time);
        text_content = findViewById(R.id.text_content);
        text_fans = findViewById(R.id.text_fans);
        text_selection = findViewById(R.id.text_selection);
        text_message = findViewById(R.id.text_message);


        frame_layout_back.setOnClickListener(this);

    }

    private void initData() {
        Picasso.with(this).load(infoBean.getHeadimg()).transform(new CircleTransform()).into(image_view_left);
        text_name.setText(infoBean.getUsername());
        text_time.setText(DateTool.stampToDate(String.valueOf(infoBean.getCreate_time())));
        text_content.setText(infoBean.getContent());

        text_fans.setText(NumberTool.intToString(String.valueOf(infoBean.getPraise())));
        text_selection.setText(NumberTool.intToString(String.valueOf(infoBean.getBrowse())));
        text_message.setText(NumberTool.intToString(String.valueOf(infoBean.getComments())));

        text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
        text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
        text_selection.setCompoundDrawables(selectDrawable, null, null, null);

        text_selection.setCompoundDrawablePadding(10);
        text_fans.setCompoundDrawablePadding(10);
        text_message.setCompoundDrawablePadding(10);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frame_layout_back) {
            finish();
        }
    }
}
