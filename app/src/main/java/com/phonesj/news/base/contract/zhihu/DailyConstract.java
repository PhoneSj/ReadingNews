package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;

/**
 * Created by Phone on 2017/7/18.
 */

public interface DailyConstract {

    interface View extends BaseView {

        void showContent(DailyBean info);

        void showMoreContent(String date, DailyBeforeBean info);

        void doInterval(int currentCount);

    }

    interface Presenter extends BasePresenter<View> {

        void getDailyData();

        void getBeforeData(String data);

        void startInterval();

        void stopInterval();

        void insertReadStateToDB(int id);
    }
}
