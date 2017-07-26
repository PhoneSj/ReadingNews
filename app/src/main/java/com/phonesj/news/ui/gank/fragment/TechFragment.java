package com.phonesj.news.ui.gank.fragment;

import com.bumptech.glide.Glide;
import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.gank.TechContract;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.presenter.gank.TechPresenter;
import com.phonesj.news.ui.gank.adapter.TechAdapter;
import com.phonesj.news.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Phone on 2017/7/26.
 */

public class TechFragment extends RootFragment<TechPresenter> implements TechContract.View {

    @BindView(R.id.iv_tech_blur)
    ImageView ivTechBlur;
    @BindView(R.id.iv_tech_origin)
    ImageView ivTechOrigin;
    @BindView(R.id.tv_tech_copyright)
    TextView tvTechCopyright;
    @BindView(R.id.tech_appbar)
    AppBarLayout techAppbar;
    @BindView(R.id.view_main)
    RecyclerView viewMain;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private List<GankItemBean> datas = new ArrayList<>();
    private TechAdapter mAdapter;

    boolean isLoadingMore = false;
    String tech;
    int type;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tech;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        tech = getArguments().getString(Constants.INTENT_GANK_TITLE);
        type = getArguments().getInt(Constants.INTENT_GANK_TYPE_CODE);

        mAdapter = new TechAdapter(mContext, datas, tech);
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        viewMain.setAdapter(mAdapter);

        stateLoading();
        mPresenter.getGrilImage();
        mPresenter.getGankData(tech, type);

        mAdapter.setOnItemClickListener(new TechAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                // TODO: 2017/7/26
            }
        });
        viewMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisiableItem = ((LinearLayoutManager) viewMain.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = viewMain.getLayoutManager().getItemCount();
                if (lastVisiableItem >= totalItemCount - 2 && dy > 0) {
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        mPresenter.getMoreGankData(tech);
                    }
                }
            }
        });
        techAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    //AppBarLayout已经完全展开时，启用下拉刷新
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                    float rate = (float) (SystemUtil.dp2px(mContext, 256) + verticalOffset * 2) / SystemUtil
                        .dp2px(mContext, 256);
                    if (rate >= 0)
                        ivTechOrigin.setAlpha(rate);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGankData(tech, type);
            }
        });
    }

    @Override
    public void showContent(List<GankItemBean> info) {
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
        stateMain();
        isLoadingMore = false;
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGrilImage(String url, String copyright) {
        ImageLoader.load(mContext, url, ivTechOrigin);
        Glide
            .with(mContext)
            .load(url)
            .bitmapTransform(new BlurTransformation(mContext))
            .into(ivTechBlur);
        tvTechCopyright.setText(copyright);
    }

    @Override
    public void stateError() {
        super.stateError();
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
