package com.phonesj.news.ui.zhihu.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.zhihu.HotConstract;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.presenter.zhihu.HotPresenter;
import com.phonesj.news.ui.zhihu.activity.ZhihuDetailActivity;
import com.phonesj.news.ui.zhihu.adapter.HotAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/18.
 */

public class HotFragment extends RootFragment<HotPresenter> implements HotConstract.View {

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    List<HotBean.RecentBean> recentBeanList = new ArrayList<HotBean.RecentBean>();
    HotAdapter mAdapter;

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
        mAdapter = new HotAdapter(mContext, recentBeanList);
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.setReadState(position, true);
                mAdapter.notifyItemChanged(position);

                Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, recentBeanList
                    .get(position)
                    .getNews_id());
                startActivity(intent);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHotData();
            }
        });
        mPresenter.getHotData();
        stateLoading();
    }

    @Override
    public void showContent(HotBean info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        recentBeanList.clear();
        recentBeanList.addAll(info.getRecent());
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
