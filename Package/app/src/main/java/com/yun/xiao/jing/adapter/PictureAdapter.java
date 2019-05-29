package com.yun.xiao.jing.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.PictureBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.util.ScreenUtil;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> {
    private List<PictureBean.InfoBean> listData;

    public PictureAdapter(List<PictureBean.InfoBean> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public PictureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureAdapter.MyViewHolder viewHolder, final int i) {
        Drawable drawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_female);
        drawable.setBounds(0, 0, 20, 20);
        int sex = listData.get(i).getSex();
        int age = listData.get(i).getAge();
        if (sex == 1) {
            viewHolder.text_sex.setText(String.valueOf(age));
            viewHolder.text_sex.setCompoundDrawables(drawable, null, null, null);
        } else if (sex == 2) {
            viewHolder.text_sex.setText(String.valueOf(age));
            viewHolder.text_sex.setCompoundDrawables(drawable, null, null, null);
        }

        viewHolder.text_name.setText(listData.get(i).getUsername());
        if (listData.get(i).getImages().size() != 0) {
            Picasso
                    .with(ChessApp.sAppContext)
                    .load(listData.get(i).getImages().get(0).getImg_url())
                    .into(viewHolder.imageView);
        }

        viewHolder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPictureClick(listData.get(i).getToken());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void updateData(List<PictureBean.InfoBean> info, boolean isMore) {
        if (isMore) {//是上拉加载更多
            listData.addAll(info);
        } else {//不是上拉加载更多
            this.listData.clear();
            this.listData = info;
        }
        notifyDataSetChanged();
    }

    private OnPictureClickListener listener;

    public void setOnPictureClickListener(OnPictureClickListener listener) {
        this.listener = listener;
    }

    public interface OnPictureClickListener {
        void onPictureClick(String token);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relative_layout;
        private ImageView imageView;
        private TextView text_name, text_sex;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            imageView = itemView.findViewById(R.id.image_view_photo);
            text_sex = itemView.findViewById(R.id.text_sex);
            text_name = itemView.findViewById(R.id.text_name);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();//获取当前控件的布局对象
            int width = ScreenUtil.getScreenWidth(ChessApp.sAppContext);
            params.width = width / 3;//设置当前控件布局的高度
            params.height = width / 2;//设置当前控件布局的高度
            imageView.setLayoutParams(params);//将设置好的布局参数应用到控件中
        }
    }
}
