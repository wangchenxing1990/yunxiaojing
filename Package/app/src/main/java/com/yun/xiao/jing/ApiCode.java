package com.yun.xiao.jing;

public class ApiCode {
    public static int TOKEN_NO_EXIST=100;//token不存在或无效token
    public static int TOKEN_NO_QUERY=101;//token未查询到用户信息请重新登录
    public static int PHONE_IS_EMPTY=102;//手机号码参数为空
    public static int PHONE_UMBER_IS_EMPTY=103;//手机号码不能为空
    public static int MOBILE_DEVICE_EMPTY=104;//手机设备不能为空
    public static int SMS_IS_SUCCESSFULLY=105;//短信发送成功
    public static int SMS_IS_FAILED=106;//短信发送失败
    public static int PHONE_PREFIX_EMPTY=107;//手机号前缀地域参数为空
    public static int PHONE_PREFIX_NO_EMPTY=108;//手机号前缀地域不能为空
    public static int VERIFICATION_CODE=109;//返回测试短信验证码
    public static int SMS_CODE_NO_EMPTY=110;//短信验证码不能为空
    public static int SMS_CODE_INVALID=111;//短信验证码无效或已过期
    public static int SMS_CODE_ERROR=112;//短信验证码错误
    public static int USER_CODE_SUCCESSFULLY=120;//获取用户信息成功
    public static int SYSTEM_CODE_WRITE_FAILED=121;//系统写入用户失败
    public static int SYSTEM_CODE_WRITE_USER_INFORMATION_FAILED=122;//系统写入用户信息失败
    public static int SYSTEM_CODE_WRITE_USER_TOKEN_FAILED=123;//系统写入用户token失败
    public static int NETEASE_CREATE_TOKEN_FAILED=124;//网易token创建失败
    public static int UPDATE_CREATE_TOKEN_FAILED=125;//更新用户网易token失败
    public static int USER_CREATE_FAILED=126;//用户创建失败
    public static int GET_USER_INFORMATION_FAILED=127;//获取用户信息失败
    public static int USER_ALREADY_EXISTS=128;//用户已经存在
    public static int LOGIN_DEVICE_FAILED=129;//登陆设备记录失败



    public static int USER_INFORMATION_UPDATE_SUCCESSFULLY=130;//更新用户信息成功
    public static int USER_INFORMATION_UPDATE_FAILED=131;//更新用户信息失败
    public static int USER_NAME_IS_EMPTY=132;//用户姓名不能为空

    public static int USER_PASSWORD_UPDATED_SUCCESSFULLY=140;//更新用户密码成功
    public static int USER_PASSWORD_UPDATED_FAILED=141;//更新用户密码失败
    public static int USER_PASSWORD_IS_EMPTY=143;//密码不能为空


    public static int IMAGE_UPLOAD_SUCCESSFULLY=150;//上传图片成功
    public static int IMAGE_UPLOAD_FAILED=151;//上传图片失败
    public static int IMAGE_SERVICE_FAILED=152;//保存到服务器本地图片失败



    public static int USER_HAVE_REGISTER=520;//用户已注册请登录
    public static int USER_HAVE_PSW_EMPTY=521;//用户已注册但密码为空
    public static int USER_ALREADY_OFFLINE=540;//账号在该设备已经离线
    public static int USER_TIME_EXPIRATION=541;//token时间过期
    public static int USER_TIME_EXPIRED_UPDATE_FAILED=542;//token时间过期更新失败


    public static String PHONE="phone";
}
