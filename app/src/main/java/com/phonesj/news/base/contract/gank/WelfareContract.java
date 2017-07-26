package com.phonesj.news.base.contract.gank;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.gank.GankItemBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/26.
 */

public interface WelfareContract {
    interface View extends BaseView {
        void showContent(List<GankItemBean> info);

        void showMoreContent(List<GankItemBean> info);
    }

    interface Presenter extends BasePresenter<View> {
        void getWelfareData();

        void getMoreWelfareData();
    }
}
