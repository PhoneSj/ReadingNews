package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.CommonBean;

/**
 * Created by Phone on 2017/7/20.
 */

public interface CommonConstract {

    interface View extends BaseView {
        void showContent(CommonBean info);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 从网络上获取评论信息
         *
         * @param id   文章id
         * @param kind 评论类型：长评论、短评论
         */
        void getCommonData(int id, int kind);
    }
}
