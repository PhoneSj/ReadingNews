package com.phonesj.news.base.contract.gold;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.gold.GoldListBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/29.
 */

public interface GoldPageContract {

    interface View extends BaseView {
        void showContent(List<GoldListBean> info);

        void showMoreContent(List<GoldListBean> info, int start, int end);
    }

    interface Presenter extends BasePresenter<View> {
        void getGoldData(String type);

        void getMoreGoldData();
    }
}
