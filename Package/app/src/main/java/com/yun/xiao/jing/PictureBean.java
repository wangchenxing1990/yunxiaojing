package com.yun.xiao.jing;

import java.io.Serializable;
import java.util.List;

public class PictureBean {

    /**
     * code : 160
     * msg : The user list was obtained successfully
     * info : [{"super_like_you":"no","username":"Seven","sex":2,"token":"FF77CEED9EE66D50498D0EA6A5652339","current_city":"","distance":"","age":21,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle","images":[{"img_id":481,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Six","sex":2,"token":"7A577679261DC435DC889ED232E5AD74","current_city":"","distance":"","age":23,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892257927.png?x-oss-process=style/imageStyle","images":[{"img_id":480,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892257927.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Five","sex":2,"token":"77B80732DA24A69E127A0617FF297B67","current_city":"","distance":"","age":22,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892253124.png?x-oss-process=style/imageStyle","images":[{"img_id":479,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892253124.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Four","sex":2,"token":"A3AB20C7E457BC5CAAFC0298D766A8BB","current_city":"","distance":"","age":23,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892249230.png?x-oss-process=style/imageStyle","images":[{"img_id":478,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892249230.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Three","sex":2,"token":"303A0E687173E9DA7A40FF3313A0A5A3","current_city":"","distance":"","age":21,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892245196.png?x-oss-process=style/imageStyle","images":[{"img_id":477,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892245196.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Two","sex":2,"token":"6D89AE40FFAA62D8CEB7D88B8BC2B974","current_city":"","distance":"","age":31,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle","images":[{"img_id":476,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892241043.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"One","sex":2,"token":"D195F6FE8194F38E883EF20FAB1D264D","current_city":"","distance":"","age":24,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155891883534.png?x-oss-process=style/imageStyle","images":[{"img_id":475,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155891883534.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Love","sex":2,"token":"EB49722D48CBBCBD1D255EEFE171CC90","current_city":"","distance":"","age":21,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868217816.png?x-oss-process=style/imageStyle","images":[{"img_id":474,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868217816.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Poli","sex":1,"token":"F6D2437D2B09149DD62536226AD87A40","current_city":"","distance":"","age":23,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868214683.png?x-oss-process=style/imageStyle","images":[{"img_id":473,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868214683.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]},{"super_like_you":"no","username":"Inmlo","sex":2,"token":"59C8EC367A458945FE2B5531F486FB94","current_city":"","distance":"","age":26,"headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868211457.png?x-oss-process=style/imageStyle","images":[{"img_id":472,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190524/155868211457.png?x-oss-process=style/imageStyle2"}],"personal_tags":[{"subtitle":"constellation","msg":"Gemini"}]}]
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
         * super_like_you : no
         * username : Seven
         * sex : 2
         * token : FF77CEED9EE66D50498D0EA6A5652339
         * current_city :
         * distance :
         * age : 21
         * headimg : https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle
         * images : [{"img_id":481,"img_url":"https://resource.golinkyou.com/public/uploads/headimgurl/20190527/155892263439.png?x-oss-process=style/imageStyle2"}]
         * personal_tags : [{"subtitle":"constellation","msg":"Gemini"}]
         */

        private String super_like_you;
        private String username;
        private int sex;
        private String token;
        private String current_city;
        private String distance;
        private int age;
        private String headimg;
        private List<ImagesBean> images;
        private List<PersonalTagsBean> personal_tags;

        public String getSuper_like_you() {
            return super_like_you;
        }

        public void setSuper_like_you(String super_like_you) {
            this.super_like_you = super_like_you;
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

        public static class ImagesBean implements Serializable{
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

        public static class PersonalTagsBean implements Serializable{
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
