package com.phonesj.news.ui.zhihu.fragment;


import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.zhihu.DailyConstract;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.presenter.zhihu.DailyPresenter;
import com.phonesj.news.ui.zhihu.activity.CalenderActivity;
import com.phonesj.news.ui.zhihu.activity.ZhihuDetailActivity;
import com.phonesj.news.ui.zhihu.adapter.DailyAdapter;
import com.phonesj.news.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyFragment extends RootFragment<DailyPresenter> implements DailyConstract.View {

    @BindView(R.id.srf_daily)
    SwipeRefreshLayout srf_daily;
    @BindView(R.id.view_main)
    RecyclerView view_main;
    @BindView(R.id.fab_calender)
    FloatingActionButton fab_calender;

    String currentDate;
    DailyAdapter mAdapter;
    List<DailyBean.StoriesBean> storiesBeanList = new ArrayList<DailyBean.StoriesBean>();

    boolean hasInterval = false;
    Unbinder unbinder;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        currentDate = DateUtil.getTomorrowDate();
        mAdapter = new DailyAdapter(mContext, storiesBeanList);
        mAdapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //在数据库中记录该数据已经阅读过
                mPresenter.insertReadStateToDB(storiesBeanList.get(position).getId());
                //更新被点击条目ui
                mAdapter.setReadState(position, true);
                if (mAdapter.isBeforeData()) {
                    mAdapter.notifyItemChanged(position + 1);
                } else {
                    mAdapter.notifyItemChanged(position + 2);
                }
                //页面跳转
                int id = storiesBeanList.get(position).getId();
                Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, id);
                mContext.startActivity(intent);
            }
        });
        srf_daily.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentDate.equals(DateUtil.getTomorrowDate())) {
                    mPresenter.getDailyData();//从网络后去数据
                } else {
                    int year = Integer.valueOf(currentDate.substring(0, 4));
                    int month = Integer.valueOf(currentDate.substring(4, 6));
                    int day = Integer.valueOf(currentDate.substring(6, 8));
                    CalendarDay date = CalendarDay.from(year, month - 1, day);
                    RxBus.getDefault().post(date);//发送Rxbus事件，返回结果会在对应的Presenter中接收
                }
            }
        });
        view_main.setLayoutManager(new LinearLayoutManager(mContext));
        view_main.setAdapter(mAdapter);
        stateLoading();
        mPresenter.getDailyData();//从网络后去数据
    }

    @Override
    public void onStart() {
        super.onStart();
        if (hasInterval) {
            mPresenter.startInterval();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stopInterval();
    }

    @Override
    public void showContent(DailyBean info) {
        if (srf_daily.isRefreshing()) {
            srf_daily.setRefreshing(false);
        }
        stateMain();//切换到主视图
        storiesBeanList = info.getStories();
        currentDate = String.valueOf(Integer.parseInt(info.getDate()) + 1);
        mAdapter.addDailyDate(info);
        hasInterval = true;
        mPresenter.startInterval();//开启轮播图
    }

    @Override
    public void showMoreContent(String date, DailyBeforeBean info) {
        if (srf_daily.isRefreshing()) {
            srf_daily.setRefreshing(false);
        }
        stateMain();
        hasInterval = false;
        mPresenter.stopInterval();
        storiesBeanList = info.getStories();
        currentDate = String.valueOf(Integer.parseInt(info.getDate()));
        mAdapter.addDailyBeforeDate(info);
    }

    @Override
    public void doInterval(int currentCount) {
        Log.w("phone", "doInterval");
        mAdapter.changeTopPage(currentCount);
    }

    @Override
    public void stateError() {
        super.stateError();
        if (srf_daily != null && srf_daily.isRefreshing()) {
            srf_daily.setRefreshing(false);
        }
    }

    @OnClick(R.id.fab_calender)
    public void onViewClicked() {
        // TODO: 2017/7/19 跳转到日历选择页面
        Intent intent = new Intent(mContext, CalenderActivity.class);
        startActivity(intent);
    }
}
