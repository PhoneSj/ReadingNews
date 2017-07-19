package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.SectionConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/19.
 */

public class SectionPresenter extends RxPresenter<SectionConstract.View> implements SectionConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SectionPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getSectionData() {
        addSubscribe(mDataManager
            .fetchSectionInfo()
            .compose(RxUtil.<SectionBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<SectionBean>(mView) {
                @Override
                public void onNext(SectionBean sectionBean) {
                    mView.showContent(sectionBean);
                }
            }));
    }
}
