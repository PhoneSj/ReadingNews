package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;

/**
 * Created by Phone on 2017/7/19.
 */

public interface ZhihuDetailContract {

    interface View extends BaseView {

        void showContent(ZhihuDetailBean info);

        void showExtraInfo(ZhihuDetailExtraBean info);

        void setLikeButtonState(boolean isLike);

    }

    interface Presenter extends BasePresenter<View> {

        void getDetailData(int id);

        void getDetailExtraData(int id);

        void inertLikeData();

        void deleteLikeData();

        void queryLikeData(int id);

        void getNoImageState();

        void getAutoCacheState();
    }
}
