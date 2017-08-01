package com.phonesj.news.ui.gold.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.gold.GoldPageContract;
import com.phonesj.news.model.bean.gold.GoldListBean;
import com.phonesj.news.presenter.gold.GoldPagePresenter;
import com.phonesj.news.ui.gold.adapter.GoldListAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldPageFrament extends RootFragment<GoldPagePresenter> implements GoldPageContract.View {

    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private String mType;
    private String mTypeStr;

    private GoldListAdapter mAdapter;
    //    private ItemDecoration mDecoration;

    private boolean isLoadingMore = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_page;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mType = getArguments().getString(Constants.INTENT_GOLD_TYPE);
        mTypeStr = getArguments().getString(Constants.INTENT_GOLD_TYPE_STR);

        mAdapter = new GoldListAdapter(mContext, new ArrayList<GoldListBean>(), mTypeStr);
        //        mDecoration = new ItemDecoration();

        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);
        //        viewMain.addItemDecoration(mDecoration);
        viewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) viewMain.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = viewMain.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        mPresenter.getMoreGoldData();
                    }
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mAdapter.isHotFlag()) {
                    //                    viewMain.removeItemDecoration(mDecoration);
                }
                mAdapter.setHotFlag(true);
                mPresenter.getGoldData(mType);
            }
        });
        mAdapter.setOnHotCloseListener(new GoldListAdapter.OnHotCloseListener() {
            @Override
            public void onClose() {
                //                viewMain.removeItemDecoration(mDecoration);
            }
        });
        stateLoading();
        mPresenter.getGoldData(mType);
    }

    @Override
    public void showContent(List<GoldListBean> info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        mAdapter.updateData(info);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<GoldListBean> info, int start, int end) {
        isLoadingMore = false;
        mAdapter.updateData(info);
        mAdapter.notifyItemRangeInserted(start, end);
    }

    @Override
    public void stateError() {
        super.stateError();
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
