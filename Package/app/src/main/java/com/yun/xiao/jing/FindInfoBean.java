package com.yun.xiao.jing;

import com.google.gson.annotations.SerializedName;
import com.yun.xiao.jing.bean.PictureUrlBean;

import java.io.Serializable;
import java.util.List;

public class FindInfoBean implements Serializable {
        /**
         * dynamic_id : 79
         * uid : 60
         * browse : 0
         * praise : 0
         * comments : 0
         * images : {"574":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599597.png?x-oss-process=style/imageStyle","575":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599541.png?x-oss-process=style/imageStyle"}
         * content : 天客隆默默哦
         * address :
         * create_time : 1559555995
         * is_vip : 0
         * username : 123456
         * sex : 1
         * headimg : https://resource.golinkyou.com/public/uploads/headimgurl/20190529/155909709936.png?x-oss-process=style/imageStyle
         * token : 190696E1B6055A9044464FDAE5043837
         */

        private int dynamic_id;
        private int uid;
        private int browse;
        private int praise;
        private int comments;
        private List<PictureUrlBean> images;
        private String content;
        private String address;
        private int create_time;
        private int is_vip;
        private String username;
        private int sex;
        private String headimg;
        private String token;

        public int getDynamic_id() {
            return dynamic_id;
        }

        public void setDynamic_id(int dynamic_id) {
            this.dynamic_id = dynamic_id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getBrowse() {
            return browse;
        }

        public void setBrowse(int browse) {
            this.browse = browse;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public List<PictureUrlBean> getImages() {
            return images;
        }

        public void setImages(List<PictureUrlBean> images) {
            this.images = images;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

}
