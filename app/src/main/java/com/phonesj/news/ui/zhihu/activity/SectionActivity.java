package com.phonesj.news.ui.zhihu.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootActivity;
import com.phonesj.news.base.contract.zhihu.SectionSubConstract;
import com.phonesj.news.model.bean.zhihu.SectionSubBean;
import com.phonesj.news.model.bean.zhihu.StoriesBean;
import com.phonesj.news.presenter.zhihu.SectionSubPresenter;
import com.phonesj.news.ui.zhihu.adapter.SectionSubAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/21.
 */

public class SectionActivity extends RootActivity<SectionSubPresenter> implements SectionSubConstract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    List<StoriesBean> datas = new ArrayList<>();
    SectionSubAdapter mAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_section;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mAdapter = new SectionSubAdapter(mContext, datas);
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);

        final Intent intent = getIntent();
        final int id = intent.getExtras().getInt(Constants.INTENT_ZHIHU_SECTION_ID);
        String title = intent.getExtras().getString(Constants.INTENT_ZHIHU_SECTION_TITLE);
        setToolbar(toolBar, title);
        mPresenter.getSectionSubData(id);
        stateLoading();

        mAdapter.setOnItemClickListener(new SectionSubAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.insertReadStateToDB(datas.get(position).getId());
                mAdapter.setReadState(position, true);
                mAdapter.notifyItemChanged(position);
                Intent intent2 = new Intent(mContext, ZhihuDetailActivity.class);
                intent2.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, datas.get(position).getId());
                startActivity(intent2);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSectionSubData(id);
            }
        });
    }

    @Override
    public void showContent(SectionSubBean info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        datas.clear();
        datas.addAll(info.getStories());
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
