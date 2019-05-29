package com.yun.xiao.jing.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MeInfoBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.FindAction;
import com.yun.xiao.jing.activity.EditInfoActivity;
import com.yun.xiao.jing.activity.SettingActivity;
import com.yun.xiao.jing.defineView.CircleTransform;
import com.yun.xiao.jing.defineView.HeadImageView;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import org.w3c.dom.Text;

public class MeFragment extends Fragment implements View.OnClickListener {
    public static Fragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            fragment = new MeFragment();
        }
        return fragment;
    }

    private View rootView;
    private FindAction mAction;
    private String userToken;
    private String device;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        mAction = new FindAction(getActivity(), null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        return rootView;
    }

    private RelativeLayout relative_layout_setting;
//    private HeadImageView iv_user_head;
    private ImageView iv_user_head;
    private FrameLayout frame_layout_setting;
    private ImageView image_view_edit_info;
    private TextView text_sex, text_name;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv_user_head = rootView.findViewById(R.id.iv_user_head);
        frame_layout_setting = rootView.findViewById(R.id.frame_layout_setting);
        image_view_edit_info = rootView.findViewById(R.id.image_view_edit_info);
        text_name = rootView.findViewById(R.id.text_name);
        text_sex = rootView.findViewById(R.id.text_sex);
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.icon_female);
        drawable.setBounds(0, 0, 20, 20);
        text_sex.setCompoundDrawables(drawable, null, null, null);
        iv_user_head.setOnClickListener(this);
        frame_layout_setting.setOnClickListener(this);
        image_view_edit_info.setOnClickListener(this);

        getDataInformation();//获取个人信息的接口
        getDataMeConcern();//获取关注量
        getDataFansConcern();
        getDataBrowseCount();
    }

    private void getDataFansConcern() {
        mAction.getDataFansConcern(userToken, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 获取关注量
     */
    private void getDataMeConcern() {
        mAction.getDataMeConcern(userToken, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void getDataInformation() {

        mAction.getDataInformation(userToken, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {
                Gson gson = new Gson();
                MeInfoBean meInfoBean = gson.fromJson(result, MeInfoBean.class);
                updateView(meInfoBean.getInfo());
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void getDataBrowseCount() {

        mAction.getDataBrowse(userToken, device, new RequestCallback() {
            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void updateView(MeInfoBean.InfoBean meInfoBean) {
        text_name.setText(meInfoBean.getUsername());
//        text_sex.setText(meInfoBean.get);
        if (meInfoBean.getSex() == 1) {

        } else if (meInfoBean.getSex() == 2) {

        }

        if (TextUtils.isEmpty(meInfoBean.getHeadimg())) {
            Picasso.with(ChessApp.sAppContext).load(R.mipmap.default_male_head).transform(new CircleTransform()).into(iv_user_head);
        } else {
            Picasso.with(ChessApp.sAppContext).load(meInfoBean.getHeadimg()).transform(new CircleTransform()).into(iv_user_head);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_head://点击修改头像
                break;
            case R.id.frame_layout_setting://点击进入设置界面
                SettingActivity.start(getActivity());
                break;
            case R.id.image_view_edit_info://修改信息
                EditInfoActivity.start(getActivity());
                break;
        }
    }
}
