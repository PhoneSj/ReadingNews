package com.phonesj.news.model.bean.main;

/**
 * Created by Phone on 2017/7/24.
 * <p>该javabean没有对应的网络数据，这里只是模拟练习</p>
 */

public class VersionBean {

    private int versionCode;
    private String versionName;
    private long size;
    private String description;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
