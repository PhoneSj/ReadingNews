package com.phonesj.news.model.bean.zhihu;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Phone on 2017/7/20.
 * <p>用于数据库的bean</p>
 */

public class LikeBean extends RealmObject {

    @PrimaryKey
    private String id;

    private String image;

    private String title;

    private String url;

    private int type;

    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
