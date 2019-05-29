package com.yun.xiao.jing;

import java.util.List;

public class InfoBeanTwo {

    /**
     * code : 370
     * msg : The user's personal information was successfully obtained
     * info : {"username":"Seven","sex":2,"token":"FF77CEED9EE66D50498D0EA6A5652339","current_city":"","imaccount":"91CF626A249A29086A8FCEB23078D76A","imtoken":"e7b68cbc29d109da28ccdb8a01703e93","distance":"","age":21,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle","images":[{"img_id":481,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}],"focus_on":"no","like":"no"}
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
         * username : Seven
         * sex : 2
         * token : FF77CEED9EE66D50498D0EA6A5652339
         * current_city :
         * imaccount : 91CF626A249A29086A8FCEB23078D76A
         * imtoken : e7b68cbc29d109da28ccdb8a01703e93
         * distance :
         * age : 21
         * headimg : https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle
         * images : [{"img_id":481,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle2"}]
         * personal_tags : [{"subtitle":"constellation","msg":"Gemini"}]
         * focus_on : no
         * like : no
         */

        private String username;
        private int sex;
        private String token;
        private String current_city;
        private String imaccount;
        private String imtoken;
        private String distance;
        private int age;
        private String headimg;
        private String focus_on;
        private String like;
        private List<ImagesBean> images;
        private List<PersonalTagsBean> personal_tags;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCurrent_city() {
            return current_city;
        }

        public void setCurrent_city(String current_city) {
            this.current_city = current_city;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getFocus_on() {
            return focus_on;
        }

        public void setFocus_on(String focus_on) {
            this.focus_on = focus_on;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<PersonalTagsBean> getPersonal_tags() {
            return personal_tags;
        }

        public void setPersonal_tags(List<PersonalTagsBean> personal_tags) {
            this.personal_tags = personal_tags;
        }

        public static class ImagesBean {
            /**
             * img_id : 481
             * img_url : https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle2
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

        public static class PersonalTagsBean {
            /**
             * subtitle : constellation
             * msg : Gemini
             */

            private String subtitle;
            private String msg;

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }
    }
}
