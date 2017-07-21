package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.ThemeSubConstract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.StoriesBean;
import com.phonesj.news.model.bean.zhihu.ThemeSubBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Phone on 2017/7/21.
 */

public class ThemeSubPresenter extends RxPresenter<ThemeSubConstract.View> implements ThemeSubConstract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ThemeSubPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getThemeSubData(int id) {
        addSubscribe(mDataManager
            .fetchThemeSubInfo(id)
            .compose(RxUtil.<ThemeSubBean>rxSchedulerHelper())
            .map(new Function<ThemeSubBean, ThemeSubBean>() {
                @Override
                public ThemeSubBean apply(@NonNull ThemeSubBean themeSubBean) throws Exception {
                    for (StoriesBean bean : themeSubBean.getStories()) {
                        //从数据库中查询该文章之前是否阅读过
                        bean.setReadState(mDataManager.queryNewsId(bean.getId()));
                    }
                    return themeSubBean;
                }
            })
            .subscribeWith(new CommonSubscriber<ThemeSubBean>(mView) {
                @Override
                public void onNext(ThemeSubBean themeSubBean) {
                    mView.showContent(themeSubBean);
                }
            }));
    }

    @Override
    public void insertReadStateToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
