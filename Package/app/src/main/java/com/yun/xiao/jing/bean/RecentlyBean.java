package com.yun.xiao.jing.bean;

import java.io.Serializable;
import java.util.List;

public class RecentlyBean implements Serializable {

    /**
     * code : 620
     * msg : Home browsing list successful
     * info : [{"create_time":1559267838,"status":0,"violations":0,"username":"123","sex":1,"token":"A5E38F550243D9B30997678B61B7B429","headimg":"https://resource.golinkyou.com/public/uploads/headimgurl/20190531/155926712727.png?x-oss-process=style/imageStyle","imgStatus":1}]
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

    public static class InfoBean {
        /**
         * create_time : 1559267838
         * status : 0
         * violations : 0
         * username : 123
         * sex : 1
         * token : A5E38F550243D9B30997678B61B7B429
         * headimg : https://resource.golinkyou.com/public/uploads/headimgurl/20190531/155926712727.png?x-oss-process=style/imageStyle
         * imgStatus : 1
         */

        private int create_time;
        private int status;
        private int violations;
        private String username;
        private int sex;
        private String token;
        private String headimg;
        private int imgStatus;

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getViolations() {
            return violations;
        }

        public void setViolations(int violations) {
            this.violations = violations;
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

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public int getImgStatus() {
            return imgStatus;
        }

        public void setImgStatus(int imgStatus) {
            this.imgStatus = imgStatus;
        }
    }
}
