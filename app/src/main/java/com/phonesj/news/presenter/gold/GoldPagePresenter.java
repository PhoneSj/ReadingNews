package com.phonesj.news.presenter.gold;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.gold.GoldPageContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.gold.GoldListBean;
import com.phonesj.news.model.http.response.GoldHttpResponse;
import com.phonesj.news.util.LogUtil;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldPagePresenter extends RxPresenter<GoldPageContract.View> implements GoldPageContract.Presenter {

    private static final int NUM_EACH_PAGE = 20;
    private static final int NUM_HOT_LIMIT = 3;

    private DataManager mDataManager;

    private String mType;
    private int currentPage = 0;

    private List<GoldListBean> totalList = new ArrayList<>();
    private boolean isHotList = true;

    @Inject
    public GoldPagePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getGoldData(String type) {
        mType = type;
        //普通信息
        Flowable<List<GoldListBean>> list = mDataManager
            .fetchGoldList(mType, NUM_EACH_PAGE, currentPage++)
            .compose(RxUtil.<GoldHttpResponse<List<GoldListBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GoldListBean>>handleGoldResult());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);//当前日期前推3天
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        //热点信息
        Flowable<List<GoldListBean>> hotList = mDataManager
            .fetchGoldHotList(mType, dateTime, NUM_HOT_LIMIT)
            .compose(RxUtil.<GoldHttpResponse<List<GoldListBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GoldListBean>>handleGoldResult());

        addSubscribe(Flowable
            .concat(list, hotList)
            .subscribeWith(new CommonSubscriber<List<GoldListBean>>(mView, "访问稀土掘金失败") {
                @Override
                public void onNext(List<GoldListBean> info) {
                    if (!isHotList) {
                        isHotList = false;
                        totalList.addAll(info);
                        LogUtil.w("hotList.size:" + info.size());
                    } else {
                        isHotList = true;
                        totalList.addAll(info);
                        mView.showContent(totalList);
                        LogUtil.w("list.size:" + info.size());
                    }
                }
            }));
    }

    @Override
    public void getMoreGoldData() {
        addSubscribe(mDataManager
            .fetchGoldList(mType, NUM_EACH_PAGE, currentPage++)
            .compose(RxUtil.<GoldHttpResponse<List<GoldListBean>>>rxSchedulerHelper())
            .compose(RxUtil.<List<GoldListBean>>handleGoldResult())
            .subscribeWith(new CommonSubscriber<List<GoldListBean>>(mView, "访问稀土掘金失败") {
                @Override
                public void onNext(List<GoldListBean> info) {
                    totalList.addAll(info);
                    mView.showMoreContent(totalList, totalList.size(), totalList.size() + NUM_EACH_PAGE);
                }
            }));
    }
}
