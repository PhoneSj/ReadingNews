package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.SectionSubConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.SectionSubBean;
import com.phonesj.news.model.bean.zhihu.StoriesBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Phone on 2017/7/21.
 */

public class SectionSubPresenter extends RxPresenter<SectionSubConstract.View> implements SectionSubConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SectionSubPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getSectionSubData(int id) {
        addSubscribe(mDataManager
            .fetchSectionSubInfo(id)
            .compose(RxUtil.<SectionSubBean>rxSchedulerHelper())
            .map(new Function<SectionSubBean, SectionSubBean>() {
                @Override
                public SectionSubBean apply(@NonNull SectionSubBean sectionSubBean) throws Exception {
                    for (StoriesBean bean : sectionSubBean.getStories()) {
                        bean.setReadState(mDataManager.queryNewsId(bean.getId()));
                    }
                    return sectionSubBean;
                }
            })
            .subscribeWith(new CommonSubscriber<SectionSubBean>(mView) {
                @Override
                public void onNext(SectionSubBean sectionSubBean) {
                    mView.showContent(sectionSubBean);
                }
            }));
    }

    @Override
    public void insertReadStateToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
