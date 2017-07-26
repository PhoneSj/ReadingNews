package com.phonesj.news.base.contract.gank;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.gank.GankItemBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/26.
 */

public interface TechContract {

    interface View extends BaseView {
        void showContent(List<GankItemBean> info);

        void showMoreContent(List<GankItemBean> info);

        void showGrilImage(String url, String copyright);
    }

    interface Presenter extends BasePresenter<View> {
        void getGankData(String tech, int type);

        void getMoreGankData(String tech);

        void getGrilImage();
    }
}
