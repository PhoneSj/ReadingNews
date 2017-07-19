package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.ZhihuDetailContract;
import com.phonesj.news.model.DataManager;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/19.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ZhihuDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getDetailData(int id) {

    }

    @Override
    public void getDetailExtraData(int id) {

    }

    @Override
    public void inertLikeData() {

    }

    @Override
    public void deleteLikeData() {

    }

    @Override
    public void queryLikeData(int id) {

    }

    @Override
    public void getNoImageState() {

    }

    @Override
    public void getAutoCacheState() {

    }
}
