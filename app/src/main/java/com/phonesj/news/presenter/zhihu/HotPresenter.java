package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.HotConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/19.
 */

public class HotPresenter extends RxPresenter<HotConstract.View> implements HotConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public HotPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getHotData() {
        addSubscribe(mDataManager
            .fetchHotInfo()
            .compose(RxUtil.<HotBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<HotBean>(mView) {
                @Override
                public void onNext(HotBean hotBean) {
                    mView.showContent(hotBean);
                }
            }));
    }
}
