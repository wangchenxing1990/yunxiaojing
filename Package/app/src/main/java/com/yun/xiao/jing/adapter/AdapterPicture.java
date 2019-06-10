package com.yun.xiao.jing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.bean.PictureUrlBean;

import java.util.ArrayList;
import java.util.List;

public class AdapterPicture extends RecyclerView.Adapter<AdapterPicture.MyPictureViewHolder> {
    private List<PictureUrlBean> list = new ArrayList<>();

    public AdapterPicture(List<PictureUrlBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyPictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_view, viewGroup, false);
        MyPictureViewHolder viewHolder = new MyPictureViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPictureViewHolder myPictureViewHolder, int i) {
//        RequestOptions requestOptions = new RequestOptions()
//                .centerCrop()
//                .placeholder(defaultResId)
//                .error(defaultResId)
//                .override(thumbSize, thumbSize);
        Glide.with(ChessApp.sAppContext).asBitmap()
                .load(list.get(i).getThumb())
//                .apply(requestOptions)
                .into(myPictureViewHolder.image_view);
//        Glide.with(ChessApp.sAppContext).load(list.get(i).getThumb()).into(myPictureViewHolder.image_view);
//        Picasso.with(ChessApp.sAppContext).load(list.get(i).getOrigin()).into(myPictureViewHolder.image_view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setUpdataData(List<PictureUrlBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyPictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view;

        public MyPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
        }
    }
}
