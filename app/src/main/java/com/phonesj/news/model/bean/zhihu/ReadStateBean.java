package com.phonesj.news.model.bean.zhihu;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Phone on 2017/7/18.
 * <p>用于数据库的bean</p>
 */

public class ReadStateBean extends RealmObject {

    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
