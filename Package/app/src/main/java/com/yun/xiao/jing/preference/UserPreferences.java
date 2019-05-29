package com.yun.xiao.jing.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings;

import com.yun.xiao.jing.ChessApp;

/**
 * SharedPreferences集成工具类
 */
public class UserPreferences {
    public static UserPreferences mPreferences;
    public SharedPreferences sp_userinfo;
    public final static String USERINFO = "userinfo";
    //USERINFO里面键
    private static final String KEY_USER_PHONE = "phone";
    private static final String KEY_USER_COUNTRY_CODE = "country_code";
    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_ID = "uid";
    private static final String KEY_USER_ACCOUNT = "account";
    //余额
    private static final String KEY_USER_COINS = "coins";
    private static final String KEY_USER_DIAMOND = "diamond";
    //手牌收藏数量
    private static final String KEY_COLLECT_HAND_NUM = "collect_hand_num";
    //战鱼号
    static final String KEY_ZHANGYU_ID = "uuid";
    static final String KEY_USER_LEVEL = "level";//100表示能够组局钻石赛
    //第几次修改昵称
    static final String KEY_NICKNAME_TIMES = "nickname_times";

    private UserPreferences(Context context) {
        sp_userinfo = context.getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
    }

    public static UserPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = new UserPreferences(context);
        }
        return mPreferences;
    }

    /**
     * 设置用户手机号
     */
    public void setUserPhone(String phnoe) {
        Editor edit = sp_userinfo.edit();
        edit.putString(KEY_USER_PHONE, phnoe);
        edit.apply();
    }

    /**
     * 设置用户手机号
     */
    public String getUserPhone() {
        return sp_userinfo.getString(KEY_USER_PHONE, "");
    }

    /**
     * 设置用户token
     *
     * @param token
     */
    public void setUserToken(String token) {
        Editor edit = sp_userinfo.edit();
        edit.putString(KEY_USER_TOKEN, token);
        edit.apply();
    }

    /**
     * 获取用户token
     */
    public String getUserToken() {
        return sp_userinfo.getString(KEY_USER_TOKEN, "");
    }

    public static String getDevice() {
        return Settings.System.getString(ChessApp.sAppContext.getContentResolver(), Settings.System.ANDROID_ID);
    }

    public String getUserAccount() {
        return sp_userinfo.getString(KEY_USER_ACCOUNT, "");
    }

    public void setAccount(String account) {
        Editor edit = sp_userinfo.edit();
        edit.putString(KEY_USER_TOKEN, account);
        edit.apply();
    }
}
