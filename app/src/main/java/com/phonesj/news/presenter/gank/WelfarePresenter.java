package com.phonesj.news.presenter.gank;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.gank.WelfareContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.model.http.response.GankHttpResponse;
import com.phonesj.news.util.LogUtil;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/26.
 */

public class WelfarePresenter extends RxPresenter<WelfareContract.View> implements WelfareContract.Presenter {

    private DataManager mDataManager;
    public static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;

    @Inject
    public WelfarePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getWelfareData() {
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchGrilList(NUM_OF_PAGE, currentPage)
            .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankItemBean>>handleResult())
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {
                @Override
                public void onNext(List<GankItemBean> info) {
                    mView.showContent(info);
                    mView.stateMain();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    LogUtil.w("==============");
                }
            }));
    }

    @Override
    public void getMoreWelfareData() {
        addSubscribe(mDataManager
            .fetchGrilList(NUM_OF_PAGE, ++currentPage)
            .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankItemBean>>handleResult())
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {
                @Override
                public void onNext(List<GankItemBean> info) {
                    mView.showMoreContent(info);
                }
            }));
    }

}
