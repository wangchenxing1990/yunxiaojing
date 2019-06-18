package com.yun.xiao.jing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.PictureBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.activity.OtherInformationActivity;
import com.yun.xiao.jing.activity.RecentlyLookActivity;
import com.yun.xiao.jing.adapter.PictureAdapter;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;
import com.yun.xiao.jing.util.ScreenUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment implements View.OnClickListener {
    public static Fragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            fragment = new ConversationFragment();
        }
        return fragment;
    }

    String device = "";
    String token = "";
    private FindAction mAction;
    private PictureAdapter adapter;
    private List<PictureBean.InfoBean> listData = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        device = UserPreferences.getDevice();
        token = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        Log.i("tokentokentoken", "token:::" + token);
        Log.i("device", "devicedevice:::" + device);
        adapter = new PictureAdapter(listData);
        mAction = new FindAction(getActivity(), null);
    }

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversation, container, false);
        return rootView;
    }

    private RecyclerView mRecyclerView;
    private ImageView imageView, image_view_left;
    private String sex = "0";
    private int p = 0;
    private String page = "10";
    private SmartRefreshLayout mSwipeRefresh;
    private TextView text_view_number;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefresh = rootView.findViewById(R.id.mSwipeRefresh);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        imageView = rootView.findViewById(R.id.image_view);
        image_view_left = rootView.findViewById(R.id.image_view_left);
        text_view_number = rootView.findViewById(R.id.text_view_number);

        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();//获取当前控件的布局对象
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(adapter);
        imageView.setOnClickListener(this);
        image_view_left.setOnClickListener(this);

        initSmartRefreshLayout();
        initAdapter();
        getData();
        getDataBrowse();//浏览记录
    }

    private void initSmartRefreshLayout() {
        mSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
                isLoadMore = false;
                p = 0;
                getData();
            }
        });
        mSwipeRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000/*,false*/);//传入false表示加载失败
                isLoadMore = true;
                p++;
                getData();
            }
        });
    }

    private void getDataBrowse() {
        String userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = UserPreferences.getDevice();
        mAction.getDataBrowse(userToken, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String info = jsonObject.getString("info");
                    if (info.equals("0")) {
                        text_view_number.setVisibility(View.INVISIBLE);
                    } else {
                        text_view_number.setText(info);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 点击进入到下一个界面
     */
    private boolean isLoadMore;

    private void initAdapter() {
        adapter.setOnPictureClickListener(new PictureAdapter.OnPictureClickListener() {
            @Override
            public void onPictureClick(String token) {
                submitBrowserCount(token);
            }
        });
    }

    /**
     * 提交浏览的次数
     */
    private void submitBrowserCount(final String toToken) {
        mAction.getBrowseCount(token, device, toToken, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                if (code == ApiCode.HOME_PAGE_BROWSING_SUCCESSFULLY) {
                    OtherInformationActivity.start(getActivity(), toToken);
                } else {
                    Toast.makeText(ChessApp.sAppContext, "Bad network. Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed() {
                Toast.makeText(ChessApp.sAppContext, "Bad network. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        mAction.getUserList(token, device, sex, String.valueOf(p + 1), page, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Gson gson = new Gson();
                PictureBean pictureBean = gson.fromJson(result, PictureBean.class);
                if (isLoadMore) {
                    adapter.updateData(pictureBean.getInfo(), true);
                } else {
                    adapter.updateData(pictureBean.getInfo(), false);
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
                RecentlyLookActivity.start(getActivity());
                break;
            case R.id.image_view:
                break;
        }
    }

    public void updateList() {
        adapter.updateDataNew();
    }
}
