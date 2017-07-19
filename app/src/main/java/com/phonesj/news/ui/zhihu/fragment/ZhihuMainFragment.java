package com.phonesj.news.ui.zhihu.fragment;

import com.phonesj.news.R;
import com.phonesj.news.base.SimpleFragment;
import com.phonesj.news.ui.zhihu.adapter.ZhihuMainAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/18.
 */

public class ZhihuMainFragment extends SimpleFragment {

    @BindView(R.id.tab_zhihu_main)
    TabLayout tl_zhihu_main;
    @BindView(R.id.vp_zhihu_main)
    ViewPager vp_zhihu_main;

    String tabTitles[] = new String[]{"日报", "主题", "专栏", "热门"};
    List<Fragment> fragments = new ArrayList<Fragment>();

    ZhihuMainAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu_main;
    }

    @Override
    protected void initEventAndData() {
        fragments.add(new DailyFragment());
        fragments.add(new ThemeFragment());
        fragments.add(new SectionFragment());
        fragments.add(new HotFragment());
        adapter = new ZhihuMainAdapter(getChildFragmentManager(), fragments);
        vp_zhihu_main.setAdapter(adapter);

        for (String title : tabTitles) {
            tl_zhihu_main.addTab(tl_zhihu_main.newTab().setText(title));
        }
        tl_zhihu_main.setupWithViewPager(vp_zhihu_main);//与ViewPager绑定，实现同步联动
        for (int i = 0; i < tabTitles.length; i++) {
            tl_zhihu_main.getTabAt(i).setText(tabTitles[i]);
        }
    }
}
