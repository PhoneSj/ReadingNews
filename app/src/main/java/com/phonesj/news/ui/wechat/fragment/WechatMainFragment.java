package com.phonesj.news.ui.wechat.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.SimpleFragment;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.event.SearchEvent;
import com.phonesj.news.ui.wechat.adapter.WechatMainPageAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;

/**
 * Created by Phone on 2017/8/1.
 */

public class WechatMainFragment extends SimpleFragment {

    @BindView(R.id.tab_wechat_main)
    TabLayout tabWechatMain;
    @BindView(R.id.vp_wechat_main)
    ViewPager vpWechatMain;

    private String tabTitles[] = {"热点新闻", "社会新闻", "国内新闻", "国际新闻", "娱乐新闻", "体育新闻", "科技新闻", "军事新闻"};
    private String types[] = {"wxnew", "social", "guonei", "world", "huabian", "tiyu", "keji", "military"};

    private List<Fragment> fragments = new ArrayList<>();
    private WechatMainPageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat_main;
    }

    @Override
    protected void initEventAndData() {
        tabWechatMain.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < tabTitles.length; i++) {
            Fragment fragment = new WechatNewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_WECHAT_NEWS_TITLE, tabTitles[i]);
            bundle.putString(Constants.BUNDLE_WECHAT_NEWS_TYPE, types[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);

            tabWechatMain.addTab(tabWechatMain.newTab().setText(tabTitles[i]));
        }
        mAdapter = new WechatMainPageAdapter(getChildFragmentManager(), fragments);
        vpWechatMain.setAdapter(mAdapter);

        tabWechatMain.setupWithViewPager(vpWechatMain);
        for (int i = 0; i < tabTitles.length; i++) {
            tabWechatMain.getTabAt(i).setText(tabTitles[i]);
        }
    }

    public void doSearch(String query) {
        RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_WECHAT));
    }
}
