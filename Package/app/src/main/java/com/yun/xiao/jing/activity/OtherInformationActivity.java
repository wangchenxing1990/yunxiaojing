package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.InfoBeanTwo;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.adapter.FindAdapter;
import com.yun.xiao.jing.defineView.BannerViewPager;
import com.yun.xiao.jing.defineView.DiscoveryBannerAdapter;
import com.yun.xiao.jing.defineView.DiscoveryBannerIndicator;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OtherInformationActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity, String token) {
        Intent intent = new Intent(activity, OtherInformationActivity.class);
        intent.putExtra("token", token);
        activity.startActivity(intent);
    }

    private String token = "";
    private FindAction findAction;
    private ImageView iamge_view_left, image_view_right;
    private ViewPager image_view_title;
    private TextView text_view_name, text_view_age, text_view_height, text_view_bachelor, text_view_some_time, text_school_name;
    private ImageView image_selection, image_message;
    private RecyclerView recycler_view;
    public static final int INTERVAL = 4000;//每4秒轮播一次
    private Handler autoNextHandler;
    private boolean mDelayAutoNext = false;
    private List<BannerItem> mNoticeData = new ArrayList<BannerItem>();

    private void initAutoNextHandler(Collection<? extends Object> data) {
        if (data == null || data.size() <= 1) {
            if (autoNextHandler != null) {
                autoNextHandler.removeMessages(0);
            }
            autoNextHandler = null;
            return;
        }
        if (autoNextHandler == null) {
            autoNextHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (!mDelayAutoNext) {
                        if (view_pager_auto_notice.isBeingTouched()) {
                            // 按中时banner不轮播，什么都不做do nothing
                        } else {
                            int nextIndex = view_pager_auto_notice.currentBannerIndex + 1;
                            view_pager_auto_notice.setCurrentItem(nextIndex, true);
                        }
                    }
                    if (autoNextHandler != null) {
                        autoNextHandler.sendEmptyMessageDelayed(0, INTERVAL);
                    }
                }
            };
            autoNextHandler.sendEmptyMessageDelayed(0, INTERVAL);
            view_pager_auto_notice.setAutoNextHandler(autoNextHandler);
        }
    }

    String userToken = "";
    String device = "";
    private List<FindInfoBean.InfoBean> listData = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getIntent().getStringExtra("token");
        findAction = new FindAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        setContentView(R.layout.activity_other_information);
        findAdapter = new FindAdapter(listData);
        initView();//初始化view
        getOtherFriendData();
        getDataFindInformation();
    }

    DiscoveryBannerIndicator discovery_banner_indicator;
    BannerViewPager view_pager_auto_notice;
    SwipeRefreshLayout mSwipeRefresh;

    private void initView() {
        View view_pager_container = findViewById(R.id.view_pager_container);
        discovery_banner_indicator = findViewById(R.id.discovery_banner_indicator);
        view_pager_auto_notice = findViewById(R.id.view_pager_auto_notice);
        view_pager_auto_notice.customIndicator(discovery_banner_indicator);

        iamge_view_left = findViewById(R.id.iamge_view_left);
        image_view_right = findViewById(R.id.image_view_right);
        text_view_name = findViewById(R.id.text_view_name);

        text_view_age = findViewById(R.id.text_view_age);
        text_view_height = findViewById(R.id.text_view_height);
        text_view_bachelor = findViewById(R.id.text_view_bachelor);
        text_view_some_time = findViewById(R.id.text_view_some_time);
        text_school_name = findViewById(R.id.text_school_name);
        image_selection = findViewById(R.id.image_selection);
        image_message = findViewById(R.id.image_message);
        mSwipeRefresh = findViewById(R.id.mSwipeRefresh);
        recycler_view = findViewById(R.id.recycler_view);

        iamge_view_left.setOnClickListener(this);
        image_view_right.setOnClickListener(this);
        image_selection.setOnClickListener(this);
        image_message.setOnClickListener(this);

        recycler_view.setLayoutManager(new LinearLayoutManager(ChessApp.sAppContext));
        recycler_view.setAdapter(findAdapter);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFindInformation();
            }
        });
    }
    InfoBeanTwo infoBeanTwo;
    private void getOtherFriendData() {
        Log.i("wangyukui1990", "usertoken:::::" + userToken + "   device::::" + device + "    token:::::" + token);
        findAction.getOtherInfo(userToken, device, token, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Log.i("wangyukui1990", result);
                Gson gson = new Gson();
                infoBeanTwo= gson.fromJson(result, InfoBeanTwo.class);
                updateView(infoBeanTwo);//更新view的数据
            }

            @Override
            public void onFailed() {

            }
        });
    }

    DiscoveryBannerAdapter noticeAdapter;
    private FindAdapter findAdapter;

    private void updateView(InfoBeanTwo infoBeanTwo) {
        text_view_name.setText(infoBeanTwo.getInfo().getUsername());
        Drawable drawable = getResources().getDrawable(R.drawable.icon_female);
        drawable.setBounds(0, 0, 20, 20);
        text_view_age.setCompoundDrawables(drawable, null, null, null);

        text_view_age.setText(String.valueOf(infoBeanTwo.getInfo().getAge()));
        if (infoBeanTwo.getInfo().getFocus_on().equals("no")) {
            image_selection.setImageResource(R.mipmap.icon_selection);
        } else if (infoBeanTwo.getInfo().getFocus_on().equals("yes")) {
            image_message.setImageResource(R.mipmap.icon_sellection_other);
            image_message.setClickable(false);
        }
        for (int i = 0; i < infoBeanTwo.getInfo().getImages().size(); i++) {
            BannerItem item = new BannerItem();
            item.picUrl = infoBeanTwo.getInfo().getImages().get(i).getImg_url();
            item.href = infoBeanTwo.getInfo().getImages().get(i).getImg_url();
            mNoticeData.add(item);
        }
        discovery_banner_indicator.setNumOfCircles(mNoticeData.size());
        noticeAdapter = new DiscoveryBannerAdapter(this, this);
        noticeAdapter.addAll(mNoticeData);
        view_pager_auto_notice.setAdapter(noticeAdapter);
        if (mNoticeData.size() == 0) {
//            view_pager_auto_notice.setCurrentItem(mNoticeData.size() == 1 ? 0 : 500);
        } else {
            view_pager_auto_notice.setCurrentItem(mNoticeData.size() == 1 ? 0 : 500);
        }

        initAutoNextHandler(mNoticeData);
//        text_view_height.setText(infoBeanTwo.getInfo().get());
    }

    private String type = "1";
    private String p = "0";
    private String page = "10";

    private void getDataFindInformation() {
        findAction.getFindDiscoveryData(userToken, device, type, p, page, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Gson gson = new Gson();
                FindInfoBean findInfoBean = gson.fromJson(result, FindInfoBean.class);
                if (mSwipeRefresh.isRefreshing()) {
                    findAdapter.updateData(findInfoBean.getInfo(), false);
                    mSwipeRefresh.setRefreshing(false);
                } else {
                    findAdapter.updateData(findInfoBean.getInfo(), true);
                }
            }

            @Override
            public void onFailed() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iamge_view_left:
                finish();
                break;
            case R.id.image_view_right:
                break;
            case R.id.image_message:
                break;
            case R.id.image_selection://点击收藏
                Toast.makeText(OtherInformationActivity.this,"ssssss", Toast.LENGTH_SHORT).show();
                submitSelectUser();
                break;
        }
    }

    /**
     * 点击收藏
     */
    private void submitSelectUser() {
        Log.i("TAGTAG","userToken:::"+userToken+"device:::::"+device+"token::::"+infoBeanTwo.getInfo().getToken());
        findAction.submitSelectUserService(userToken,device,infoBeanTwo.getInfo().getToken(),new RequestCallback(){

            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    public static class BannerItem implements Serializable {
        public String picUrl;
        public String href;

        @Override
        public String toString() {
            return "BannerItem{" +
                    "picUrl='" + picUrl + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }
}
