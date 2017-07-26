package com.phonesj.news.base;

import com.phonesj.news.app.App;
import com.phonesj.news.di.component.ActivityComponent;
import com.phonesj.news.di.component.DaggerActivityComponent;
import com.phonesj.news.di.module.ActivityModule;
import com.phonesj.news.util.SnackbarUtil;

import javax.inject.Inject;

import android.support.v7.app.AppCompatDelegate;
import android.view.ViewGroup;

/**
 * Created by Phone on 2017/7/14.
 * <p>封装了MVP的基类</p>
 */

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;

    /**
     * 获得注射器Component
     */
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent
            .builder()
            .appComponent(App.getAppComponent())
            .activityModule(getActivityModule())
            .build();
    }

    /**
     * 获得依赖模板Module
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated() {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        SnackbarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        //重新创建该Activity
        recreate();
    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }
}
