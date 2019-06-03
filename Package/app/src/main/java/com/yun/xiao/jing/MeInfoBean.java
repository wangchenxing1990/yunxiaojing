package com.yun.xiao.jing;

import java.io.Serializable;
import java.util.List;

public class MeInfoBean implements Serializable {

    /**
     * code : 320
     * msg : User data was successfully retrieved
     * info : {"password":"5dcfb2c20d10b35ebb2636fab9e64708","imtoken":"d6064aa57c92681d4c47daeae119bd0a","mobile":"yes","status":0,"is_vip":0,"enable_superLike":0,"username":"dave2","birthday":"1991-09-25","sex":1,"headimg":"https://resource.devcgx.top/public/uploads/headimgurl/20190403/155428563552.png","token":"E64290AB6B8A6B3E075B4DD2B21955C1","token_expired":1556864989,"images":[{"img_id":26,"img_url":"https://resource.devcgx.top/public/uploads/headimgurl/20190403/155428563552.png"}],"position":"","school":"","height":"","weight":"","dating_preferences":1,"dating_startage":19,"dating_endage":79,"personal_introduction":"","like_count":2,"seen_count":2,"focus_count":1}
     */

    private int code;
    private String msg;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * password : 5dcfb2c20d10b35ebb2636fab9e64708
         * imtoken : d6064aa57c92681d4c47daeae119bd0a
         * mobile : yes
         * status : 0
         * is_vip : 0
         * enable_superLike : 0
         * username : dave2
         * birthday : 1991-09-25
         * sex : 1
         * headimg : https://resource.devcgx.top/public/uploads/headimgurl/20190403/155428563552.png
         * token : E64290AB6B8A6B3E075B4DD2B21955C1
         * token_expired : 1556864989
         * images : [{"img_id":26,"img_url":"https://resource.devcgx.top/public/uploads/headimgurl/20190403/155428563552.png"}]
         * position :
         * school :
         * height :
         * weight :
         * dating_preferences : 1
         * dating_startage : 19
         * dating_endage : 79
         * personal_introduction :
         * like_count : 2
         * seen_count : 2
         * focus_count : 1
         */

        private String password;
        private String imtoken;
        private String mobile;
        private int status;
        private int is_vip;
        private int enable_superLike;
        private String username;
        private String birthday;
        private int sex;
        private String headimg;
        private String token;
        private int token_expired;
        private String position;
        private String school;
        private String height;
        private String weight;
        private int dating_preferences;
        private int dating_startage;
        private int dating_endage;
        private String personal_introduction;
        private int like_count;
        private int seen_count;
        private int focus_count;
        private List<ImagesBean> images;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImtoken() {
            return imtoken;
        }

        public void setImtoken(String imtoken) {
            this.imtoken = imtoken;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getEnable_superLike() {
            return enable_superLike;
        }

        public void setEnable_superLike(int enable_superLike) {
            this.enable_superLike = enable_superLike;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public int getToken_expired() {
            return token_expired;
        }

        public void setToken_expired(int token_expired) {
            this.token_expired = token_expired;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getDating_preferences() {
            return dating_preferences;
        }

        public void setDating_preferences(int dating_preferences) {
            this.dating_preferences = dating_preferences;
        }

        public int getDating_startage() {
            return dating_startage;
        }

        public void setDating_startage(int dating_startage) {
            this.dating_startage = dating_startage;
        }

        public int getDating_endage() {
            return dating_endage;
        }

        public void setDating_endage(int dating_endage) {
            this.dating_endage = dating_endage;
        }

        public String getPersonal_introduction() {
            return personal_introduction;
        }

        public void setPersonal_introduction(String personal_introduction) {
            this.personal_introduction = personal_introduction;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getSeen_count() {
            return seen_count;
        }

        public void setSeen_count(int seen_count) {
            this.seen_count = seen_count;
        }

        public int getFocus_count() {
            return focus_count;
        }

        public void setFocus_count(int focus_count) {
            this.focus_count = focus_count;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean implements Serializable{
            /**
             * img_id : 26
             * img_url : https://resource.devcgx.top/public/uploads/headimgurl/20190403/155428563552.png
             */

            private int img_id;
            private String img_url;

            public int getImg_id() {
                return img_id;
            }

            public void setImg_id(int img_id) {
                this.img_id = img_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
