package com.yun.xiao.jing.adapter;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.defineView.CircleTransform;
import com.yun.xiao.jing.util.ScreenUtil;

public class RecentlyLookAdapter extends RecyclerView.Adapter<RecentlyLookAdapter.RecentlyViewHolder> {
    @NonNull
    @Override
    public RecentlyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recently_look, viewGroup, false);
        RecentlyViewHolder viewHolder = new RecentlyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewHolder viewHolder, int i) {
//        Picasso.with(ChessApp.sAppContext).load("").transform(new CircleTransform()).into(viewHolder.image_view_user);
//        viewHolder.text_user_name.setText("");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class RecentlyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view_user;
        private TextView text_user_name;
        private RelativeLayout relative_layout;

        public RecentlyViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            image_view_user = itemView.findViewById(R.id.image_view_user);
            text_user_name = itemView.findViewById(R.id.text_user_name);
            ViewGroup.LayoutParams layoutParams = relative_layout.getLayoutParams();
            layoutParams.width = relative_layout.getWidth();
            layoutParams.height = ScreenUtil.px2dip(109);
            relative_layout.setLayoutParams(layoutParams);

            ViewGroup.LayoutParams imageViewParams = image_view_user.getLayoutParams();
            imageViewParams.height = ScreenUtil.px2dip(70);
            imageViewParams.width = ScreenUtil.px2dip(70);
            image_view_user.setLayoutParams(imageViewParams);
        }
    }
}
