package com.yun.xiao.jing;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    private HomeTabItem discoveryTab;
    private HomeTabItem clubTab;
    private ImageView homeTab;
    private HomeTabItem recordTab;
    private HomeTabItem meTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHomeBottomTab();
        initFragments();
    }
    private void initFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (conversationFragment == null) {
            conversationFragment = ConversationFragment.newInstance();
            if (!conversationFragment.isAdded()) {
                ft.add(R.id.frame_content, conversationFragment, FRAGMENT_GAME).commit();
            }
        }
//        ft.commitAllowingStateLoss();
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
                homeTab.setEnabled(v == homeTab ? false : true);
                recordTab.setEnabled(v == recordTab ? false : true);
                meTab.setEnabled(v == meTab ? false : true);
                if (v == discoveryTab) {
                    if (conversationFragment == null) {
                        conversationFragment =  ConversationFragment.newInstance();
                    }
                    changeFragment(conversationFragment, FRAGMENT_DISCOVERY);
                } else if (v == clubTab) {
                    if (findFragment == null) {
                        findFragment=  FindFragment.newInstance();
                    }
                    changeFragment(findFragment, FRAGMENT_RECENTCONTACTS);
                } else if (v == homeTab) {
//                    if (mCurrentFragment != null && mGameFragment != null && mCurrentFragment == mGameFragment) {
////                        mGameFragment.onBackPressed();
//                    } else {
//                        if (mGameFragment == null) {
//                            mGameFragment = GameFragmentNew.newInstance();
//                        }
//                        changeFragment(mGameFragment, FRAGMENT_GAME);
//                    }
                } else if (v == recordTab) {
                    if (settingFragment == null) {
                        settingFragment =  SettingFragment.newInstance();
                    }
                    changeFragment(settingFragment, FRAGMENT_RECORD);
                } else if (v == meTab) {
                    if (meFragment == null) {
                        meFragment =  MeFragment.newInstance();
                    }
                    changeFragment(meFragment, FRAGMENT_ME);
                }
            }
        };
        HomeTabLayout home_tab_layout =  findViewById(R.id.home_tab_layout);
        //发现
        discoveryTab = (HomeTabItem) home_tab_layout.getChildAt(0);
        discoveryTab.setOnClickListener(clickListener);
        //对话
        clubTab = (HomeTabItem) home_tab_layout.getChildAt(1);
        clubTab.setOnClickListener(clickListener);
        //发表说说
        homeTab =  home_tab_layout.findViewById(R.id.item_data);
        homeTab.setImageResource(R.drawable.home_tab_game);
        homeTab.setOnClickListener(clickListener);
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
}
