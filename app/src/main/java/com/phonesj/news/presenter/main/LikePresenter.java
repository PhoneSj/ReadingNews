package com.phonesj.news.presenter.main;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.main.LikeContract;
import com.phonesj.news.model.DataManager;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/21.
 */

public class LikePresenter extends RxPresenter<LikeContract.View> implements LikeContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public LikePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getLikeData() {
        mView.showContent(mDataManager.queryLikeAll());
    }

    @Override
    public void deleteLikeData() {

    }
}
