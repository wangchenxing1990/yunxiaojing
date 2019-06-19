package com.yun.xiao.jing;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.plugin.interact.IMixPushInteract;
import com.yun.xiao.jing.activity.PostInfoActivity;
import com.yun.xiao.jing.defineView.HomeTabItem;
import com.yun.xiao.jing.defineView.HomeTabLayout;
import com.yun.xiao.jing.fragment.ConversationFragment;
import com.yun.xiao.jing.fragment.FindFragment;
import com.yun.xiao.jing.fragment.MeFragment;
import com.yun.xiao.jing.fragment.SettingFragment;
import com.yun.xiao.jing.register.IntroduceActivity;
import com.yun.xiao.jing.register.SetPasswordActivity;

public class MainActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, String params) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("params", params);
        activity.startActivity(intent);
    }

    private HomeTabItem discoveryTab;
    private HomeTabItem clubTab;
    private ImageView homeTab;
    private HomeTabItem recordTab;
    private HomeTabItem meTab;
    private ImageView image_view_home;
    private String params;
    MyBroadCast myBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = getIntent().getStringExtra("params");
        setContentView(R.layout.activity_main);
        image_view_home = findViewById(R.id.image_view_home);
        initHomeBottomTab();
        initFragments();
        image_view_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostInfoActivity.start(MainActivity.this);
            }
        });
        if (params != null && params.equals("postinfo")) {
            if (findFragment != null) {
                clubTab.performClick();
                FindFragment fragment = (FindFragment) findFragment;
                fragment.getDataFindInformation();
            } else {
                findFragment = FindFragment.newInstance();
                clubTab.performClick();
            }
        } else {
            discoveryTab.performClick();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yun.xiao.jing");
        myBroadCast = new MyBroadCast();
        registerReceiver(myBroadCast, intentFilter);
    }

    private void initFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (conversationFragment == null) {
            conversationFragment = ConversationFragment.newInstance();
            if (!conversationFragment.isAdded()) {
                ft.add(R.id.frame_content, conversationFragment, FRAGMENT_GAME).commit();
            }
        }
        mCurrentFragment = conversationFragment;
    }

    private Fragment conversationFragment;
    private Fragment findFragment;
    private Fragment settingFragment;
    private Fragment meFragment;
    private Fragment mCurrentFragment;
    final static String FRAGMENT_DISCOVERY = "DiscoveryFragment";
    final static String FRAGMENT_GAME = "GameFragmentNew";
    final static String FRAGMENT_RECORD = "RecordFragment";
    final static String FRAGMENT_ME = "MeFragment";
    final static String FRAGMENT_RECENTCONTACTS = "RecentContactsFragment";

    private void initHomeBottomTab() {
//        mOnlineStatusView = (OnlineStatusView) findViewById(R.id.mOnlineStatusView);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discoveryTab.setEnabled(v == discoveryTab ? false : true);
                clubTab.setEnabled(v == clubTab ? false : true);
//                homeTab.setEnabled(v == homeTab ? false : true);
//                homeTab.setEnabled(v == homeTab);
                recordTab.setEnabled(v == recordTab ? false : true);
                meTab.setEnabled(v == meTab ? false : true);
                if (v == discoveryTab) {
                    if (conversationFragment == null) {
                        conversationFragment = ConversationFragment.newInstance();
                    }
                    changeFragment(conversationFragment, FRAGMENT_DISCOVERY);
                } else if (v == clubTab) {
//                    if (findFragment == null) {
                        findFragment = new FindFragment();
//                    }
                    changeFragment(findFragment, FRAGMENT_RECENTCONTACTS);
                } else if (v == homeTab) {
                    Log.i("11111111111", "1441414444114");
                } else if (v == recordTab) {
                    if (settingFragment == null) {
                        settingFragment = new RecentContactsFragment();
                    }
                    changeFragment(settingFragment, FRAGMENT_RECORD);
                } else if (v == meTab) {
                    if (meFragment == null) {
                        meFragment = MeFragment.newInstance();
                    }
                    changeFragment(meFragment, FRAGMENT_ME);
                }
            }
        };
        HomeTabLayout home_tab_layout = findViewById(R.id.home_tab_layout);
        //发现
        discoveryTab = (HomeTabItem) home_tab_layout.getChildAt(0);
        discoveryTab.setOnClickListener(clickListener);
        //对话
        clubTab = (HomeTabItem) home_tab_layout.getChildAt(1);
        clubTab.setOnClickListener(clickListener);
        //发表说说
        homeTab = home_tab_layout.findViewById(R.id.item_data);
        homeTab.setImageResource(R.drawable.home_tab_game);
        homeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostInfoActivity.start(MainActivity.this);
            }
        });
        //
        recordTab = (HomeTabItem) home_tab_layout.getChildAt(3);
        recordTab.setOnClickListener(clickListener);
        //设置
        meTab = (HomeTabItem) home_tab_layout.getChildAt(4);
        meTab.setOnClickListener(clickListener);
        //会话
        discoveryTab.setResources(R.string.main_tab_discovery, R.drawable.home_tab_discovery);
        //对话
        clubTab.setResources(R.string.club, R.drawable.home_tab_chat);
        //消息
        recordTab.setResources(R.string.main_tab_record, R.drawable.home_tab_contacts);
        //设置
        meTab.setResources(R.string.more, R.drawable.home_tab_me);
    }

    public void changeFragment(Fragment fragment, String tag) {
        if (fragment == mCurrentFragment) {
            //如果选择的是当前的，不进行任何操作
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().executePendingTransactions();//虽然已经add过了，但是是异步的，因此Fragment.isAdded()=false
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        mCurrentFragment = fragment;
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(R.id.frame_content, fragment, tag);
        }
//            ft.addToBackStack("");
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myBroadCast != null) {
            unregisterReceiver(myBroadCast);
        }
    }

    class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (conversationFragment != null) {
                ((ConversationFragment) conversationFragment).updateList();
            }
        }
    }
}
