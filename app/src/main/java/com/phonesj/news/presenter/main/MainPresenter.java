package com.phonesj.news.presenter.main;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.main.MainContract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.event.NightModeEvent;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/17.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    /**
     * 注册夜间主题模式事件
     */
    private void registerEvent() {
        addSubscribe(RxBus
            .getDefault()
            .toFlowable(NightModeEvent.class)
            .compose(RxUtil.<NightModeEvent>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<NightModeEvent>(mView) {
                @Override
                public void onNext(NightModeEvent nightModeEvent) {
                    mView.useNightMode(nightModeEvent.isNightMode());
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();
                    mView.showErrorMsg("主题切换失败");
                }
            }));
    }

    @Override
    public void setNightModeState(boolean state) {
        dataManager.setNightModeState(state);
    }

    @Override
    public void setCurrentItem(int key) {
        dataManager.setCurrentItem(key);
    }

    @Override
    public int getCurrentItem() {
        return dataManager.getCurrentItem();
    }

    @Override
    public void checkVersion(String currentVersion) {
        // TODO: 2017/7/24  
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        // TODO: 2017/7/24
    }
}
