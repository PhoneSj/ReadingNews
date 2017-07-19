package com.phonesj.news.ui.zhihu.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Phone on 2017/7/18.
 */

public class ZhihuMainAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ZhihuMainAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //    @Override
    //    public void destroyItem(ViewGroup container, int position, Object object) {
    //        super.destroyItem(container, position, object);
    //    }
    //
    //    @Override
    //    public Object instantiateItem(ViewGroup container, int position) {
    //        return super.instantiateItem(container, position);
    //    }
}
