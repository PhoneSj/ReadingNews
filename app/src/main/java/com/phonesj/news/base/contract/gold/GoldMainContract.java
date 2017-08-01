package com.phonesj.news.base.contract.gold;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.gold.GoldManagerBean;
import com.phonesj.news.model.bean.gold.GoldManagerItemBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/29.
 */

public interface GoldMainContract {

    interface View extends BaseView {

        void updateTab(List<GoldManagerItemBean> info);

        void jumpToManager(GoldManagerBean bean);
    }

    interface Presenter extends BasePresenter<View> {

        void initManagerList();

        void setManagerList();
    }
}
