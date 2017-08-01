package com.phonesj.news.presenter.wechat;

import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.wechat.WechatMainContract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.wechat.WXItemBean;
import com.phonesj.news.model.event.SearchEvent;
import com.phonesj.news.model.http.response.WXHttpResponse;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Phone on 2017/8/1.
 */

public class WechatMainPresenter extends RxPresenter<WechatMainContract.View> implements WechatMainContract.Presenter {

    private static final int NUM_OF_PAGE = 20;

    private DataManager mDataManager;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public WechatMainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(WechatMainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus
            .getDefault()
            .toFlowable(SearchEvent.class)
            .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
            .filter(new Predicate<SearchEvent>() {
                @Override
                public boolean test(@NonNull SearchEvent searchEvent) throws Exception {
                    return searchEvent.getType() == Constants.TYPE_WECHAT;
                }
            })
            .map(new Function<SearchEvent, String>() {
                @Override
                public String apply(@NonNull SearchEvent searchEvent) throws Exception {
                    return searchEvent.getQuery();
                }
            })
            .subscribeWith(new CommonSubscriber<String>(mView, "搜索失败") {
                @Override
                public void onNext(String s) {
                    queryStr = s;
                    getSearchWechatData();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();
                }
            }));
    }

    @Override
    public void getWechatData() {
        queryStr = null;
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchWXInfo(NUM_OF_PAGE, currentPage)
            .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<WXItemBean>>handleWechatResult())
            .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView, "访问微信精选失败") {
                @Override
                public void onNext(List<WXItemBean> info) {
                    mView.showContent(info);
                }
            }));
    }

    @Override
    public void getMoreWechatData() {
        Flowable<WXHttpResponse<List<WXItemBean>>> flowable;
        currentPage++;
        if (queryStr != null) {
            flowable = mDataManager.fetchWXSearchInfo(NUM_OF_PAGE, currentPage, queryStr);
        } else {
            flowable = mDataManager.fetchWXInfo(NUM_OF_PAGE, currentPage);
        }
        addSubscribe(flowable
            .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<WXItemBean>>handleWechatResult())
            .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView, "没有更多数据") {
                @Override
                public void onNext(List<WXItemBean> info) {
                    mView.showMoreContent(info);
                }
            }));
    }

    public void getSearchWechatData() {
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchWXSearchInfo(NUM_OF_PAGE, currentPage, queryStr)
            .compose(RxUtil.<WXHttpResponse<List<WXItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<WXItemBean>>handleWechatResult())
            .subscribeWith(new CommonSubscriber<List<WXItemBean>>(mView, "访问微信精选失败") {
                @Override
                public void onNext(List<WXItemBean> info) {
                    mView.showContent(info);
                }
            }));
    }
}
