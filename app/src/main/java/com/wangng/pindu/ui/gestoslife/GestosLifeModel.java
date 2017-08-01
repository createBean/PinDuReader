package com.wangng.pindu.ui.gestoslife;

import java.io.Serializable;

/**
 * Created by yu on 2017/4/5.
 */

public class GestosLifeModel implements Serializable {
    private String title;
    private String time;
    private String detailUrl;
    private String detailUrl2;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl2() {
        return detailUrl2;
    }

    public void setDetailUrl2(String detailUrl2) {
        this.detailUrl2 = detailUrl2;
    }
}
