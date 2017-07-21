package com.phonesj.news.di.component;

import com.phonesj.news.di.module.ActivityModule;
import com.phonesj.news.di.scope.ActivityScope;
import com.phonesj.news.ui.main.activity.MainActivity;
import com.phonesj.news.ui.main.activity.WelcomeActivity;
import com.phonesj.news.ui.zhihu.activity.ThemeActivity;
import com.phonesj.news.ui.zhihu.activity.ZhihuDetailActivity;

import dagger.Component;

/**
 * Created by Phone on 2017/7/14.
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    /**
     * 注入依赖需求方
     *
     * @param welcomeActivity
     */
    void inject(WelcomeActivity welcomeActivity);


    void inject(MainActivity mainActivity);


    void inject(ZhihuDetailActivity zhihuDetailActivity);

    void inject(ThemeActivity themeActivity);
}
