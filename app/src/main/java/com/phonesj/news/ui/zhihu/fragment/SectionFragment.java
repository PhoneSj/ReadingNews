package com.phonesj.news.ui.zhihu.fragment;

import com.phonesj.news.R;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.zhihu.SectionConstract;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.presenter.zhihu.SectionPresenter;
import com.phonesj.news.ui.zhihu.adapter.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/18.
 */

public class SectionFragment extends RootFragment<SectionPresenter> implements SectionConstract.View {

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    List<SectionBean.DataBean> dataBeanList = new ArrayList<SectionBean.DataBean>();
    SectionAdapter mAdapter;

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
        mAdapter = new SectionAdapter(mContext, dataBeanList);
        viewMain.setLayoutManager(new GridLayoutManager(mContext, 2));
        viewMain.setAdapter(mAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSectionData();
            }
        });

        mPresenter.getSectionData();
        stateLoading();
    }

    @Override
    public void showContent(SectionBean info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        dataBeanList.clear();
        dataBeanList.addAll(info.getData());
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
