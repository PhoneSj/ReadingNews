package com.phonesj.news.presenter.gold;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.gold.GoldMainContract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.gold.GoldManagerBean;
import com.phonesj.news.model.bean.gold.GoldManagerItemBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

import io.realm.RealmList;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldMainPresenter extends RxPresenter<GoldMainContract.View> implements GoldMainContract.Presenter {

    private DataManager mDataManager;

    private RealmList<GoldManagerItemBean> mList;

    @Inject
    public GoldMainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(GoldMainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus
            .getDefault()
            .toFlowable(GoldManagerBean.class)
            .compose(RxUtil.<GoldManagerBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<GoldManagerBean>(mView, "设置失败", false) {
                @Override
                public void onNext(GoldManagerBean bean) {
                    mDataManager.updateGoldManagerList(bean);
                    mView.updateTab(bean.getManagerList());
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();
                }
            }));
    }

    @Override
    public void initManagerList() {
        if (mDataManager.getManagerPoint()) {
            initList();
            mDataManager.updateGoldManagerList(new GoldManagerBean(mList));
        } else {
            if (mDataManager.getGoldManagerList() == null) {
                initList();
                mDataManager.updateGoldManagerList(new GoldManagerBean(mList));
            } else {
                mList = mDataManager.getGoldManagerList().getManagerList();
            }
        }
        mView.updateTab(mList);
    }

    @Override
    public void setManagerList() {
        mView.jumpToManager(mDataManager.getGoldManagerList());
    }

    private void initList() {
        mList = new RealmList<>();
        mList.add(new GoldManagerItemBean(0, true));
        mList.add(new GoldManagerItemBean(1, true));
        mList.add(new GoldManagerItemBean(2, true));
        mList.add(new GoldManagerItemBean(3, true));
        mList.add(new GoldManagerItemBean(4, false));
        mList.add(new GoldManagerItemBean(5, false));
        mList.add(new GoldManagerItemBean(6, false));
        mList.add(new GoldManagerItemBean(7, false));
    }
}
