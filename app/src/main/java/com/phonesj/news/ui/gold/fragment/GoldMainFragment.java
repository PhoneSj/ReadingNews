package com.phonesj.news.ui.gold.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.BaseFragment;
import com.phonesj.news.base.contract.gold.GoldMainContract;
import com.phonesj.news.model.bean.gold.GoldManagerBean;
import com.phonesj.news.model.bean.gold.GoldManagerItemBean;
import com.phonesj.news.presenter.gold.GoldMainPresenter;
import com.phonesj.news.ui.gold.activity.GoldManagerActivity;
import com.phonesj.news.ui.gold.adapter.GoldPageAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Phone on 2017/7/19.
 */

public class GoldMainFragment extends BaseFragment<GoldMainPresenter> implements GoldMainContract.View {

    @BindView(R.id.tab_gold_main)
    TabLayout tabGoldMain;
    @BindView(R.id.iv_gold_menu)
    ImageView ivGoldMenu;
    @BindView(R.id.vp_gold_main)
    ViewPager vpGoldMain;

    public static String[] typeStr = {"Android", "iOS", "前端", "后端", "设计", "产品", "阅读", "工具资源"};
    public static String[] type = {"android", "ios", "frontend", "backend", "design", "product", "article", "freebie"};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_main;
    }

    @Override
    protected void initEventAndData() {
        tabGoldMain.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabGoldMain.setupWithViewPager(vpGoldMain);
        mPresenter.initManagerList();
    }


    @Override
    public void updateTab(List<GoldManagerItemBean> info) {
        //先清空数据
        fragments.clear();
        tabGoldMain.removeAllTabs();
        //在重填数据
        for (GoldManagerItemBean bean : info) {
            if (bean.isSelected()) {
                GoldPageFrament frament = new GoldPageFrament();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_GOLD_TYPE, type[bean.getIndex()]);
                bundle.putString(Constants.INTENT_GOLD_TYPE_STR, typeStr[bean.getIndex()]);
                tabGoldMain.addTab(tabGoldMain.newTab().setText(typeStr[bean.getIndex()]));
                frament.setArguments(bundle);
                fragments.add(frament);
            }
        }
        GoldPageAdapter adapter = new GoldPageAdapter(getChildFragmentManager(), fragments);
        vpGoldMain.setAdapter(adapter);

        //在与viewpager联动时，tab标题可能会不显示，这里在设置一次tab标题
        int tempIndex = 0;
        for (GoldManagerItemBean bean : info) {
            if (bean.isSelected()) {
                tabGoldMain.getTabAt(tempIndex).setText(typeStr[bean.getIndex()]);
                tempIndex++;
            }
        }
    }

    @Override
    public void jumpToManager(GoldManagerBean bean) {
        Intent intent = new Intent(mContext, GoldManagerActivity.class);
        intent.putExtra(Constants.INTENT_GOLD_MANAGER, bean);
        startActivity(intent);
    }

    @OnClick(R.id.iv_gold_menu)
    public void onViewClicked() {
        mPresenter.setManagerList();
    }
}
