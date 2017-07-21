package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.ThemeBean;

/**
 * Created by Phone on 2017/7/19.
 */

public interface ThemeConstract {

    interface View extends BaseView {

        void showContent(ThemeBean info);

    }

    interface Presenter extends BasePresenter<View> {

        void getThemeData();

    }
}
