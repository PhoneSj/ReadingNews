package com.phonesj.news.base.contract.main;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by Phone on 2017/7/17.
 */

public interface MainContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

        void checkPermissions(RxPermissions rxPermissions);

        void setNightModeState(boolean state);

        void setCurrentItem(int key);

        int getCurrentItem();
    }
}
