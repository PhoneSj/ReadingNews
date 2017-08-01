package com.phonesj.news.ui.wechat.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.wechat.WechatNewsContract;
import com.phonesj.news.model.bean.wechat.WXItemBean;
import com.phonesj.news.presenter.wechat.WechatNewsPresenter;
import com.phonesj.news.ui.wechat.adapter.WechatNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/19.
 */

public class WechatNewsFragment extends RootFragment<WechatNewsPresenter> implements WechatNewsContract.View {

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private WechatNewsAdapter mAdapter;
    private List<WXItemBean> datas = new ArrayList<>();
    private String title;
    private String type;

    private boolean isLoadingMore;

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
        title = getArguments().getString(Constants.BUNDLE_WECHAT_NEWS_TITLE);
        type = getArguments().getString(Constants.BUNDLE_WECHAT_NEWS_TYPE);

        mAdapter = new WechatNewsAdapter(mContext, datas);
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);
        viewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiableItem = ((LinearLayoutManager) viewMain.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = viewMain.getLayoutManager().getItemCount();
                if (lastVisiableItem > totalItemCount - 2 && dy > 0) {
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        mPresenter.getMoreWechatData(type);
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWechatData(type);
            }
        });
        stateLoading();
        mPresenter.getWechatData(type);
    }

    @Override
    public void showContent(List<WXItemBean> info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        datas.clear();
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<WXItemBean> info) {
        stateMain();
        isLoadingMore = false;
        datas.addAll(info);
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
