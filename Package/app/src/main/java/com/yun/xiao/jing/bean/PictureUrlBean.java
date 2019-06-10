package com.yun.xiao.jing.bean;

import java.io.Serializable;

public class PictureUrlBean implements Serializable {
    /**
     * origin : https://resource.golinkyou.com/public/uploads/dynamic/20190531/155928177399.png
     * thumb : https://resource.golinkyou.com/public/uploads/dynamic/20190531/155928177399.png?x-oss-process=style/imageStyle
     */

    private String origin;
    private String thumb;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
