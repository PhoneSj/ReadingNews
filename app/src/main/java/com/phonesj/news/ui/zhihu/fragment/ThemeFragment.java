package com.phonesj.news.ui.zhihu.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.zhihu.ThemeConstract;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.presenter.zhihu.ThemePresenter;
import com.phonesj.news.ui.zhihu.activity.ThemeActivity;
import com.phonesj.news.ui.zhihu.adapter.ThemeAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/18.
 */

public class ThemeFragment extends RootFragment<ThemePresenter> implements ThemeConstract.View {

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    List<ThemeBean.OthersBean> othersBeanList = new ArrayList<ThemeBean.OthersBean>();
    ThemeAdapter mAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mAdapter = new ThemeAdapter(mContext, othersBeanList);
        viewMain.setLayoutManager(new GridLayoutManager(mContext, 2));
        viewMain.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO: 2017/7/19 页面跳转
                Intent intent = new Intent(mContext, ThemeActivity.class);
                intent.putExtra(Constants.INTENT_ZHIHU_THEME_ID, othersBeanList
                    .get(position)
                    .getId());
                startActivity(intent);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeData();
            }
        });
        mPresenter.getThemeData();
        stateLoading();
    }

    @Override
    public void showContent(ThemeBean info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        othersBeanList.clear();
        othersBeanList.addAll(info.getOthers());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stateError() {
        super.stateError();
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
