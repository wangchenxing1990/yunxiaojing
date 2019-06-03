package com.yun.xiao.jing;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FindInfoBean implements Serializable {

    /**
     * code : 230
     * msg : Dynamic list data obtained successfully
     * info : [{"dynamic_id":79,"uid":60,"browse":0,"praise":0,"comments":0,"images":{"574":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599597.png?x-oss-process=style/imageStyle","575":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599541.png?x-oss-process=style/imageStyle"},"content":"天客隆默默哦","address":"","create_time":1559555995,"is_vip":0,"username":"123456","sex":1,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190529/155909709936.png?x-oss-process=style/imageStyle","token":"190696E1B6055A9044464FDAE5043837"},{"dynamic_id":78,"uid":60,"browse":0,"praise":0,"comments":0,"images":{"572":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955383318.png?x-oss-process=style/imageStyle","573":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955383319.png?x-oss-process=style/imageStyle"},"content":"特额流量监控哦","address":"","create_time":1559553833,"is_vip":0,"username":"123456","sex":1,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190529/155909709936.png?x-oss-process=style/imageStyle","token":"190696E1B6055A9044464FDAE5043837"},{"dynamic_id":77,"uid":60,"browse":0,"praise":0,"comments":0,"images":{"571":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955368971.png?x-oss-process=style/imageStyle"},"content":"来来来摸摸","address":"","create_time":1559553689,"is_vip":0,"username":"123456","sex":1,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190529/155909709936.png?x-oss-process=style/imageStyle","token":"190696E1B6055A9044464FDAE5043837"},{"dynamic_id":76,"uid":52,"browse":1,"praise":0,"comments":0,"images":{"562":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163434.png?x-oss-process=style/imageStyle","563":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163415.png?x-oss-process=style/imageStyle","564":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163452.png?x-oss-process=style/imageStyle","565":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163420.png?x-oss-process=style/imageStyle","566":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163446.png?x-oss-process=style/imageStyle","567":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163547.png?x-oss-process=style/imageStyle","568":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163542.png?x-oss-process=style/imageStyle","569":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163569.png?x-oss-process=style/imageStyle","570":"https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955163589.png?x-oss-process=style/imageStyle"},"content":"","address":"","create_time":1559551636,"is_vip":0,"username":"Three","sex":2,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892245196.png?x-oss-process=style/imageStyle","token":"C07733BAB03DC53BE5CCB2AECCECA094"}]
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

        public static class ImagesBean implements Serializable{
            /**
             * 574 : https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599597.png?x-oss-process=style/imageStyle
             * 575 : https://resource.golinkyou.com/public/uploads/dynamic/20190603/155955599541.png?x-oss-process=style/imageStyle
             */

            @SerializedName("574")
            private String _$574;
            @SerializedName("575")
            private String _$575;

            public String get_$574() {
                return _$574;
            }

            public void set_$574(String _$574) {
                this._$574 = _$574;
            }

            public String get_$575() {
                return _$575;
            }

            public void set_$575(String _$575) {
                this._$575 = _$575;
            }
        }
    }
}
