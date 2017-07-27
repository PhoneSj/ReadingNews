package com.phonesj.news.base.contract.gank;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.LikeBean;

/**
 * Created by Phone on 2017/7/27.
 */

public interface TechDetailContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        boolean getNoImageState();

        boolean getAutoCacheState();

        boolean queryLikeId(String id);

        void deleteLikeBean(String id);

        void insertLikeBean(LikeBean bean);
    }
}
