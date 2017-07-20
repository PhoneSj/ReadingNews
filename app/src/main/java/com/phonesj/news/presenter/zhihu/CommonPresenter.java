package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.CommonConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/20.
 */

public class CommonPresenter extends RxPresenter<CommonConstract.View> implements CommonConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public CommonPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getCommonData(int id, int kind) {
        if (kind == Constants.ZHIHU_COMMON_TYPE_SHORT) {
            addSubscribe(mDataManager
                .fetchShortCommonInfo(id)
                .compose(RxUtil.<CommonBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        mView.showContent(commonBean);
                    }
                }));
        } else if (kind == Constants.ZHIHU_COMMON_TYPE_LONG) {
            addSubscribe(mDataManager
                .fetchLongCommonInfo(id)
                .compose(RxUtil.<CommonBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        mView.showContent(commonBean);
                    }
                }));
        }
    }
}
