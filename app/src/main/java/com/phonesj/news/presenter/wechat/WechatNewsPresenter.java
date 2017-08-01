package com.phonesj.news.presenter.wechat;

import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.wechat.WechatNewsContract;
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

public class WechatNewsPresenter extends RxPresenter<WechatNewsContract.View> implements WechatNewsContract.Presenter {

    private static final int NUM_OF_PAGE = 20;

    private DataManager mDataManager;

    private int currentPage = 1;
    private String queryStr = null;
    private String type;

    @Inject
    public WechatNewsPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(WechatNewsContract.View view) {
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
                    getSearchWechatData(type);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();
                }
            }));
    }

    @Override
    public void getWechatData(String type) {
        this.type = type;
        queryStr = null;
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchWXInfo(type, NUM_OF_PAGE, currentPage)
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
    public void getMoreWechatData(String type) {
        Flowable<WXHttpResponse<List<WXItemBean>>> flowable;
        currentPage++;
        if (queryStr != null) {
            flowable = mDataManager.fetchWXSearchInfo(type, NUM_OF_PAGE, currentPage, queryStr);
        } else {
            flowable = mDataManager.fetchWXInfo(type, NUM_OF_PAGE, currentPage);
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

    public void getSearchWechatData(String type) {
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchWXSearchInfo(type, NUM_OF_PAGE, currentPage, queryStr)
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
