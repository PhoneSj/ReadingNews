package com.phonesj.news.base.contract.main;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.main.WelcomeBean;

/**
 * Created by Phone on 2017/7/17.
 */

public interface WelcomeContract {

    interface View extends BaseView {
        void showContent(WelcomeBean welcomeBean);

        void jumpToMain();
    }

    interface Presenter extends BasePresenter<View> {
        void getWelcomeData();
    }
}
