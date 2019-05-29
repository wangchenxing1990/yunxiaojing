package com.yun.xiao.jing.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getTimeHour(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
     * 比较两个时间点相差多少年。
     */
    public static long compareTime(String time){
        String myString=null;
        if(time==null){
            myString="0000-00-00";
        }else{
            myString = time;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        Date now1 = new Date();
        String now=sdf.format(now1);
        Date date = null;
        try {
            now1 = sdf.parse(now);
            date = sdf.parse(myString);
            start.setTime(date);
            end.setTime(now1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(end.get(Calendar.YEAR)>start.get(Calendar.YEAR)){
            int year=end.get(Calendar.YEAR)-start.get(Calendar.YEAR);
            if(end.get(Calendar.MONTH)+1>=start.get(Calendar.MONTH)+1){
                if(end.get(Calendar.DATE)>=start.get(Calendar.DATE)){
                    return year;
                }else{
                    return year-1;
                }
            }else{
                return year-1;
            }

        }else{
            return 0;
        }

    }
}
