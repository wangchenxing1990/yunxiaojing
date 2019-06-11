package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.contact.ContactChangedObserver;
import com.netease.nim.uikit.api.model.main.OnlineStateChangeObserver;
import com.netease.nim.uikit.api.model.team.TeamDataChangedObserver;
import com.netease.nim.uikit.api.model.team.TeamMemberDataChangedObserver;
import com.netease.nim.uikit.api.model.user.UserInfoObserver;
import com.netease.nim.uikit.business.recent.TeamMemberAitHelper;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.badger.Badger;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.InfoBeanTwo;
import com.yun.xiao.jing.ParseObjectToHaspMap;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.BlackAction;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.adapter.FindAdapter;
import com.yun.xiao.jing.defineView.BannerViewPager;
import com.yun.xiao.jing.defineView.DiscoveryBannerAdapter;
import com.yun.xiao.jing.defineView.DiscoveryBannerIndicator;
import com.yun.xiao.jing.defineView.MyPopuwindown;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private String userToken = "";
    private String device = "";
    private List<FindInfoBean> listData = new ArrayList();
    private BlackAction blackAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        token = getIntent().getStringExtra("token");
        findAction = new FindAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        blackAction = new BlackAction(this, null);
        setContentView(R.layout.activity_other_information);
//        showPopuwindowPhoto();
        findAdapter = new FindAdapter(listData);
        initView();//初始化view
        getOtherFriendData();
        getDataFindInformation();
        registerObservers(true);
        registerDropCompletedListener(true);
        registerOnlineStateChangeListener(true);
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
                infoBeanTwo = gson.fromJson(result, InfoBeanTwo.class);
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
            image_selection.setImageResource(R.mipmap.icon_sellection_other);
            image_selection.setClickable(false);
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
                List<FindInfoBean> findInfoBean = ParseObjectToHaspMap.testJackson(result);
                if (mSwipeRefresh.isRefreshing()) {
                    findAdapter.updateData(findInfoBean, false, false);
                    mSwipeRefresh.setRefreshing(false);
                } else {
                    findAdapter.updateData(findInfoBean, true, false);
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
                showPopuwindowPhoto();
                break;
            case R.id.image_message:
                if (!TextUtils.isEmpty(infoBeanTwo.getInfo().getImaccount())) {
                    createUserSessionInfo();
                }
                break;
            case R.id.image_selection://点击收藏
                submitSelectUser();
                break;
            case R.id.text_take_photo://举报用户
                ReportUserActivity.start(this, token, "otherInfo");
                break;
            case R.id.text_picture://拉黑用户
                myPopuwindown.dismiss();
                blackUserToService();
                break;
            case R.id.text_cancel://取消
                myPopuwindown.dismiss();
                break;
        }
    }

    /**
     * 拉黑用户向服务器
     */
    private void blackUserToService() {
        EasyAlertDialogHelper.createOkCancelDiolag(this, "", "你确定拉黑该用户吗？", true, new EasyAlertDialogHelper.OnDialogActionListener() {

            @Override
            public void doCancelAction() {

            }

            @Override
            public void doOkAction() {
                blackUser();
            }
        }).show();
    }

    /**
     * 添加好友向云信
     */
    private void addFriends() {
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(infoBeanTwo.getInfo().getImtoken(), VerifyType.DIRECT_ADD, ""))
                .setCallback(new com.netease.nimlib.sdk.RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        NimUIKit.startP2PSession(OtherInformationActivity.this, infoBeanTwo.getInfo().getImtoken().toLowerCase(), infoBeanTwo.getInfo().getUsername());
                    }

                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                });
    }

    /**
     * 告诉自己的服务器添加的好友
     */
    private void createUserSessionInfo() {
        blackAction.createUserSession(userToken, device, infoBeanTwo.getInfo().getToken(), new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {
                if (code == ApiCode.ESTABLISHMENT_ALREADY_FRIENDS) {
                    NimUIKit.startP2PSession(OtherInformationActivity.this, infoBeanTwo.getInfo().getImaccount().toLowerCase(), infoBeanTwo.getInfo().getUsername());
                } else if (code == ApiCode.ESTABLISHMENT_OF_IS_FRIENDSHIP) {
                    addFriends();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 拉黑用户
     */
    private void blackUser() {
        blackAction.otherBlackUser(userToken, device, token, "1", "no", new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Intent intent = new Intent();
                intent.setAction("com.yun.xiao.jing");
                sendBroadcast(intent);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 弹出修改头像的对话框
     */
    MyPopuwindown myPopuwindown;

    private void showPopuwindowPhoto() {
        myPopuwindown = new MyPopuwindown(this, R.layout.popuwindown_photo);
        View view = myPopuwindown.getView();
        ColorDrawable dw = new ColorDrawable(0xaa000000);
        myPopuwindown.setBackgroundDrawable(dw);
        myPopuwindown.showAtLocation(findViewById(R.id.ll_popuwindd), Gravity.BOTTOM, 0, 0);
        myPopuwindown.setOutsideTouchable(false);

        myPopuwindown.setAnimationStyle(R.style.popwin_anim_style);
        TextView text_take_photo = view.findViewById(R.id.text_take_photo);
        TextView text_picture = view.findViewById(R.id.text_picture);
        TextView text_cancel = view.findViewById(R.id.text_cancel);

        text_take_photo.setText("举报用户");
        text_picture.setText("拉黑用户");

        text_take_photo.setOnClickListener(this);
        text_picture.setOnClickListener(this);
        text_cancel.setOnClickListener(this);

    }

    /**
     * 点击收藏
     */
    private void submitSelectUser() {
        Log.i("TAGTAG", "userToken:::" + userToken + "device:::::" + device + "token::::" + infoBeanTwo.getInfo().getToken());
        findAction.submitSelectUserService(userToken, device, infoBeanTwo.getInfo().getToken(), new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {
                image_selection.setImageResource(R.mipmap.icon_sellection_other);
                Intent intent = new Intent();
                intent.setAction("com.yun.xiao.jing.other");
                sendBroadcast(intent);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        registerDropCompletedListener(false);
        registerOnlineStateChangeListener(false);
    }

    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        NimUIKitImpl.getOnlineStateChangeObservable().registerOnlineStateChangeListeners(onlineStateChangeObserver, register);
    }

    OnlineStateChangeObserver onlineStateChangeObserver = new OnlineStateChangeObserver() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
//            notifyDataSetChanged();
        }
    };

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(messageReceiverObserver, register);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);

        registerTeamUpdateObserver(register);
        registerTeamMemberUpdateObserver(register);
        NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }

    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            NimUIKit.getUserInfoObservable().registerObserver(userInfoObserver, false);
        }
    }

    private void refreshMessages(boolean unreadChanged) {
//        sortRecentContacts(items);
//        notifyDataSetChanged();
//
//        if (unreadChanged) {
//
//            // 方式一：累加每个最近联系人的未读（快）
//
//            int unreadNum = 0;
//            for (RecentContact r : items) {
//                unreadNum += r.getUnreadCount();
//            }
//
//            // 方式二：直接从SDK读取（相对慢）
//            //int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
//
//            if (callback != null) {
//                callback.onUnreadCountChange(unreadNum);
//            }

//            Badger.updateBadgerCount(unreadNum);
//        }
    }

    ContactChangedObserver friendDataChangedObserver = new ContactChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            refreshMessages(false);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            refreshMessages(false);
        }
    };

    private UserInfoObserver userInfoObserver;

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
//                    refreshMessages(false);
                }
            };
        }
        NimUIKit.getUserInfoObservable().registerObserver(userInfoObserver, true);
    }

    TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
//            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeamMember(List<TeamMember> member) {

        }
    };

    private void registerTeamMemberUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver, register);
    }

    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
//            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    /**
     * 注册群信息&群成员更新监听
     */
    private void registerTeamUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
    }

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
//            if (recentContact != null) {
//                for (RecentContact item : items) {
//                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
//                            && item.getSessionType() == recentContact.getSessionType()) {
//                        items.remove(item);
//                        refreshMessages(true);
//                        break;
//                    }
//                }
//            } else {
//                items.clear();
//                refreshMessages(true);
//            }
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
//            int index = getItemIndex(message.getUuid());
////            if (index >= 0 && index < items.size()) {
////                RecentContact item = items.get(index);
////                item.setMsgStatus(message.getStatus());
////                refreshViewHolderByIndex(index);
////            }
        }
    };

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            if (!DropManager.getInstance().isTouchable()) {
                // 正在拖拽红点，缓存数据
                for (RecentContact r : recentContacts) {
//                    cached.put(r.getContactId(), r);
                }

                return;
            }

//            onRecentContactChanged(recentContacts);
        }
    };

    // 暂存消息，当RecentContact 监听回来时使用，结束后清掉
    private Map<String, Set<IMMessage>> cacheMessages = new HashMap<>();
    //监听在线消息中是否有@我
    private Observer<List<IMMessage>> messageReceiverObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> imMessages) {
            if (imMessages != null) {
                for (IMMessage imMessage : imMessages) {
                    if (!TeamMemberAitHelper.isAitMessage(imMessage)) {
                        continue;
                    }
                    Set<IMMessage> cacheMessageSet = cacheMessages.get(imMessage.getSessionId());
                    if (cacheMessageSet == null) {
                        cacheMessageSet = new HashSet<>();
                        cacheMessages.put(imMessage.getSessionId(), cacheMessageSet);
                    }
                    cacheMessageSet.add(imMessage);
                }
            }
        }
    };

    private void registerDropCompletedListener(boolean register) {
        if (register) {
//            DropManager.getInstance().addDropCompletedListener(dropCompletedListener);
        } else {
//            DropManager.getInstance().removeDropCompletedListener(dropCompletedListener);
        }
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
