package com.phonesj.news.presenter.main;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.main.WelcomeContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.main.WelcomeBean;
import com.phonesj.news.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Phone on 2017/7/17.
 * <p>RxPresenter封装公有方法，WelcomeConstarct.Presenter封装Welcome页面特定的方法</p>
 */

public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private static final String RES = "1080*1776";
    private static final int COUNT_DOWN_TIME = 2200;
    private DataManager dataManager;

    @Inject
    public WelcomePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getWelcomeData() {
        addSubscribe(dataManager
            .fetchWelcomeInfo(RES)
            .compose(RxUtil.<WelcomeBean>rxSchedulerHelper())
            .subscribe(new Consumer<WelcomeBean>() {
                @Override
                public void accept(@NonNull WelcomeBean welcomeBean) throws Exception {
                    //网络响应成功，显示数据
                    mView.showContent(welcomeBean);
                    startCountDown();
                }
            }, new Consumer<Throwable>() {

                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    //网络响应失败，直接跳转页面
                    mView.jumpToMain();
                }
            }));
    }

    /**
     * 倒计时
     */
    private void startCountDown() {
        addSubscribe(Flowable
            .timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
            .compose(RxUtil.<Long>rxSchedulerHelper())
            .subscribe(new Consumer<Long>() {
                @Override
                public void accept(@NonNull Long aLong) throws Exception {
                    mView.jumpToMain();//跳转页面
                }
            }));
    }
}
