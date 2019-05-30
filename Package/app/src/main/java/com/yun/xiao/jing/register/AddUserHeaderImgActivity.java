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

import com.yun.xiao.jing.ApiCode;
import com.yun.xiao.jing.ChessApp;
import com.yun.xiao.jing.R;
import com.yun.xiao.jing.action.RegisterAction;
import com.yun.xiao.jing.api.ApiConstants;
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
                showPopuwindowPhoto();//弹出修改头像的对话框
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
        switch (requestCode) {
            case 10:
                if (data == null) {
                    startPhotoZoom(imageUrl);
                } else {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            case 12:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);//4.4及以上系统使用这个方法处理图片
                    } else {
                        handleImageBeforeKitKat(data);//4.4以下系统使用这个方法处理图片
                    }
                }
                break;
            case 14:
                Bitmap btmaip = null;
                if (data == null) {
                    try {
                        if (imageUrl != null) {
                            btmaip = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    btmaip = data.getExtras().getParcelable("data");
                }

                File fImage = new File(getExternalCacheDir(), "out_exearl.jpg");
                FileOutputStream iStream = null;
                if (fImage.exists()) {
                    fImage.delete();
                }
                try {
                    fImage.createNewFile();
                    iStream = new FileOutputStream(fImage);
                    if (btmaip == null) {

                    } else {

                        btmaip.compress(Bitmap.CompressFormat.JPEG, 50, iStream);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (iStream == null) {

                        } else {
                            iStream.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                submitTakePhoto(btmaip, fImage);
//                update(btmaip);
                break;
        }
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

        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);

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
        Log.d("intent.getData :", "" + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d("getDocumentId(uri) :", "" + docId);
            Log.d("uri.getAuthority() :", "" + uri.getAuthority());
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.e("media :", "" + imagePath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                Log.e("downloads :", "" + imagePath);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
            Log.e("content", "" + imagePath);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
            Log.e("file", "" + imagePath);
        }
        startPhotoZoom(uri);
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
        startPhotoZoom(uri);
    }

    /**
     * 上传头像
     */
    String str = "";

    private void submitTakePhoto(Bitmap bitmap, File file) {
//        StyledDialog.buildLoading().show();
        String strTwo = bitmapToBase64(bitmap);
        String userToken = UserPreferences.getInstance(ChessApp.sAppContext).getUserToken();
        String device = UserPreferences.getDevice();
        okhttp3.OkHttpClient okhttp = new okhttp3.OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("imgurl", strTwo);
        builder.addFormDataPart("imgtype", "png");
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(ApiConstants.HOST + ApiConstants.ADD_USER_HEADER_IMG)
                .addHeader("user-token", userToken)
                .addHeader("mobile-device", device)
                .post(builder.build())
                .build();

        okhttp.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.getMessage() != null) {
                    Log.i("vvvvvv", e.getMessage());
                }
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

//    private static class MyHandler extends Handler{
//        private WeakReference<AddUserHeaderImgActivity> mActivity;
//        public MyHandler(AddUserHeaderImgActivity activity){
//             mActivity= new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 133:
//                    break;
//            }
//        }
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 133:
                    IntroduceActivity.start(AddUserHeaderImgActivity.this);
                    break;
            }
        }
    };

    //将bitmap转成Base64字符串
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        //Base64算法加密，当字符串过长（一般超过76）时会自动在中间加一个换行符，字符串最后也会加一个换行符。
        // 导致和其他模块对接时结果不一致。所以不能用默认Base64.DEFAULT，而是Base64.NO_WRAP不换行
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }

    public void update(final Bitmap bitmap) {
        final String str = bitmapToBase64(bitmap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
//                    json.put("siteId", share.getString("siteid", ""));
                    json.put("imgurl", str);
                    json.put("imgtype", ".png");
                    String data = json.toString().replace("\\", "%");
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
//                        url = "agentid=1&token=" + URLEncoder.encode("token", "utf-8") + "&json=" + URLEncoder.encode(data, "utf-8");
//                        url = "data:image/png;"  + "base64," + URLEncoder.encode(data, "utf-8");
                        url = URLEncoder.encode(data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, data);
//                    RequestBody body = RequestBody.create(mediaType, url);
                    Log.i("infossToken", "token:::" + userToken + "device::::" + device);
                    Log.i("bodybody", "body:::" + url);
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
