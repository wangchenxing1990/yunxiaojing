package com.yun.xiao.jing.register;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.activity.EditInfoActivity;
import com.yun.xiao.jing.api.ApiConstants;
import com.yun.xiao.jing.api.ApiParams;
import com.yun.xiao.jing.api.Bitamp2Base64;
import com.yun.xiao.jing.defineView.MyPopuwindown;
import com.yun.xiao.jing.interfaces.RequestCallback;
import com.yun.xiao.jing.preference.UserPreferences;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddUserHeaderImgActivity extends AppCompatActivity implements View.OnClickListener {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddUserHeaderImgActivity.class);
        activity.startActivity(intent);
    }

    private ImageView add_header_phone;
    private RegisterAction mAction;
    String userToken;
    String device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessApp.addActivity(this);
        mAction = new RegisterAction(this, null);
        userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        device = UserPreferences.getDevice();
        setContentView(R.layout.activity_add_header_img);
        initView();
    }

    private void initView() {
        add_header_phone = findViewById(R.id.add_header_phone);
        add_header_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_header_phone:
                pickPicture();
//                showPopuwindowPhoto();//弹出修改头像的对话框
                break;
            case R.id.text_take_photo://打开相机进行拍照
                myPopuwindown.dismiss();
                openTakePhoto();//打开相机进行拍照
                break;
            case R.id.text_picture://打开相册
                myPopuwindown.dismiss();
                openPhotoAlbum();//打开相册
                break;
            case R.id.text_cancel://取消
                myPopuwindown.dismiss();
                break;
        }
    }

    List<LocalMedia> selectListTwo = new ArrayList<>();

    private void pickPicture() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(AddUserHeaderImgActivity.this)
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


    private void updateAddHeaderImg() {
        mAction.addHeaderImgInfo("", "", "", "", new RequestCallback() {

            @Override
            public void onResult(int code, String result, Throwable var3) {

            }

            @Override
            public void onFailed() {

            }
        });
    }


    /**
     * 弹出修改头像的对话框
     */
    MyPopuwindown myPopuwindown;

    private void showPopuwindowPhoto() {
        myPopuwindown = new MyPopuwindown(this, R.layout.popuwindown_photo);
        View view = myPopuwindown.getView();
        ColorDrawable dw = new ColorDrawable(0xaa000000);
        myPopuwindown.setBackgroundDrawable(dw);
        myPopuwindown.showAtLocation(this.findViewById(R.id.ll_popuwindd), Gravity.BOTTOM, 0, 0);
        myPopuwindown.setOutsideTouchable(false);
        TextView text_take_photo = view.findViewById(R.id.text_take_photo);
        TextView text_picture = view.findViewById(R.id.text_picture);
        TextView text_cancel = view.findViewById(R.id.text_cancel);

        text_take_photo.setOnClickListener(this);
        text_picture.setOnClickListener(this);
        text_cancel.setOnClickListener(this);
    }

    /**
     * 打开相册进行选择图片
     */
    private void openPhotoAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    /**
     * 启动相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 12);
    }

    /**
     * 打开相机进行拍照
     */
    private Uri imageUrl;
    File outputImage = null;

    private void openTakePhoto() {
        outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUrl = FileProvider.getUriForFile(this, ApiConstants.FILE_PROVIDER, outputImage);
        } else {
            imageUrl = Uri.fromFile(outputImage);
        }
        Log.i("imageUrl", imageUrl + "");
        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    selectListTwo = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    // selectList.get(0).getCompressPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(selectListTwo.get(0).getCompressPath());
                    submitTakePhoto(bitmap);
                    break;
            }
        }
//        switch (requestCode) {
//            case 10://启动相机
//                Log.i("111111111", "启动相机00000000" + imageUrl);
//                if (data == null) {
////                    startPhotoZoom(imageUrl);
//                    Log.i("111111111", "启动相机1111111" + imageUrl);
//                    pickBitmap(Uri.parse(String.valueOf(imageUrl)));
//                } else {
//                    Log.i("111111111", "启动相机2222222" + imageUrl);
//                    Uri uri = data.getData();
//                    pickBitmap(uri);
////                    startPhotoZoom(uri);
//                }
//                break;
//            case 12://启动图片库
//                if (resultCode == RESULT_OK) {
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        Log.i("111111111", "启动图片库00000000");
//                        handleImageOnKitKat(data);//4.4及以上系统使用这个方法处理图片
//                    } else {
//                        Log.i("111111111", "启动图片库1111111");
//                        handleImageBeforeKitKat(data);//4.4以下系统使用这个方法处理图片
//                    }
//                }
//                break;
//            case 14://缩放图片
//                Bitmap btmaip = null;
//                if (data == null) {
//                    try {
//                        if (imageUrl != null) {
//                            btmaip = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    btmaip = data.getExtras().getParcelable("data");
//                }
//
//                File fImage = new File(getExternalCacheDir(), "out_exearl.jpg");
//                FileOutputStream iStream = null;
//                if (fImage.exists()) {
//                    fImage.delete();
//                }
//                try {
//                    fImage.createNewFile();
//                    iStream = new FileOutputStream(fImage);
//                    if (btmaip == null) {
//
//                    } else {
//
//                        btmaip.compress(Bitmap.CompressFormat.JPEG, 50, iStream);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (iStream == null) {
//
//                        } else {
//                            iStream.close();
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                submitTakePhoto(btmaip);
////                update(btmaip);
//                break;
//        }
    }

    public void pickBitmap(Uri imagePath) {
        Bitmap btmaip = null;
//        if (data == null) {
        try {
            if (imagePath != null) {
                Log.i("11111111", "0000000");
                btmaip = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagePath));
                Log.i("11111111", "111111111");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        } else {
//            btmaip = data.getExtras().getParcelable("data");
//        }

//        File fImage = new File(getExternalCacheDir(), "out_exearl.jpg");
//        FileOutputStream iStream = null;
//        if (fImage.exists()) {
//            fImage.delete();
//        }
//        try {
//            fImage.createNewFile();
//            iStream = new FileOutputStream(fImage);
//            if (btmaip == null) {
//
//            } else {
//
//                btmaip.compress(Bitmap.CompressFormat.JPEG, 50, iStream);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (iStream == null) {
//
//                } else {
//                    iStream.close();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        submitTakePhoto(btmaip);
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Log.i("log", uri + "");

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

//        intent.putExtra("outputX", 180);
//        intent.putExtra("outputY", 180);

        intent.putExtra("return-data", true);
        startActivityForResult(intent, 14);

    }

    /**
     * 4.4及以上系统使用这个方法处理图片
     *
     * @param data
     */
    String imagePath = null;

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        Uri uri = data.getData();
        Log.i("1111111intent.getData :", "" + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            Log.i("111111111get :", "" + docId);
            Log.i("111111111uri :", "" + uri.getAuthority());
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.i("111111111media :", "" + imagePath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                Log.i("111111111downloads :", "" + imagePath);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
            Log.i("111111111content", "" + imagePath);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
            Log.i("111111111file", "" + imagePath);
        }
//        startPhotoZoom(uri);
        pickBitmap(uri);
//        submitTakePhoto();
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, selection, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

            cursor.close();
        }
        return path;
    }

    /**
     * 4.4以下系统使用这个方法处理图片
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String uriData = data.getDataString();
        Log.i("打开图片库的地址:::::", "uri:::" + uri + "   uriData:::::::" + uriData);
        startPhotoZoom(uri);
    }

    /**
     * 上传头像
     */
    String str = "";

    private void submitTakePhoto(final Bitmap bitmap) {
        Log.i("1111111111", "333333333333");
        StyledDialog.buildLoading().show();
        String strTwo = Bitamp2Base64.bitmapToBase64(bitmap);
        String userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = UserPreferences.getDevice();
        okhttp3.OkHttpClient okhttp = new okhttp3.OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("imgurl", strTwo);
        builder.addFormDataPart("imgtype", "png");
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(ApiConstants.HOST + ApiConstants.ADD_USER_HEADER_IMG)
                .addHeader(ApiParams.USER_TOKEN, userToken)
                .addHeader(ApiParams.MOBILE_DEVICE, device)
                .post(builder.build())
                .build();

        okhttp.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null) {
                    Log.i("vvvvvv", e.getMessage());
                }
                StyledDialog.dismissLoading();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                str = response.body().string();
                int code = response.code();
                Log.i("aaaaaaaa", str);
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
                    IntroduceActivity.start(AddUserHeaderImgActivity.this);
                    StyledDialog.dismissLoading();
                    break;
            }
        }
    };


    public void update(final Bitmap bitmap) {
        final String str = Bitamp2Base64.bitmapToBase64(bitmap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("imgurl", str);
                    json.put("imgtype", ".png");
                    String data = json.toString().replace("\\", "%");
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = URLEncoder.encode(data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, data);
                    final Request request = new Request.Builder()
                            .url(ApiConstants.HOST + ApiConstants.ADD_USER_HEADER_IMG)
                            .addHeader("user-token", userToken)
                            .addHeader("mobile-device", device)
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("infoss", response.body().string());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
