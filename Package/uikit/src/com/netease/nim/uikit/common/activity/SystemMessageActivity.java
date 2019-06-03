package com.netease.nim.uikit.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.adapter.SystemAdaper;

public class SystemMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frameBack;
    private RecyclerView recyclerView;
    private SystemAdaper adaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaper = new SystemAdaper();
        setContentView(R.layout.activity_system_messge);
        initView();//初始化view
    }

    private void initView() {
        frameBack = findViewById(R.id.frame_layout_back);
        recyclerView = findViewById(R.id.recycler_view);
        frameBack.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaper);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frame_layout_back) {
            finish();
        }
    }
}
