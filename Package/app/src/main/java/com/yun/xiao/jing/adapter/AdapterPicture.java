package com.yun.xiao.jing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yun.xiao.jing.R;

public class AdapterPicture extends RecyclerView.Adapter<AdapterPicture.MyPictureViewHolder> {
    public AdapterPicture() {

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyPictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view;

        public MyPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view = itemView.findViewById(R.id.image_view);
        }
    }
}
