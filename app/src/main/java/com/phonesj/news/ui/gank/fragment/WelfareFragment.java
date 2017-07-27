package com.phonesj.news.ui.gank.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.gank.WelfareContract;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.presenter.gank.WelfarePresenter;
import com.phonesj.news.ui.gank.activity.WelfareDetailActivity;
import com.phonesj.news.ui.gank.adapter.WelfareAdapter;
import com.phonesj.news.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/26.
 */

public class WelfareFragment extends RootFragment<WelfarePresenter> implements WelfareContract.View {

    public static final int SPAN_COUNT = 2;

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<GankItemBean> datas = new ArrayList<>();
    private WelfareAdapter mAdapter;
    private boolean isLoadingMore = false;

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

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);
        mAdapter = new WelfareAdapter(mContext, datas);
        viewMain.setLayoutManager(mStaggeredGridLayoutManager);
        viewMain.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new WelfareAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Intent intent = new Intent(mContext, WelfareDetailActivity.class);
                intent.putExtra(Constants.INTENT_WELFARE_DETAIL_ID, datas.get(position).get_id());
                intent.putExtra(Constants.INTENT_WELFARE_DETAIL_URL, datas.get(position).getUrl());
                startActivity(intent);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWelfareData();
            }
        });

        viewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visiableItems[] = ((StaggeredGridLayoutManager) viewMain.getLayoutManager()).findLastVisibleItemPositions(null);
                int lastItem = Math.max(visiableItems[0], visiableItems[1]);
                if (lastItem > mAdapter.getItemCount() - 5 && dy > 0 && !isLoadingMore) {
                    isLoadingMore = true;
                    mPresenter.getMoreWelfareData();
                }
            }
        });

        mPresenter.getWelfareData();
        stateLoading();
    }

    @Override
    public void stateError() {
        super.stateError();
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showContent(List<GankItemBean> info) {
        LogUtil.w("welfare showContent()");
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        datas.clear();
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<GankItemBean> info) {
        isLoadingMore = false;
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

}
