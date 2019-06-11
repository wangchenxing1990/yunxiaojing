package com.yun.xiao.jing.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.ParseObjectToHaspMap;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.BlackAction;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.activity.OtherFriendsDetailActivity;
import com.yun.xiao.jing.activity.ReportUserActivity;
import com.yun.xiao.jing.adapter.FindAdapter;
import com.yun.xiao.jing.defineView.MyPopuwindown;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment implements View.OnClickListener {
    private static Fragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            fragment = new FindFragment();
        }
        return fragment;
    }

    private FindAction mAction;
    private BlackAction mBlackAction;
    private String userToken;
    private String device;
    private String type = "1";
    private int p = 0;
    private String page = "10";
    private List<FindInfoBean> listData = new ArrayList();
    private FindAdapter findAdapter;
    private MyBroadCast myBroadCast;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        mAction = new FindAction(getActivity(), null);
        mBlackAction = new BlackAction(getActivity(), null);
        findAdapter = new FindAdapter(listData);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ApiCode.USER_TO_REPORT_SUCCESS_STRING);
        myBroadCast=new MyBroadCast();
        getActivity().registerReceiver(myBroadCast,intentFilter);
    }

    private View rootView;
    private RecyclerView mrecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find, container, false);
        return rootView;
    }

    private ImageView image_view_left, image_view_two, image_view_message;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String dynamic_id = "";
    private TextView text_view_empty;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout);
        mrecyclerView = rootView.findViewById(R.id.recycler_view);
        image_view_left = rootView.findViewById(R.id.image_view_left);
        image_view_two = rootView.findViewById(R.id.image_view_two);
        image_view_message = rootView.findViewById(R.id.image_view_message);
        text_view_empty = rootView.findViewById(R.id.text_view_empty);
        image_view_left.setOnClickListener(this);
        image_view_two.setOnClickListener(this);
        image_view_message.setOnClickListener(this);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(ChessApp.sAppContext));
        mrecyclerView.setAdapter(findAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFindInformation();
            }
        });

        findAdapter.setOnItemClckListener(new FindAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FindInfoBean infoBean) {
                OtherFriendsDetailActivity.start(getActivity(), infoBean);
            }
        });

        findAdapter.setOnCancelClickListener(new FindAdapter.OnCancleClickListener() {
            @Override
            public void onCancelClick(FindInfoBean findInfoBean, int position) {
                dynamic_id = String.valueOf(findInfoBean.getToken());
                showPopuwindowPhoto();
            }
        });

        mrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoadMore || mSwipeRefreshLayout.isRefreshing()) {
                    return;
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mrecyclerView.getLayoutManager();
                int firstVisibleItemPos = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPos = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && totalItemCount >= 1 && lastVisibleItemPos + 5 >= totalItemCount && (lastVisibleItemPos - firstVisibleItemPos) != totalItemCount) {
                    isLoadMore = true;
                    getDataFindInformation();
                    p++;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        getDataFindInformation();
    }

    private boolean isLoadMore;

    public void getDataFindInformation() {
        mAction.getFindDiscoveryData(userToken, device, type, String.valueOf(p), page, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                if (code == ApiCode.DYNAMIC_LIST_DATA) {
                    List<FindInfoBean> list = ParseObjectToHaspMap.testJackson(result);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        findAdapter.updateData(list, false, false);
                        mSwipeRefreshLayout.setRefreshing(false);
                        isLoadMore = false;
                    } else {
                        findAdapter.updateData(list, true, true);
                    }
                } else if (code == ApiCode.DYNAMIC_LIST_EMPTY) {
                    mSwipeRefreshLayout.setRefreshing(false);
//                    text_view_empty.setVisibility(View.INVISIBLE);
//                    mSwipeRefreshLayout.setVisibility(View.GONE);
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
            case R.id.image_view_left:
                image_view_left.setImageResource(R.mipmap.icon_find_left);
                image_view_two.setImageResource(R.mipmap.icon_select_normal);
                type = "1";
                findAdapter.updateType("1");
                break;
            case R.id.image_view_two:
                image_view_left.setImageResource(R.mipmap.icon_view_left_normal);
                image_view_two.setImageResource(R.mipmap.icon_selection_two);
                type = "2";
                findAdapter.updateType("2");
                break;
            case R.id.image_view_message:
                break;
            case R.id.text_take_photo://举报
                myPopuwindown.dismiss();
                ReportUserActivity.start(getActivity(), dynamic_id,"");
                break;
            case R.id.text_picture://屏蔽
                myPopuwindown.dismiss();
                EasyAlertDialogHelper.createOkCancelDiolag(getContext(), "", "你确定屏蔽该账号吗？", true, new EasyAlertDialogHelper.OnDialogActionListener() {

                    @Override
                    public void doCancelAction() {

                    }

                    @Override
                    public void doOkAction() {
                        blackUser();
                    }
                }).show();

                break;
            case R.id.text_cancel://取消
                myPopuwindown.dismiss();
                break;
        }
    }

    private void blackUser() {
        mBlackAction.gainBlackUserDynamic(userToken, device, dynamic_id, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Toast.makeText(ChessApp.sAppContext, "屏蔽用户动态成功", Toast.LENGTH_SHORT).show();
//                myPopuwindown.dismiss();
                getDataFindInformation();
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
        myPopuwindown = new MyPopuwindown(getActivity(), R.layout.popuwindown_photo);
        View view = myPopuwindown.getView();
        ColorDrawable dw = new ColorDrawable(0xaa000000);
        myPopuwindown.setBackgroundDrawable(dw);
        myPopuwindown.showAtLocation(rootView.findViewById(R.id.ll_popuwindd), Gravity.BOTTOM, 0, 0);
        myPopuwindown.setOutsideTouchable(false);

        myPopuwindown.setAnimationStyle(R.style.popwin_anim_style);
        TextView text_take_photo = view.findViewById(R.id.text_take_photo);
        TextView text_picture = view.findViewById(R.id.text_picture);
        TextView text_cancel = view.findViewById(R.id.text_cancel);

        text_take_photo.setText("举报用户");
        text_picture.setText("屏蔽用户");

        text_take_photo.setOnClickListener(this);
        text_picture.setOnClickListener(this);
        text_cancel.setOnClickListener(this);

    }

    class MyBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            getDataFindInformation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myBroadCast!=null){
            getActivity().unregisterReceiver(myBroadCast);
        }
    }
}
