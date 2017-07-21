package com.phonesj.news.ui.zhihu.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootActivity;
import com.phonesj.news.base.contract.zhihu.ThemeSubConstract;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.StoriesBean;
import com.phonesj.news.model.bean.zhihu.ThemeSubBean;
import com.phonesj.news.presenter.zhihu.ThemeSubPresenter;
import com.phonesj.news.ui.zhihu.adapter.ThemeSubAdapter;
import com.phonesj.news.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Phone on 2017/7/21.
 */

public class ThemeActivity extends RootActivity<ThemeSubPresenter> implements ThemeSubConstract.View {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_theme_child_blur)
    ImageView ivThemeChildBlur;
    @BindView(R.id.iv_theme_child_origin)
    ImageView ivThemeChildOrigin;
    @BindView(R.id.tv_theme_child_des)
    TextView tvThemeChildDes;
    @BindView(R.id.theme_child_appbar)
    AppBarLayout themeChildAppbar;
    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<StoriesBean> datas = new ArrayList<>();
    private ThemeSubAdapter mAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_theme;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mAdapter = new ThemeSubAdapter(mContext, datas);
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);

        Intent intent = getIntent();
        final int id = intent.getExtras().getInt(Constants.INTENT_ZHIHU_THEME_ID);
        String title = intent.getExtras().getString(Constants.INTENT_ZHIHU_THEME_TITLE);
        setToolbar(toolBar, title);
        mPresenter.getThemeSubData(id);
        stateLoading();

        //点击事件
        mAdapter.setOnItemClickListener(new ThemeSubAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                mPresenter.insertReadStateToDB(datas.get(positon).getId());//存储该消息的阅读状态
                mAdapter.setReadState(positon, true);
                mAdapter.notifyItemChanged(positon);//刷新指定item的ui
                Intent intent2 = new Intent(mContext, ZhihuDetailActivity.class);
                intent2.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, datas.get(positon).getId());
                startActivity(intent2);
            }
        });

        //AppBarLayout偏移监听器
        themeChildAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    //滑动已经触顶了，使能刷星
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                    float rate = (float) (SystemUtil.dp2px(mContext, 256) + verticalOffset * 2) / SystemUtil
                        .dp2px(mContext, 256);
                    if (rate >= 0) {
                        ivThemeChildOrigin.setAlpha(rate);
                    }
                }
            }
        });

        //刷新事件
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeSubData(id);
            }
        });
    }

    @Override
    public void showContent(ThemeSubBean info) {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        setToolbar(toolBar, info.getName());
        //该图片加入模糊效果
        ImageLoader.load(mContext, info.getBackground(), new BlurTransformation(mContext), ivThemeChildBlur);
        ImageLoader.load(mContext, info.getBackground(), ivThemeChildOrigin);
        tvThemeChildDes.setText(info.getDescription());
        datas.clear();
        datas.addAll(info.getStories());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
