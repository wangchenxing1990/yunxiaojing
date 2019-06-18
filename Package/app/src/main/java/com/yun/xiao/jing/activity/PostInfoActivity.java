package com.yun.xiao.jing.activity;

import android.app.Activity;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.util.Log;

import android.view.View;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.hss01248.dialog.StyledDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.MainActivity;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.LoginAction;
import com.yun.xiao.jing.adapter.GridImageAdapter;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.Bitamp2Base64;
import com.yun.xiao.jing.api.FullyGridLayoutManager;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PostInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginAction loginAction;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PostInfoActivity.class);
        activity.startActivity(intent);
    }

    private String userToken = "";
    private String device = "";
    private String content = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        loginAction = new LoginAction(this, null);
        setContentView(R.layout.activity_post_info);
        initView();
    }

    private ImageView imageView;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private RelativeLayout frame_layout;
    private RelativeLayout frame_layout_post;
    private EditText edit_text_view;

    private void initView() {
        imageView = findViewById(R.id.image_view);
        recyclerView = findViewById(R.id.recycler);
        frame_layout = findViewById(R.id.frame_layout);
        frame_layout_post = findViewById(R.id.frame_layout_post);
        edit_text_view = findViewById(R.id.edit_text_view);
        imageView.setOnClickListener(this);
        frame_layout.setOnClickListener(this);
        frame_layout_post.setOnClickListener(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(PostInfoActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(PostInfoActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        recyclerView.setAdapter(adapter);
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(PostInfoActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(9)// 最大图片选择数量
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
                    .selectionMedia(selectList)// 是否传入已选图片
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
    };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_layout:
                finish();
                break;
            case R.id.frame_layout_post:
                submitTakePhoto();
                break;
        }
    }

    /**
     * 发表动态
     */
    private void submitPostInDynamic() {
        String content = edit_text_view.getText().toString().trim();
        if (TextUtils.isEmpty(content) && selectList.size() == 0) {
            Toast.makeText(ChessApp.sAppContext, "Please enter content", Toast.LENGTH_SHORT).show();
            return;
        }

        loginAction.submitPostInDynamicToService(userToken, device, content, new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {
                Log.i("上传动态返回的数据", result);
            }

            @Override
            public void onFailed() {

            }
        });

    }

    /**
     * 上传头像
     */
    String str = "";
    String strTwo = "";

    private void submitTakePhoto() {
        String content = edit_text_view.getText().toString().trim();
        if (TextUtils.isEmpty(content) && selectList.size() == 0) {
            Toast.makeText(ChessApp.sAppContext, "Please enter the published content", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONArray jsonArray = new JSONArray();
        String jsonArrayTwo = "";
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < selectList.size(); i++) {
            strTwo = "";
            strTwo = ApiConstants.BITMAP_TO_BASE_64 + Bitamp2Base64.bitmapToBase64(BitmapFactory.decodeFile(selectList.get(i).getCompressPath()));
            jsonArray.add(strTwo);
        }
        Log.i("jsonArray::::", "jsonArray::::" + jsonArray.toString());
        if (selectList.size() != 0) {
            builder.addFormDataPart("images", jsonArray.toString());
        }
        String userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = UserPreferences.getDevice();
        StyledDialog.buildLoading("").show();
        OkHttpClient okHttp = new OkHttpClient();
        builder.addFormDataPart("content", content);
        Request request = new Request.Builder()
                .url(ApiConstants.HOST + ApiConstants.USER_IN_DYNAMIC)
                .addHeader("user-token", userToken)
                .addHeader("mobile-device", device)
                .post(builder.build())
                .build();
        Log.i("发表动态", "3333333333");
        okHttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null) {
                    Log.i("发表动态失败", e.getMessage());
                }
                StyledDialog.dismissLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                str = response.body().string();
                int code = response.code();
                handler.sendEmptyMessage(133);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 133:
                    StyledDialog.dismissLoading();
                    MainActivity.start(PostInfoActivity.this, "postinfo");
                    break;
            }
        }
    };
    List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
