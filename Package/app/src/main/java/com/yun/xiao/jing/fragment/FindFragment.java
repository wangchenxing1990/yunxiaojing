package com.yun.xiao.jing.fragment;

import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.adapter.FindAdapter;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

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
    private String userToken;
    private String device;
    private String type = "1";
    private String p = "0";
    private String page = "10";
    private List<FindInfoBean.InfoBean> listData = new ArrayList();
    private FindAdapter findAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        mAction = new FindAction(getActivity(), null);
        findAdapter = new FindAdapter(listData);
    }

    private View rootView;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find, container, false);
        return rootView;
    }

    private ImageView image_view_left, image_view_two, image_view_message;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        image_view_left = rootView.findViewById(R.id.image_view_left);
        image_view_two = rootView.findViewById(R.id.image_view_two);
        image_view_message = rootView.findViewById(R.id.image_view_message);
        image_view_left.setOnClickListener(this);
        image_view_two.setOnClickListener(this);
        image_view_message.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChessApp.sAppContext));
        recyclerView.setAdapter(findAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFindInformation();
            }
        });
        getDataFindInformation();
    }

    private void getDataFindInformation() {
        mAction.getFindDiscoveryData(userToken, device, type, p, page, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Gson gson = new Gson();
                FindInfoBean findInfoBean = gson.fromJson(result, FindInfoBean.class);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    findAdapter.updateData(findInfoBean.getInfo(), false);
                    mSwipeRefreshLayout.setRefreshing(false);
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
        }
    }
}
