package com.yun.xiao.jing.adapter;

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
import com.yun.xiao.jing.bean.RecentlyBean;
import com.yun.xiao.jing.defineView.CircleTransform;

import java.util.List;

public class RecentlyLookAdapter extends RecyclerView.Adapter<RecentlyLookAdapter.RecentlyViewHolder> {
    private List<RecentlyBean.InfoBean> info;

    public RecentlyLookAdapter(List<RecentlyBean.InfoBean> info) {
        this.info = info;
    }

    @NonNull
    @Override
    public RecentlyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recently_look, viewGroup, false);
        RecentlyViewHolder viewHolder = new RecentlyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewHolder viewHolder, int i) {
        if (info.size() >= 1) {
            Picasso.with(ChessApp.sAppContext).load(info.get(i).getHeadimg())
                    .placeholder(R.mipmap.default_male_head)
                    .error(R.mipmap.default_male_head)
                    .transform(new CircleTransform())
                    .into(viewHolder.image_view_user);
            viewHolder.text_user_name.setText(info.get(i).getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public void update(List<RecentlyBean.InfoBean> info) {
        this.info = info;
        notifyDataSetChanged();
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
        }
    }
}
