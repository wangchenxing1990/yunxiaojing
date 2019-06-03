package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.adapter.RecentlyLookAdapter;
import com.yun.xiao.jing.bean.RecentlyBean;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.register.IntroduceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RecentlyLookActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RecentlyLookActivity.class);
        activity.startActivity(intent);
    }

    private FindAction findAction;
    String userToken = "";
    String device = "";
    private String p = "0";
    private String page = "10";
    private RecentlyLookAdapter adapter;
    private List<RecentlyBean.InfoBean> list = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        findAction = new FindAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        adapter = new RecentlyLookAdapter(list);
        setContentView(R.layout.activity_recently_look);
        initView();
        initData();//初始化数据
    }

    private FrameLayout relative_layout;
    private TextView text_title;
    private RecyclerView recycler_view;

    private void initView() {
        relative_layout = findViewById(R.id.frame_layout_back);
        text_title = findViewById(R.id.text_title);
        recycler_view = findViewById(R.id.recycler_view);
        relative_layout.setOnClickListener(this);
        recycler_view.setLayoutManager(new LinearLayoutManager(ChessApp.sAppContext));
        recycler_view.setAdapter(adapter);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        findAction.gainUserPageBrowseList(userToken, device, p, page, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Gson gson = new Gson();
                RecentlyBean recentlyBean = gson.fromJson(result, RecentlyBean.class);
                adapter.update(recentlyBean.getInfo());
            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_layout_back:
                finish();
                break;
        }
    }
}
