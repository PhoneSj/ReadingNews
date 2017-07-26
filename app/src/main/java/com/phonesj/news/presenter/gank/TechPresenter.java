package com.phonesj.news.presenter.gank;

import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.gank.TechContract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.model.bean.gank.GankSearchBean;
import com.phonesj.news.model.event.SearchEvent;
import com.phonesj.news.model.http.response.GankHttpResponse;
import com.phonesj.news.ui.gank.fragment.GankMainFragment;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Phone on 2017/7/26.
 */

public class TechPresenter extends RxPresenter<TechContract.View> implements TechContract.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;
    private String currentTech = GankMainFragment.titles[0];
    private int currentType = Constants.TYPE_ANDROID;
    private String queryStr;//搜索的字符串

    @Inject
    public TechPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(TechContract.View view) {
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
                    return searchEvent.getType() == currentType;
                }
            })
            .map(new Function<SearchEvent, String>() {
                @Override
                public String apply(@NonNull SearchEvent searchEvent) throws Exception {
                    return searchEvent.getQuery();
                }
            })
            .subscribeWith(new CommonSubscriber<String>(mView) {
                @Override
                public void onNext(String s) {
                    queryStr = s;
                    getSearchTechData();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();
                }
            }));
    }

    private void getSearchTechData() {
        currentPage = 1;
        addSubscribe(mDataManager
            .fetchGankSearchList(queryStr, currentTech, NUM_OF_PAGE, currentPage)
            .compose(RxUtil.<GankHttpResponse<List<GankSearchBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankSearchBean>>handleResult())
            .map(new Function<List<GankSearchBean>, List<GankItemBean>>() {
                @Override
                public List<GankItemBean> apply(@NonNull List<GankSearchBean> info) throws Exception {
                    List<GankItemBean> newInfo = new ArrayList<GankItemBean>();
                    for (GankSearchBean bean : info) {
                        GankItemBean newBean = new GankItemBean();
                        newBean.set_id(bean.getGanhuo_id());
                        newBean.setDesc(bean.getDesc());
                        newBean.setPublishedAt(bean.getPublishedAt());
                        newBean.setUrl(bean.getUrl());
                        newBean.setWho(bean.getWho());
                        newInfo.add(newBean);
                    }
                    return newInfo;
                }
            })
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {

                @Override
                public void onNext(List<GankItemBean> info) {
                    mView.showContent(info);
                }
            }));
    }

    @Override
    public void getGankData(String tech, int type) {
        queryStr = null;
        currentPage = 1;
        currentTech = tech;
        currentType = type;
        addSubscribe(mDataManager
            .fetchTechList(tech, NUM_OF_PAGE, currentPage)
            .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankItemBean>>handleResult())
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {
                @Override
                public void onNext(List<GankItemBean> gankItemBeen) {
                    mView.showContent(gankItemBeen);
                }
            }));
    }

    @Override
    public void getMoreGankData(String tech) {
        addSubscribe(mDataManager
            .fetchTechList(tech, NUM_OF_PAGE, ++currentPage)
            .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankItemBean>>handleResult())
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {
                @Override
                public void onNext(List<GankItemBean> gankItemBeen) {
                    mView.showMoreContent(gankItemBeen);
                }
            }));
    }

    @Override
    public void getGrilImage() {
        addSubscribe(mDataManager
            .fetchRandomGril(1)
            .compose(RxUtil.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GankItemBean>>handleResult())
            .subscribeWith(new CommonSubscriber<List<GankItemBean>>(mView) {
                @Override
                public void onNext(List<GankItemBean> gankItemBeen) {
                    mView.showGrilImage(gankItemBeen.get(0).getUrl(), gankItemBeen.get(0).getWho());
                }
            }));
    }
}
