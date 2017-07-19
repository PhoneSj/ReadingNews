package com.phonesj.news.di.module;

import com.phonesj.news.di.scope.ActivityScope;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Phone on 2017/7/14.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }
}
