package com.netease.nim.uikit.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;

import java.util.List;

public class ContactsActivity extends FragmentActivity implements View.OnClickListener {
    private Fragment contactsFragment;
    private FrameLayout frameLayoutBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        frameLayoutBack = findViewById(R.id.frame_layout_back);
        frameLayoutBack.setOnClickListener(this);
        contactsFragment = new ContactsFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,contactsFragment).show(contactsFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, contactsFragment).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frame_layout_back) {
            finish();
        }
    }
}
