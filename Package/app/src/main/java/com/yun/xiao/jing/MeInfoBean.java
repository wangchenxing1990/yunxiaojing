package com.yun.xiao.jing;

import java.util.List;

public class MeInfoBean {

    /**
     * code : 320
     * msg : User data was successfully retrieved
     * info : {"password":"fa9b5d7974e5e51c7eeb03ee963b41e6","mobile":"yes","mobile_device":"2443c65e814e1e11","status":0,"is_vip":0,"violations":0,"enable_superLike":0,"imaccount":"B0C6BC853C37F36F2D5841B99FAC0A3E","imtoken":"80b0436f4eb3a3dd0f2e649efca18d73","username":"123456","birthday":"1990-5-20","sex":1,"headimg":"","token":"69F92A318F8E9BC0E1B646FB1563BEB2","token_expired":1558958244,"images":[],"position":"","school":"","height":"","weight":"","info_complete":1,"dating_preferences":0,"dating_startage":18,"dating_endage":80,"personal_introduction":"","like_count":0,"seen_count":0,"focus_count":0}
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

    public static class InfoBean {
        /**
         * password : fa9b5d7974e5e51c7eeb03ee963b41e6
         * mobile : yes
         * mobile_device : 2443c65e814e1e11
         * status : 0
         * is_vip : 0
         * violations : 0
         * enable_superLike : 0
         * imaccount : B0C6BC853C37F36F2D5841B99FAC0A3E
         * imtoken : 80b0436f4eb3a3dd0f2e649efca18d73
         * username : 123456
         * birthday : 1990-5-20
         * sex : 1
         * headimg :
         * token : 69F92A318F8E9BC0E1B646FB1563BEB2
         * token_expired : 1558958244
         * images : []
         * position :
         * school :
         * height :
         * weight :
         * info_complete : 1
         * dating_preferences : 0
         * dating_startage : 18
         * dating_endage : 80
         * personal_introduction :
         * like_count : 0
         * seen_count : 0
         * focus_count : 0
         */

        private String password;
        private String mobile;
        private String mobile_device;
        private int status;
        private int is_vip;
        private int violations;
        private int enable_superLike;
        private String imaccount;
        private String imtoken;
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
        private int info_complete;
        private int dating_preferences;
        private int dating_startage;
        private int dating_endage;
        private String personal_introduction;
        private int like_count;
        private int seen_count;
        private int focus_count;
        private List<?> images;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile_device() {
            return mobile_device;
        }

        public void setMobile_device(String mobile_device) {
            this.mobile_device = mobile_device;
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

        public int getViolations() {
            return violations;
        }

        public void setViolations(int violations) {
            this.violations = violations;
        }

        public int getEnable_superLike() {
            return enable_superLike;
        }

        public void setEnable_superLike(int enable_superLike) {
            this.enable_superLike = enable_superLike;
        }

        public String getImaccount() {
            return imaccount;
        }

        public void setImaccount(String imaccount) {
            this.imaccount = imaccount;
        }

        public String getImtoken() {
            return imtoken;
        }

        public void setImtoken(String imtoken) {
            this.imtoken = imtoken;
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

        public int getInfo_complete() {
            return info_complete;
        }

        public void setInfo_complete(int info_complete) {
            this.info_complete = info_complete;
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

        public List<?> getImages() {
            return images;
        }

        public void setImages(List<?> images) {
            this.images = images;
        }
    }
}
