package com.phonesj.news.base.contract.main;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.LikeBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/21.
 */

public interface LikeContract {
    interface View extends BaseView {
        void showContent(List<LikeBean> info);
    }

    interface Presenter extends BasePresenter<View> {

        void getLikeData();

        void deleteLikeData();
    }
}
