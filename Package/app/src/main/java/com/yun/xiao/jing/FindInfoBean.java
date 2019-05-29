package com.yun.xiao.jing;

import android.widget.SectionIndexer;

import java.io.Serializable;
import java.util.List;

public class FindInfoBean implements Serializable {
    /**
     * code : 230
     * msg : Dynamic list data obtained successfully
     * info : [{"dynamic_id":11,"browse":0,"praise":0,"comments":0,"images":{},"content":"123123213","address":"","create_time":1558936096,"is_vip":0,"username":"Two","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","token":"C9077853CF698160C4233EC60F7927FA"},{"dynamic_id":11,"browse":0,"praise":0,"comments":0,"images":{},"content":"123123213","address":"","create_time":1558936096,"is_vip":0,"username":"Two","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","token":"C85D4E6D7A10626399E8AE7C79A79CF6"},{"dynamic_id":11,"browse":0,"praise":0,"comments":0,"images":{},"content":"123123213","address":"","create_time":1558936096,"is_vip":0,"username":"Two","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","token":"02978DF0FBEC99A6DA4C43E128488118"},{"dynamic_id":11,"browse":0,"praise":0,"comments":0,"images":{},"content":"123123213","address":"","create_time":1558936096,"is_vip":0,"username":"Two","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","token":"6D89AE40FFAA62D8CEB7D88B8BC2B974"},{"dynamic_id":11,"browse":0,"praise":0,"comments":0,"images":{},"content":"123123213","address":"","create_time":1558936096,"is_vip":0,"username":"Two","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","token":"ED11A92972EC0424FA648D860F142520"}]
     */

    private int code;
    private String msg;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable {
        /**
         * dynamic_id : 11
         * browse : 0
         * praise : 0
         * comments : 0
         * images : {}
         * content : 123123213
         * address :
         * create_time : 1558936096
         * is_vip : 0
         * username : Two
         * sex : 2
         * headimg : https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle
         * token : C9077853CF698160C4233EC60F7927FA
         */

        private int dynamic_id;
        private int browse;
        private int praise;
        private int comments;
        private ImagesBean images;
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

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
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

        public static class ImagesBean implements Serializable {

        }
    }
}
