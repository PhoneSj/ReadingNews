package com.phonesj.news.model.db;

import com.phonesj.news.model.bean.zhihu.ReadStateBean;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Phone on 2017/7/14.
 */

public class RealmHelper implements DBHelper {

    private static final String DB_NAME = "main.realm";

    private Realm mRealm;

    @Inject
    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .name(DB_NAME)
            .build());
    }

    @Override
    public void insertNewsId(int id) {
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    @Override
    public boolean queryNewsId(int id) {
        RealmResults<ReadStateBean> result = mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean bean : result) {
            if (bean.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
