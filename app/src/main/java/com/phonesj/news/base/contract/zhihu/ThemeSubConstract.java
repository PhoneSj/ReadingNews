package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.ThemeSubBean;

/**
 * Created by Phone on 2017/7/21.
 */

public interface ThemeSubConstract {

    interface View extends BaseView {
        void showContent(ThemeSubBean info);
    }

    interface Presenter extends BasePresenter<View> {
        void getThemeSubData(int id);

        void insertReadStateToDB(int id);
    }
}
