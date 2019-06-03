package com.yun.xiao.jing.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FindInfoBean.InfoBean> infoData;
    private String typeTwo = "";
    private Drawable selectDrawable;
    private Drawable fansDrawable;
    private Drawable messageDrawable;
    private Drawable selectDrawableNormal;
    private int FOOT_LOADING_ITEM = 1;
    private int NORMAL_ITEM = 0;
    private AdapterPicture adapterPicture;
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
        adapterPicture=new AdapterPicture();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == NORMAL_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_information, viewGroup, false);
            FindViewHolder holder = new FindViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_footer_loading, viewGroup, false);
            FootViewHolder holderFoot = new FootViewHolder(view);
            return holderFoot;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == infoData.size()) {
            return FOOT_LOADING_ITEM;
        } else {
            return NORMAL_ITEM;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder findViewHolder, final int i) {

        if (infoData.size() >= 1) {
            if (findViewHolder instanceof FindViewHolder) {
                final int position = i;
                Picasso
                        .with(ChessApp.sAppContext)
                        .load(infoData.get(position)
                                .getHeadimg())
                        .transform(new CircleTransform())
                        .into(((FindViewHolder) findViewHolder).image_view_left);
                if (infoData.get(position).getSex() == 1) {
                    ((FindViewHolder) findViewHolder).text_name.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.text_red_me));
                } else if (infoData.get(position).getSex() == 2) {
                    ((FindViewHolder) findViewHolder).text_name.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.black_color));
                }
                ((FindViewHolder) findViewHolder).text_name.setText(infoData.get(position).getUsername());
                ((FindViewHolder) findViewHolder).text_time.setText(DateTool.stampToDate(String.valueOf(infoData.get(position).getCreate_time())));
                ((FindViewHolder) findViewHolder).text_content.setText(infoData.get(position).getContent());

                ((FindViewHolder) findViewHolder).text_fans.setText(NumberTool.intToString(String.valueOf(infoData.get(position).getPraise())));
                ((FindViewHolder) findViewHolder).text_selection.setText(NumberTool.intToString(String.valueOf(infoData.get(position).getBrowse())));
                ((FindViewHolder) findViewHolder).text_message.setText(NumberTool.intToString(String.valueOf(infoData.get(position).getComments())));
                if (typeTwo.equals("1")) {
                    ((FindViewHolder) findViewHolder).text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.line_color));
                    ((FindViewHolder) findViewHolder).text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
                } else if (typeTwo.equals("2")) {
                    ((FindViewHolder) findViewHolder).text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.text_red_me));
                    ((FindViewHolder) findViewHolder).text_selection.setCompoundDrawables(selectDrawable, null, null, null);
                } else {
                    ((FindViewHolder) findViewHolder).text_selection.setTextColor(ChessApp.sAppContext.getResources().getColor(R.color.line_color));
                    ((FindViewHolder) findViewHolder).text_selection.setCompoundDrawables(selectDrawableNormal, null, null, null);
                }
                ((FindViewHolder) findViewHolder).text_selection.setCompoundDrawablePadding(10);
                ((FindViewHolder) findViewHolder).text_fans.setCompoundDrawablePadding(10);
                ((FindViewHolder) findViewHolder).text_message.setCompoundDrawablePadding(10);
//        if (infoData.get(i).getImages()){
//
//        }
                ((FindViewHolder) findViewHolder).recycler_view.setLayoutManager(new GridLayoutManager(ChessApp.sAppContext, 3));
                ((FindViewHolder) findViewHolder).recycler_view.setAdapter(adapterPicture);
//                if (infoData.get(i).getImages()){
//
//                }
                ((FindViewHolder) findViewHolder).text_fans.setCompoundDrawables(fansDrawable, null, null, null);
                ((FindViewHolder) findViewHolder).text_message.setCompoundDrawables(messageDrawable, null, null, null);
                ((FindViewHolder) findViewHolder).relative_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(infoData.get(position));
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return infoData.size() + 1;
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

    public interface OnItemClickListener {
        void onItemClick(FindInfoBean.InfoBean infoBean);
    }

    private OnItemClickListener listener;

    public void setOnItemClckListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class FindViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_view_left;
        private TextView text_name, text_time, text_content, text_selection, text_fans, text_message, text_view_cancel;
        private RecyclerView recycler_view;
        private RelativeLayout relative_layout;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            image_view_left = itemView.findViewById(R.id.image_view_left);
            text_name = itemView.findViewById(R.id.text_name);
            text_time = itemView.findViewById(R.id.text_time);
            text_content = itemView.findViewById(R.id.text_content);
            text_selection = itemView.findViewById(R.id.text_selection);
            text_fans = itemView.findViewById(R.id.text_fans);
            text_message = itemView.findViewById(R.id.text_message);
            text_view_cancel = itemView.findViewById(R.id.text_view_cancel);

            recycler_view = itemView.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new GridLayoutManager(ChessApp.sAppContext, 3));

        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
