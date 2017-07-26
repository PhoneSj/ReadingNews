package com.phonesj.news.ui.gank.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.SimpleFragment;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.event.SearchEvent;
import com.phonesj.news.ui.gank.adapter.GankMainAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/19.
 */

public class GankMainFragment extends SimpleFragment {


    @BindView(R.id.tab_gank_main)
    TabLayout tabGankMain;
    @BindView(R.id.vp_gank_main)
    ViewPager vpGankMain;

    public static String titles[] = {"Android", "iOS", "前端", "福利"};
    private List<Fragment> fragments = new ArrayList<>();

    private TechFragment androidFragment;
    private TechFragment iosFragment;
    private TechFragment webFragment;
    private WelfareFragment welfareFragment;

    private GankMainAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_main;
    }

    @Override
    protected void initEventAndData() {
        androidFragment = new TechFragment();
        Bundle androidBundle = new Bundle();
        androidBundle.putString(Constants.INTENT_GANK_TITLE, titles[0]);
        androidBundle.putInt(Constants.INTENT_GANK_TYPE_CODE, Constants.TYPE_ANDROID);
        androidFragment.setArguments(androidBundle);

        iosFragment = new TechFragment();
        Bundle iosBundle = new Bundle();
        iosBundle.putString(Constants.INTENT_GANK_TITLE, titles[1]);
        iosBundle.putInt(Constants.INTENT_GANK_TYPE_CODE, Constants.TYPE_IOS);
        iosFragment.setArguments(iosBundle);

        webFragment = new TechFragment();
        Bundle webBundle = new Bundle();
        webBundle.putString(Constants.INTENT_GANK_TITLE, titles[2]);
        webBundle.putInt(Constants.INTENT_GANK_TYPE_CODE, Constants.TYPE_WEB);
        webFragment.setArguments(webBundle);

        welfareFragment = new WelfareFragment();
        Bundle welfareBundle = new Bundle();
        welfareBundle.putString(Constants.INTENT_GANK_TITLE, titles[3]);
        welfareBundle.putInt(Constants.INTENT_GANK_TYPE_CODE, Constants.TYPE_WELFARE);

        fragments.add(androidFragment);
        fragments.add(iosFragment);
        fragments.add(webFragment);
        fragments.add(welfareFragment);

        mAdapter = new GankMainAdapter(getChildFragmentManager(), fragments);
        vpGankMain.setAdapter(mAdapter);

        tabGankMain.addTab(tabGankMain.newTab().setText(titles[0]));
        tabGankMain.addTab(tabGankMain.newTab().setText(titles[1]));
        tabGankMain.addTab(tabGankMain.newTab().setText(titles[2]));
        tabGankMain.addTab(tabGankMain.newTab().setText(titles[3]));
        tabGankMain.setupWithViewPager(vpGankMain);
        tabGankMain.getTabAt(0).setText(titles[0]);
        tabGankMain.getTabAt(1).setText(titles[1]);
        tabGankMain.getTabAt(2).setText(titles[2]);
        tabGankMain.getTabAt(3).setText(titles[3]);
    }

    public void doSearch(String queryStr) {
        switch (vpGankMain.getCurrentItem()) {
            case 0:
                RxBus.getDefault().post(new SearchEvent(queryStr, Constants.TYPE_ANDROID));
                break;
            case 1:
                RxBus.getDefault().post(new SearchEvent(queryStr, Constants.TYPE_IOS));
                break;
            case 2:
                RxBus.getDefault().post(new SearchEvent(queryStr, Constants.TYPE_WEB));
                break;
            case 3:
                RxBus.getDefault().post(new SearchEvent(queryStr, Constants.TYPE_WELFARE));
                break;
        }
    }

}
