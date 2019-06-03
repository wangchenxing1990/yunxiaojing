package com.yun.xiao.jing.api;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Bitamp2Base64 {
    //将bitmap转成Base64字符串
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }
}
