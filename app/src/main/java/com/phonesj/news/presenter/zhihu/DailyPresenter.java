package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.DailyConstract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.util.DateUtil;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import android.util.Log;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyPresenter extends RxPresenter<DailyConstract.View> implements DailyConstract.Presenter {

    private DataManager mDataManager;

    private int topCount = 0;//轮播图页面数量
    private int currentTopCount = 0;//轮播图当前显示的第几页

    private Disposable intervalSubscription;//轮播图控制器

    private static final int INTERVAL_INSTANCE = 5;//轮播每页停留时长

    @Inject
    public DailyPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(DailyConstract.View view) {
        super.attachView(view);
        registerEvent();
    }

    /**
     * 注册RxBus监听事件
     */
    private void registerEvent() {
        // TODO: 2017/7/18 监听CalenderDate
        addSubscribe(RxBus
            .getDefault()
            .toFlowable(CalendarDay.class)
            .subscribeOn(Schedulers.io())
            .map(new Function<CalendarDay, String>() {
                @Override
                public String apply(@NonNull CalendarDay calendarDay) throws Exception {
                    StringBuilder sb = new StringBuilder();
                    String year = String.valueOf(calendarDay.getYear());
                    String month = String.valueOf(calendarDay.getMonth());
                    String day = String.valueOf(calendarDay.getDate());
                    if (month.length() < 2) {
                        month = "0" + month;
                    }
                    if (day.length() < 2) {
                        day = "0" + day;
                    }
                    return sb.append(year).append(month).append(day).toString();
                }
            })
            .filter(new Predicate<String>() {
                @Override
                public boolean test(@NonNull String s) throws Exception {
                    if (s.equals(DateUtil.getTomorrowDate())) {
                        getDailyData();
                        return false;
                    }
                    return true;
                }
            })
            .observeOn(Schedulers.io())
            .flatMap(new Function<String, Flowable<DailyBeforeBean>>() {
                @Override
                public Flowable<DailyBeforeBean> apply(@NonNull String date) throws Exception {
                    return mDataManager.fetchDailyBeforeInfo(date);
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Function<DailyBeforeBean, DailyBeforeBean>() {
                @Override
                public DailyBeforeBean apply(@NonNull DailyBeforeBean dailyBeforeBean) throws Exception {
                    List<DailyBean.StoriesBean> list = dailyBeforeBean.getStories();
                    for (DailyBean.StoriesBean bean : list) {
                        bean.setReadState(mDataManager.queryNewsId(bean.getId()));
                    }
                    return dailyBeforeBean;
                }
            })
            .subscribeWith(new CommonSubscriber<DailyBeforeBean>(mView) {
                @Override
                public void onNext(DailyBeforeBean dailyBeforeBean) {
                    int year = Integer.valueOf(dailyBeforeBean.getDate().substring(0, 4));
                    int month = Integer.valueOf(dailyBeforeBean.getDate().substring(4, 6));
                    int day = Integer.valueOf(dailyBeforeBean.getDate().substring(6, 8));
                    mView.showMoreContent(String.format("%d年%d月%d日", year, month, day), dailyBeforeBean);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    registerEvent();//出现错误，重新注册
                }
            }));
    }

    @Override
    public void getDailyData() {
        addSubscribe(mDataManager
            .fetchDailyInfo()
            .compose(RxUtil.<DailyBean>rxSchedulerHelper())
            .map(new Function<DailyBean, DailyBean>() {
                @Override
                public DailyBean apply(@NonNull DailyBean dailyBean) throws Exception {
                    List<DailyBean.StoriesBean> storiesBeanList = dailyBean.getStories();
                    for (DailyBean.StoriesBean bean : storiesBeanList) {
                        bean.setReadState(mDataManager.queryNewsId(bean.getId()));
                    }
                    return dailyBean;
                }
            })
            .subscribeWith(new CommonSubscriber<DailyBean>(mView) {
                @Override
                public void onNext(DailyBean dailyBean) {
                    topCount = dailyBean.getTop_stories().size();
                    mView.showContent(dailyBean);
                }
            }));
    }

    @Override
    public void getBeforeData(String date) {
        addSubscribe(mDataManager
            .fetchDailyBeforeInfo(date)
            .compose(RxUtil.<DailyBeforeBean>rxSchedulerHelper())
            .map(new Function<DailyBeforeBean, DailyBeforeBean>() {
                @Override
                public DailyBeforeBean apply(@NonNull DailyBeforeBean dailyBeforeBean) throws Exception {
                    List<DailyBean.StoriesBean> storiesBeanList = dailyBeforeBean.getStories();
                    for (DailyBean.StoriesBean bean : storiesBeanList) {
                        bean.setReadState(mDataManager.queryNewsId(bean.getId()));
                    }
                    return dailyBeforeBean;
                }
            })
            .subscribeWith(new CommonSubscriber<DailyBeforeBean>(mView) {
                @Override
                public void onNext(DailyBeforeBean dailyBeforeBean) {
                    int year = Integer.valueOf(dailyBeforeBean.getDate().substring(0, 4));
                    int month = Integer.valueOf(dailyBeforeBean.getDate().substring(4, 6));
                    int day = Integer.valueOf(dailyBeforeBean.getDate().substring(6, 8));
                    mView.showMoreContent(String.format("%d年%d月%d日", year, month, day), dailyBeforeBean);
                }
            }));
    }

    @Override
    public void startInterval() {
        Log.w("phone", "startInterval");
        if (intervalSubscription != null && !intervalSubscription.isDisposed()) {
            //轮播图正在运行
            return;
        }
        intervalSubscription = Flowable
            .interval(INTERVAL_INSTANCE, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .compose(RxUtil.<Long>rxSchedulerHelper())
            .subscribe(new Consumer<Long>() {
                @Override
                public void accept(@NonNull Long aLong) throws Exception {
                    Log.i("phone", "accept");
                    if (currentTopCount == topCount) {
                        currentTopCount = 0;
                    }
                    mView.doInterval(currentTopCount++);
                }
            });
        addSubscribe(intervalSubscription);
    }

    @Override
    public void stopInterval() {
        Log.w("phone", "stopInterval");
        //停止轮播图运行
        if (intervalSubscription != null && !intervalSubscription.isDisposed()) {
            intervalSubscription.dispose();
        }
    }

    @Override
    public void insertReadStateToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
