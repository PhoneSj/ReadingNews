package com.phonesj.news.ui.main.fragment;

import com.phonesj.news.R;
import com.phonesj.news.base.BaseFragment;
import com.phonesj.news.base.contract.main.LikeContract;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.presenter.main.LikePresenter;
import com.phonesj.news.ui.main.adapter.LikeAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/21.
 */

public class LikeFragment extends BaseFragment<LikePresenter> implements LikeContract.View {

    @BindView(R.id.rv_like_list)
    RecyclerView rvLikeList;

    private List<LikeBean> datas = new ArrayList<>();
    private LikeAdapter mAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_like;
    }

    @Override
    protected void initEventAndData() {
        mAdapter = new LikeAdapter(mContext, datas);
        rvLikeList.setLayoutManager(new LinearLayoutManager(mContext));
        rvLikeList.setAdapter(mAdapter);

        mPresenter.getLikeData();
    }

    @Override
    public void showContent(List<LikeBean> info) {
        datas.clear();
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

}
