package com.phonesj.news.presenter.main;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.main.MainContract;
import com.phonesj.news.model.DataManager;
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
    public void setNightModeState(boolean state) {

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

    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {

    }
}
