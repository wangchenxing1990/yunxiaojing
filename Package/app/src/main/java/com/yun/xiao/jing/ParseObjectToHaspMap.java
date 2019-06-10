package com.yun.xiao.jing;

import android.util.Log;


import com.yun.xiao.jing.bean.PictureUrlBean;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseObjectToHaspMap {
    public static List<FindInfoBean> testJackson(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonInfo = jsonObject.getJSONArray("info");
            return parseInfo(jsonInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<FindInfoBean> parseInfo(JSONArray jsonInfo) throws JSONException {
        List<FindInfoBean> list = new ArrayList();
        for (int i = 0; i < jsonInfo.length(); i++) {
            FindInfoBean findInfoBean = new FindInfoBean();
            JSONObject jsonObject = jsonInfo.getJSONObject(i);
            findInfoBean.setAddress(jsonObject.getString("address"));
            findInfoBean.setDynamic_id(jsonObject.getInt("dynamic_id"));
            findInfoBean.setUid(jsonObject.getInt("uid"));
            findInfoBean.setBrowse(jsonObject.getInt("browse"));
            findInfoBean.setPraise(jsonObject.getInt("praise"));
            findInfoBean.setComments(jsonObject.getInt("comments"));
            findInfoBean.setContent(jsonObject.getString("content"));
            findInfoBean.setCreate_time(jsonObject.getInt("create_time"));
            findInfoBean.setIs_vip(jsonObject.getInt("is_vip"));
            findInfoBean.setUsername(jsonObject.getString("username"));
            findInfoBean.setSex(jsonObject.getInt("sex"));
            findInfoBean.setHeadimg(jsonObject.getString("headimg"));
            findInfoBean.setToken(jsonObject.getString("token"));
            findInfoBean.setImages(praseImages(jsonObject.getString("images")));
            list.add(findInfoBean);
        }
        return list;
    }

    public static List<PictureUrlBean> praseImages(String jsonInfo) {
        try {
            List<PictureUrlBean> listUrl = new ArrayList();
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef
                    = new TypeReference<HashMap<String, Object>>() {
            };
            HashMap<String, Object> o = mapper.readValue(String.valueOf(jsonInfo), typeRef);
            for (Object ob : o.values()) {
                String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(ob);
                Log.i("解析数据的格式重新改变", jsonString);
                PictureUrlBean pictureUrlBean = parseUrl(jsonString);
                listUrl.add(pictureUrlBean);
            }

            try {
                JSONArray jsonArray = new JSONArray(o.values().toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String origin = json.getString("origin");
                    Log.i("originorigin", "originorigin::::" + origin);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (Object url : o.values()) {
//                PictureUrlBean pictureUrlBean = parseUrl(url);
//                PictureUrlBean  list = mapper.readValue(url, PictureUrlBean.class);
//                Log.i("解析数据的格式重新改变", "sssssssss" + pictureUrlBean.getOrigin());
//                listUrl.add(url.toString());
            }
            return listUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PictureUrlBean parseUrl(String json) {
        PictureUrlBean pictureUrlBean = new PictureUrlBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            pictureUrlBean.setOrigin(jsonObject.optString("origin"));
            pictureUrlBean.setThumb(jsonObject.optString("thumb"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pictureUrlBean;
    }
}
