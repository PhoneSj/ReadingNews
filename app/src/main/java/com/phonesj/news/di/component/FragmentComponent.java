package com.phonesj.news.di.component;

import com.phonesj.news.di.module.FragmentModule;
import com.phonesj.news.di.scope.FragmentScope;
import com.phonesj.news.ui.zhihu.fragment.CommonFragment;
import com.phonesj.news.ui.zhihu.fragment.DailyFragment;
import com.phonesj.news.ui.zhihu.fragment.HotFragment;
import com.phonesj.news.ui.zhihu.fragment.SectionFragment;
import com.phonesj.news.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by Phone on 2017/7/14.
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(DailyFragment dailyFragment);

    void inject(ThemeFragment themeFragment);

    void inject(SectionFragment sectionFragment);

    void inject(HotFragment hotFragment);

    void inject(CommonFragment commonFragment);

}
