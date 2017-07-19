package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.ThemeConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/19.
 */

public class ThemePresenter extends RxPresenter<ThemeConstract.View> implements ThemeConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ThemePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getThemeData() {
        addSubscribe(mDataManager
            .fetchThemeInfo()
            .compose(RxUtil.<ThemeBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ThemeBean>(mView) {
                @Override
                public void onNext(ThemeBean themeBean) {
                    mView.showContent(themeBean);
                }
            }));
        ;
    }
}
