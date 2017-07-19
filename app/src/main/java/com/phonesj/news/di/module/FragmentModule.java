package com.phonesj.news.di.module;

import com.phonesj.news.di.scope.FragmentScope;

import android.app.Activity;
import android.support.v4.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Phone on 2017/7/14.
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
