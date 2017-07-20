package com.phonesj.news.ui.zhihu.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootFragment;
import com.phonesj.news.base.contract.zhihu.CommonConstract;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.presenter.zhihu.CommonPresenter;
import com.phonesj.news.ui.zhihu.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/20.
 */

public class CommonFragment extends RootFragment<CommonPresenter> implements CommonConstract.View {


    @BindView(R.id.view_main)
    RecyclerView viewMain;

    List<CommonBean.CommentsBean> datas = new ArrayList<>();
    CommonAdapter mAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        viewMain.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommonAdapter(mContext, datas);
        viewMain.setAdapter(mAdapter);

        stateLoading();

        Bundle bundle = getArguments();
        int id = bundle.getInt(Constants.BUNDLE_ZHIHU_COMMON_ID);
        int kind = bundle.getInt(Constants.BUNDLE_ZHIHU_COMMON_KIND);
        mPresenter.getCommonData(id, kind);//网络获取数据
    }

    @Override
    public void showContent(CommonBean info) {
        stateMain();
        datas.clear();
        datas.addAll(info.getComments());
        mAdapter.notifyDataSetChanged();
    }

}
