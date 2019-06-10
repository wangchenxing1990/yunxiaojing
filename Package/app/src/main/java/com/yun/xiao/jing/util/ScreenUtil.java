package com.yun.xiao.jing.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yun.xiao.jing.ChessApp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ScreenUtil {
    public static final String TAG = "Demo.ScreenUtil";

    public static double RATIO = 0.75;

    public static int screenWidth;
    public static int screenHeight;
    public static int screenMin;// 宽高中，小的一边
    public static int screenMax;// 宽高中，较大的值

    public static float density;
    public static float scaleDensity;
    public static float xdpi;
    public static float ydpi;
    public static int densityDpi;

    public static int dialogWidth;
    public static int statusbarheight;
    public static int navbarheight;

    static {
        init(ChessApp.sAppContext);
//		init(NimUIKit.getContext());
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    public static int getDialogWidth() {
        dialogWidth = (int) (screenMin * RATIO);
        return dialogWidth;
    }

    public static void init(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;

        LogUtil.d(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getDisplayHeight() {
        if (screenHeight == 0) {
//			GetInfo(NimUIKit.getContext());
            GetInfo(ChessApp.sAppContext);
        }
        return screenHeight;
    }

    public static void GetInfo(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
        statusbarheight = getStatusBarHeight(context);
        navbarheight = getNavBarHeight(context);
        LogUtil.d(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getStatusBarHeight(Context context) {
//		Class<?> c = null;
//		Object obj = null;
//		Field field = null;
//		int x = 0, sbar = 0;
//		try {
//			c = Class.forName("com.android.internal.R$dimen");
//			obj = c.newInstance();
//			field = c.getField("status_bar_height");
//			x = Integer.parseInt(field.get(obj).toString());
//			sbar = context.getResources().getDimensionPixelSize(x);
//		} catch (Exception E) {
//			E.printStackTrace();
//		}
//		return sbar;
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        LogUtil.i(context.getResources().getDimensionPixelSize(resourceId) + "");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void screenBrightness(Activity activity, float progress) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = progress;
        activity.getWindow().setAttributes(lp);
    }

    public static int getSysScreenBrightness(Context context) {
        int brightness = 0;
        try {
            brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return brightness;
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        int eX = (int) ev.getRawX();
        int eY = (int) ev.getRawY();
        if (eX < x || eX > (x + view.getWidth()) || eY < y || eY > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static int getScaleHeight(int scalewidth, int width, int height) {
        int scaleHeight = scalewidth * height / width;
        return scaleHeight;
    }

    public static void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,// 改变亮度
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    public static int getScreenWidth(Context activity) {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context activity) {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     *
     * @param activity
     */
    public static void quitFullScreen(Activity activity) {
        final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        if (context == null) {
            return (int) ((dp * 2.5) + 0.5);
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static int getImgWH(String urls) {
        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(is);

            int srcWidth = image.getWidth();      // 源图宽度
            int srcHeight = image.getHeight();    // 源图高度
            int[] imgSize = new int[2];
            imgSize[0] = srcWidth;
            imgSize[1] = srcHeight;
            int imageHeight = srcHeight * screenWidth / srcWidth;
            //释放资源
            image.recycle();
            is.close();
            conn.disconnect();
            return imageHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleHeight, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }


}
