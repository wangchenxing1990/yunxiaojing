package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hss01248.dialog.StyledDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nim.uikit.common.adapter.SystemAdaper;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MeInfoBean;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.ApiParams;
import com.yun.xiao.jing.api.Bitamp2Base64;
import com.yun.xiao.jing.defineView.CircleTransformTwo;
import com.yun.xiao.jing.preference.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MultipartBody;

public class EditInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity, MeInfoBean meInfoBean) {
        Intent intent = new Intent(activity, EditInfoActivity.class);
        intent.putExtra(ApiParams.PARAMS_EDIT_BEAN, meInfoBean);
        activity.startActivity(intent);
    }

    private FrameLayout frameLayoutBack;
    private String userToken = "";
    private String device = "";
    private MeInfoBean meInfoBean;
    private MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meInfoBean = (MeInfoBean) getIntent().getSerializableExtra(ApiParams.PARAMS_EDIT_BEAN);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        setContentView(R.layout.activity_edit_info);
        initView();
        updateViewData();//更新显示的数据
        handler = new MyHandler(this);
        textPersonalName.setText(meInfoBean.getInfo().getUsername());
    }

    /**
     * 更新显示的数据
     */
    private void updateViewData() {
        if (meInfoBean.getInfo().getImages().size() != 0) {
            for (int i = 0; i < meInfoBean.getInfo().getImages().size(); i++) {
                switch (i) {
                    case 0:
                        displayUpdateImage(imageOne, meInfoBean.getInfo().getImages().get(0));
                        break;
                    case 1:
                        displayUpdateImage(imageTwo, meInfoBean.getInfo().getImages().get(1));
                        break;
                    case 2:
                        displayUpdateImage(imageThree, meInfoBean.getInfo().getImages().get(2));
                        break;
                    case 3:
                        displayUpdateImage(imageFore, meInfoBean.getInfo().getImages().get(3));
                        break;
                    case 4:
                        displayUpdateImage(imageFive, meInfoBean.getInfo().getImages().get(4));
                        break;
                    case 5:
                        displayUpdateImage(imageSix, meInfoBean.getInfo().getImages().get(5));
                        break;
                }
            }
        }
    }

    private ImageView imageOne, imageTwo, imageThree, imageFore, imageFive, imageSix;
    private TextView textPersonalName;

    private void initView() {

        frameLayoutBack = findViewById(R.id.frame_layout_back);
        imageOne = findViewById(R.id.image_one);
        imageTwo = findViewById(R.id.image_two);
        imageThree = findViewById(R.id.image_three);
        imageFore = findViewById(R.id.image_fore);
        imageFive = findViewById(R.id.image_five);
        imageSix = findViewById(R.id.image_six);
        textPersonalName = findViewById(R.id.text_personal_name);

        frameLayoutBack.setOnClickListener(this);
        imageTwo.setOnClickListener(this);
        imageOne.setOnClickListener(this);
        imageThree.setOnClickListener(this);
        imageFore.setOnClickListener(this);
        imageFive.setOnClickListener(this);
        imageSix.setOnClickListener(this);

    }

    private int index = 0;
    private ImageView imageViewIndex;
    private int indexId = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frame_layout_back) {
            finish();
        } else if (v.getId() == R.id.image_one) {
            pickPicture(selectListOne);
            index = R.id.image_one;
            imageViewIndex = imageOne;
        } else if (v.getId() == R.id.image_two) {
            pickPicture(selectListTwo);
            index = R.id.image_two;
            imageViewIndex = imageTwo;
        } else if (v.getId() == R.id.image_three) {
            pickPicture(selectListThree);
            index = R.id.image_three;
            imageViewIndex = imageThree;
        } else if (v.getId() == R.id.image_fore) {
            pickPicture(selectListFore);
            index = R.id.image_fore;
            imageViewIndex = imageFore;
        } else if (v.getId() == R.id.image_five) {
            pickPicture(selectListFive);
            index = R.id.image_five;
            imageViewIndex = imageFive;
        } else if (v.getId() == R.id.image_six) {
            pickPicture(selectListSix);
            index = R.id.image_six;
            imageViewIndex = imageSix;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    // selectList.get(0).getCompressPath();
                    if (index == R.id.image_one) {
                        selectListOne = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 1 ? meInfoBean.getInfo().getImages().get(0).getImg_id() : 0;
                        submitTakePhoto(selectListOne);
                    } else if (index == R.id.image_two) {
                        selectListTwo = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 2 ? meInfoBean.getInfo().getImages().get(1).getImg_id() : 0;
                        submitTakePhoto(selectListTwo);
                    } else if (index == R.id.image_three) {
                        selectListThree = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 3 ? meInfoBean.getInfo().getImages().get(2).getImg_id() : 0;
                        submitTakePhoto(selectListThree);
                    } else if (index == R.id.image_fore) {
                        selectListFore = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 4 ? meInfoBean.getInfo().getImages().get(3).getImg_id() : 0;
                        submitTakePhoto(selectListFore);
                    } else if (index == R.id.image_five) {
                        selectListFive = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 5 ? meInfoBean.getInfo().getImages().get(4).getImg_id() : 0;
                        submitTakePhoto(selectListFive);
                    } else if (index == R.id.image_six) {
                        selectListSix = PictureSelector.obtainMultipleResult(data);
                        indexId = meInfoBean.getInfo().getImages().size() >= 6 ? meInfoBean.getInfo().getImages().get(5).getImg_id() : 0;
                        submitTakePhoto(selectListSix);
                    }
                    break;
            }
        }
    }

    private void displayImage(ImageView imageView, List<LocalMedia> list) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.color_f6)
                .transform(new CircleTransformTwo())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(EditInfoActivity.this)
                .load(list.get(0).getCompressPath())
                .apply(options)
                .into(imageView);
        StyledDialog.dismissLoading();
    }

    private void displayUpdateImage(ImageView imageView, MeInfoBean.InfoBean.ImagesBean imagesBean) {
        RequestOptions options = new RequestOptions()
//                .override(80,80)
                .circleCrop()
                .centerCrop()
                .placeholder(R.color.color_f6)
                .transform(new CircleTransformTwo())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(EditInfoActivity.this)
                .load(imagesBean.getImg_url())
                .apply(options)
                .into(imageView);
    }

    List<LocalMedia> selectListOne = new ArrayList<>();
    List<LocalMedia> selectListTwo = new ArrayList<>();
    List<LocalMedia> selectListThree = new ArrayList<>();
    List<LocalMedia> selectListFore = new ArrayList<>();
    List<LocalMedia> selectListFive = new ArrayList<>();
    List<LocalMedia> selectListSix = new ArrayList<>();

    private void pickPicture(List<LocalMedia> selectListTwo) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(EditInfoActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectListTwo)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    /**
     * 上传头像
     */
    String str = "";

    private void submitTakePhoto(final List<LocalMedia> selectList) {
        StyledDialog.buildLoading("").show();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart(ApiParams.PARAMS_IMAGE_DATA, ApiConstants.BITMAP_TO_BASE_64 + Bitamp2Base64.bitmapToBase64(BitmapFactory.decodeFile(selectList.get(0).getCompressPath())));
        if (indexId != 0) {
            builder.addFormDataPart(ApiParams.PARAMS_IMAGE_ID, "" + indexId);
        } else {
            builder.addFormDataPart(ApiParams.PARAMS_IMAGE_ID, "");
        }

        Log.i("codecodecode", "codecodecode:::::::" + ApiConstants.BITMAP_TO_BASE_64 + Bitamp2Base64.bitmapToBase64(BitmapFactory.decodeFile(selectList.get(0).getCompressPath())));
        okhttp3.OkHttpClient okHttp = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(ApiConstants.HOST + ApiConstants.USER_HEAD_IMAGE_UPDATE)
                .addHeader(ApiParams.USER_TOKEN, userToken)
                .addHeader(ApiParams.MOBILE_DEVICE, device)
                .post(builder.build())
                .build();
        Log.i("修改用户头像动态", "3333333333");
        okHttp.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null) {
                    Log.i("修改用户头像动态失败", e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                str = response.body().string();
                int code = response.code();
                Log.i("修改用户头像动态成功", str);
                Message message = new Message();
                message.arg1 = 133;
                message.obj = selectList;
                handler.sendMessage(message);
            }
        });
    }

    class MyHandler extends Handler {
        private WeakReference<EditInfoActivity> activity;

        public MyHandler(EditInfoActivity editInfoActivity) {
            activity = new WeakReference(editInfoActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 133:
                    displayImage(imageViewIndex, (List<LocalMedia>) msg.obj);
                    Intent intent = new Intent();
                    intent.setAction("com.yun.xiao.jing.fresh");
                    sendBroadcast(intent);
                    break;
            }
        }
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

}
