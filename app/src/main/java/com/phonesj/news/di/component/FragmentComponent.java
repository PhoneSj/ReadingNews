package com.phonesj.news.di.component;

import com.phonesj.news.di.module.FragmentModule;
import com.phonesj.news.di.scope.FragmentScope;
import com.phonesj.news.ui.gank.fragment.TechFragment;
import com.phonesj.news.ui.gank.fragment.WelfareFragment;
import com.phonesj.news.ui.main.fragment.LikeFragment;
import com.phonesj.news.ui.main.fragment.SettingFragment;
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

    void inject(LikeFragment likeFragment);

    void inject(SettingFragment settingFragment);

    void inject(TechFragment techFragment);

    void inject(WelfareFragment welfareFragment);

}
