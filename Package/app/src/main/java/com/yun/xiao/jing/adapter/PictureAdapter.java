package com.yun.xiao.jing.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PictureBean.InfoBean> listData;
    Drawable drawableMalse;
    Drawable drawableFemale;

    public PictureAdapter(List<PictureBean.InfoBean> listData) {
        drawableMalse = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_female);
        drawableFemale = ChessApp.sAppContext.getResources().getDrawable(R.mipmap.icon_male_normal);
        drawableMalse.setBounds(0, 0, 20, 20);
        drawableFemale.setBounds(0, 0, 20, 20);
        this.listData = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    private int position = -1;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof MyViewHolder) {
            int sex = listData.get(i).getSex();
            int age = listData.get(i).getAge();
            if (sex == 1) {
                Log.i("sexsexsex", "sexsex:::::" + sex);
                ((MyViewHolder) viewHolder).text_sex.setText(String.valueOf(age));
                ((MyViewHolder) viewHolder).text_sex.setCompoundDrawables(drawableMalse, null, null, null);
            } else if (sex == 2) {
                ((MyViewHolder) viewHolder).text_sex.setText(String.valueOf(age));
                ((MyViewHolder) viewHolder).text_sex.setCompoundDrawables(drawableFemale, null, null, null);
            }

            ((MyViewHolder) viewHolder).text_name.setText(listData.get(i).getUsername());
            if (listData.get(i).getImages().size() != 0) {
                Picasso
                        .with(ChessApp.sAppContext)
                        .load(listData.get(i).getImages().get(0).getImg_url())
                        .into(((MyViewHolder) viewHolder).imageView);
            }

            ((MyViewHolder) viewHolder).relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = i;
                    if (listener != null) {
                        listener.onPictureClick(listData.get(i).getToken());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void updateData(List<PictureBean.InfoBean> info, boolean isMore) {
        if (isMore) {//是上拉加载更多
            listData.addAll(info);
        } else {//不是上拉加载更多
            listData.clear();
            listData = info;
        }
        notifyDataSetChanged();
    }

    public void updateDataNew() {
        if (listData.size() != 0 && position != -1) {
            listData.remove(position);
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
