package com.yun.xiao.jing.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.FindInfoBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.defineView.CircleTransform;
import com.yun.xiao.jing.util.DateTool;
import com.yun.xiao.jing.util.NumberTool;
import com.yun.xiao.jing.util.ScreenUtil;

import java.util.List;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.FindViewHolder> {
    List<FindInfoBean.InfoBean> infoData;
    private String typeTwo = "";
    private Drawable selectDrawable;
    private Drawable fansDrawable;
    private Drawable messageDrawable;
    private Drawable selectDrawableNormal;

    public FindAdapter(List<FindInfoBean.InfoBean> info) {
        this.infoData = info;
        selectDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_one);
        selectDrawableNormal = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_one_normal);
        fansDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_find_fans);
        messageDrawable = ChessApp.sAppContext.getResources().getDrawable(R.drawable.icon_message_normal);
        selectDrawableNormal.setBounds(0, 0, 30, 30);
        selectDrawable.setBounds(0, 0, 30, 30);
        fansDrawable.setBounds(0, 0, 30, 30);
        messageDrawable.setBounds(0, 0, 30, 30);
    }

    @NonNull
    @Override
    public FindViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_information, viewGroup, false);
        FindViewHolder holder = new FindViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindViewHolder findViewHolder, int i) {
        Picasso
                .with(ChessApp.sAppContext)
                .load(infoData.get(i)
                        .getHeadimg())
                .transform(new CircleTransform())
                .into(findViewHolder.image_view_left);
        if (infoData.get(i).getSex() == 1) {
            findViewHolder.text_name.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.text_red_me));
        } else if (infoData.get(i).getSex() == 2) {
            findViewHolder.text_name.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.black_color));
        }
        findViewHolder.text_name.setText(infoData.get(i).getUsername());
        findViewHolder.text_time.setText(DateTool.stampToDate(String.valueOf(infoData.get(i).getCreate_time())));
        findViewHolder.text_content.setText(infoData.get(i).getContent());

        findViewHolder.text_fans.setText(NumberTool.intToString(String.valueOf(infoData.get(i).getPraise())));
        findViewHolder.text_selection.setText(NumberTool.intToString(String.valueOf(infoData.get(i).getBrowse())));
        findViewHolder.text_message.setText(NumberTool.intToString(String.valueOf(infoData.get(i).getComments())));
        if (typeTwo.equals("1")) {
            findViewHolder.text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.line_color));
            findViewHolder.text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
        } else if (typeTwo.equals("2")) {
            findViewHolder.text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.text_red_me));
            findViewHolder.text_selection.setCompoundDrawables(selectDrawable, null, null, null);
        } else {
            findViewHolder.text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.line_color));
            findViewHolder.text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
        }
        findViewHolder.text_selection.setCompoundDrawablePadding(10);
        findViewHolder.text_fans.setCompoundDrawablePadding(10);
        findViewHolder.text_message.setCompoundDrawablePadding(10);
//        if (infoData.get(i).getImages()){
//
//        }
        findViewHolder.recycler_view.setLayoutManager(new GridLayoutManager(ChessApp.sAppContext,3));

        findViewHolder.text_fans.setCompoundDrawables(fansDrawable, null, null, null);
        findViewHolder.text_message.setCompoundDrawables(messageDrawable, null, null, null);
    }

    @Override
    public int getItemCount() {
        return infoData.size();
    }

    public void updateData(List<FindInfoBean.InfoBean> info, boolean isMore) {

        if (isMore) {
            infoData.addAll(info);
        } else {
            this.infoData = info;
        }
        notifyDataSetChanged();
    }

    public void updateType(String type) {
        this.typeTwo = type;
        notifyDataSetChanged();
    }

    public class FindViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view_left;
        private TextView text_name, text_time, text_content, text_selection, text_fans, text_message, text_view_cancel;
        private RecyclerView recycler_view;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            image_view_left = itemView.findViewById(R.id.image_view_left);
            text_name = itemView.findViewById(R.id.text_name);
            text_time = itemView.findViewById(R.id.text_time);
            text_content = itemView.findViewById(R.id.text_content);
            text_selection = itemView.findViewById(R.id.text_selection);
            text_fans = itemView.findViewById(R.id.text_fans);
            text_message = itemView.findViewById(R.id.text_message);
            text_view_cancel = itemView.findViewById(R.id.text_view_cancel);

            recycler_view = itemView.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new GridLayoutManager(ChessApp.sAppContext,3));


//            ViewGroup.LayoutParams params = image_view_left.getLayoutParams();
//            params.height = ScreenUtil.px2dip(70);
//            params.width = ScreenUtil.px2dip(70);
//            image_view_left.setLayoutParams(params);

        }
    }
}
