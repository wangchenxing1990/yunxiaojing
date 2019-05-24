package com.yun.xiao.jing.defineView;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.nos.util.NosThumbImageUtil;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;

/**
 * Created by huangjun on 2015/11/13.
 */
public class HeadImageView extends CircleImageView {

    public static final int DEFAULT_AVATAR_THUMB_SIZE = (int) ChessApp.sAppContext.getResources().getDimension(R.dimen.dp_48);
    public static final int DEFAULT_AVATAR_NOTIFICATION_ICON_SIZE = (int) ChessApp.sAppContext.getResources().getDimension(R.dimen.dp_60);

    private DisplayImageOptions options = createImageOptions();

    public static final DisplayImageOptions createImageOptions() {
//        int defaultIcon = NimUIKit.getUserInfoProvider().getDefaultIconResId();
        int defaultIcon = R.mipmap.default_club_head;
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultIcon)
                .showImageOnFail(defaultIcon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public HeadImageView(Context context) {
        super(context);
    }

    public HeadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 加载用户头像（默认大小的缩略图）
     *
     * @param account
     */
    public void loadBuddyAvatar(String account) {
        loadBuddyAvatar(account, DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * 加载用户头像（原图）
     * @param account
     */
    public void loadBuddyOriginalAvatar(String account) {
        loadBuddyAvatar(account, 0);
    }

    /**
     * 加载用户头像（指定缩略大小）
     * @param account
     * @param thumbSize 缩略图的宽、高
     */
    public void loadBuddyAvatar(final String account, final int thumbSize) {
        // 先显示默认头像
        setImageResource(R.mipmap.default_club_head);
        if(TextUtils.isEmpty(account)){
            return;
        }
        // 判断是否需要ImageLoader加载

        boolean needLoad = true;
        doLoadImage(needLoad, account,"", thumbSize);
    }

    public void loadBuddyAvatarByMeFrg(final String account, final int thumbSize, int defaultImgId) {
        // 先显示默认头像
        setImageResource(defaultImgId);
        if(TextUtils.isEmpty(account)){
            return;
        }
        // 判断是否需要ImageLoader加载
        boolean needLoad = true;
        doLoadImage(needLoad, account, "", thumbSize);
    }

    /**
     * 加载用户头像（指定缩略大小），根据URL
     * @param account
     * @param thumbSize 缩略图的宽、高
     */
    public void loadBuddyAvatarByUrl(final String account, final String url , int thumbSize) {
        String avater = "";
        // 先显示默认头像
        setImageResource(R.mipmap.default_club_head);
        if(TextUtils.isEmpty(account)){
            return;
        }
        // 判断是否需要ImageLoader加载
//        final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(account);
        avater = url;
//        if(userInfo != null){
//            avater = userInfo.getAvatar();
//        }else{
//            avater = url;
//        }
        boolean needLoad = true;
        // ImageLoader异步加载
        if (needLoad) {
            setTag(account); // 解决ViewHolder复用问题
            /**
             * 若使用网易云信云存储，这里可以设置下载图片的压缩尺寸，生成下载URL
             * 如果图片来源是非网易云信云存储，请不要使用NosThumbImageUtil
             */
//            final String thumbUrl = thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(avater,
//                    NosThumbParam.ThumbType.Crop, thumbSize, thumbSize) : avater;
            final String thumbUrl = makeAvatarThumbNosUrl(avater, thumbSize);

            // 异步从cache or NOS加载图片
            ImageLoader.getInstance().displayImage(thumbUrl, new NonViewAware(new ImageSize(thumbSize, thumbSize), ViewScaleType.CROP), options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (getTag() != null && getTag().equals(account)) {
                        setImageBitmap(loadedImage);
                    }
                }
            });
        } else {
            setTag(null);
        }
    }

    /**
     * 加载俱乐部头像（指定缩略大小），根据URL
     * @param clubId
     * @param thumbSize 缩略图的宽、高
     */
    public void loadClubAvatarByUrl(final String clubId, final String url , int thumbSize) {
        String avater = "";
        // 先显示默认头像
        setImageResource(R.mipmap.default_club_head);
        if (TextUtils.isEmpty(clubId)) {
            return;
        }
        // 判断是否需要ImageLoader加载
//        final Team team = TeamDataCache.getInstance().getTeamById(clubId);
//        if (ClubConstant.isSuperClub(team)) {
//            //超级俱乐部
////            setImageResource(R.drawable.default_club_super_head);
//            setImageResource(R.mipmap.default_club_head);
//        }
        if (!TextUtils.isEmpty(url)) {
            avater = url;
        }
//        else if (team != null) {
//            avater = ClubConstant.getClubExtAvatar(team.getExtServer());
//        }
        boolean needLoad = true;
        // ImageLoader异步加载
        if (needLoad) {
            setTag(clubId); // 解决ViewHolder复用问题
            /**
             * 若使用网易云信云存储，这里可以设置下载图片的压缩尺寸，生成下载URL
             * 如果图片来源是非网易云信云存储，请不要使用NosThumbImageUtil
             */
            final String thumbUrl = thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(avater,
                    NosThumbParam.ThumbType.Crop, thumbSize, thumbSize) : avater;

            // 异步从cache or NOS加载图片
            ImageLoader.getInstance().displayImage(thumbUrl, new NonViewAware(new ImageSize(thumbSize, thumbSize),
                    ViewScaleType.CROP), options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (getTag() != null && getTag().equals(clubId)) {
                        setImageBitmap(loadedImage);
                    }
                }
            });
        } else {
            setTag(null);
        }
    }

//    public void loadTeamIcon(String tid) {
//        Bitmap bitmap = NimUIKit.getUserInfoProvider().getTeamIcon(tid);
//        setImageBitmap(bitmap);
//    }

    public void loadTeamIconByTeam(final Team team) {
        // 先显示默认头像
//        setImageResource(R.drawable.nim_avatar_group);

        // 判断是否需要ImageLoader加载
//        boolean needLoad = team != null && ImageLoaderKit.isImageUriValid(team.getIcon());

        doLoadImage(true, team != null ? team.getId() : null, team.getIcon(), DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * ImageLoader异步加载
     */
    private void doLoadImage(final boolean needLoad, final String tag, final String url, final int thumbSize) {
        if (needLoad) {
            setTag(tag); // 解决ViewHolder复用问题
            /**
             * 若使用网易云信云存储，这里可以设置下载图片的压缩尺寸，生成下载URL
             * 如果图片来源是非网易云信云存储，请不要使用NosThumbImageUtil
             */
            final String thumbUrl = makeAvatarThumbNosUrl(url, thumbSize);

            // 异步从cache or NOS加载图片
            ImageLoader.getInstance().displayImage(thumbUrl, new NonViewAware(new ImageSize(thumbSize, thumbSize),
                    ViewScaleType.CROP), options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (getTag() != null && getTag().equals(tag)) {
                        setImageBitmap(loadedImage);
                    }
                }
            });
        } else {
            setTag(null);
        }
    }

    /**
     * 解决ViewHolder复用问题
     */
    public void resetImageView() {
        setImageBitmap(null);
    }

    /**
     * 生成头像缩略图NOS URL地址（用作ImageLoader缓存的key）
     */
    public static String makeAvatarThumbNosUrl(final String url, final int thumbSize) {
        return thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(url, NosThumbParam.ThumbType.Crop, thumbSize, thumbSize) : url;
    }

    public static String getAvatarCacheKey(final String url) {
        return makeAvatarThumbNosUrl(url, DEFAULT_AVATAR_THUMB_SIZE);
    }
}
