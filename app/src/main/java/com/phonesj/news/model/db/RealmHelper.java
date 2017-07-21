package com.phonesj.news.model.db;

import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.model.bean.zhihu.ReadStateBean;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

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

    @Override
    public void inertLikeBean(LikeBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    @Override
    public void deleteLikeBean(String id) {
        LikeBean bean = mRealm.where(LikeBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (bean != null) {
            bean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    @Override
    public boolean queryLikeId(String id) {
        RealmResults<LikeBean> realmResults = mRealm
            .where(LikeBean.class)
            .equalTo("id", id)
            .findAll();
        if (realmResults != null && realmResults.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<LikeBean> queryLikeAll() {
        RealmResults<LikeBean> realmResults = mRealm
            .where(LikeBean.class)
            .findAllSorted("time", Sort.ASCENDING);
        return mRealm.copyFromRealm(realmResults);
    }
}
