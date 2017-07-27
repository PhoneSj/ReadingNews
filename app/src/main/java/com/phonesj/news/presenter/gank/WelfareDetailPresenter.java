package com.phonesj.news.presenter.gank;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.gank.WelfareDetailContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.LikeBean;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/27.
 */

public class WelfareDetailPresenter extends RxPresenter<WelfareDetailContract.View> implements WelfareDetailContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public WelfareDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public boolean queryLikeId(String id) {
        return mDataManager.queryLikeId(id);
    }

    @Override
    public void deleteLikeBean(String id) {
        mDataManager.deleteLikeBean(id);
    }

    @Override
    public void insertLikeBean(LikeBean bean) {
        mDataManager.inertLikeBean(bean);
    }
}
