package com.yun.xiao.jing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FindFragment extends Fragment {
    private static Fragment fragment = null;

    public static Fragment newInstance() {
        if (fragment == null) {
            fragment = new FindFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(getActivity());
        textView.setText("FindFragmentFindFragmentFindFragmentFindFragment");
        textView.setTextSize(20);
        return textView;
    }

}