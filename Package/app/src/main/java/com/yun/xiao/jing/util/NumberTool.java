package com.yun.xiao.jing.util;

public class NumberTool {
    public static String intToString(String num) {
        String number = "";
        Float floa = Float.parseFloat(num);
        if (floa > 1000) {
            return String.valueOf(floa / 1000) + "K";
        }
        return num;
    }
}
