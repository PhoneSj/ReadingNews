package com.phonesj.news.ui.main.fragment;

import com.phonesj.news.R;
import com.phonesj.news.base.BaseFragment;
import com.phonesj.news.base.contract.main.LikeContract;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.presenter.main.LikePresenter;
import com.phonesj.news.ui.main.adapter.LikeAdapter;
import com.phonesj.news.widget.DefaultItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
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
        DefaultItemTouchHelperCallback itemTouchHelperCallback = new DefaultItemTouchHelperCallback(new DefaultItemTouchHelperCallback.OnItemTouchCallbackListener() {
            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                Log.w("phone", "src:" + srcPosition + "  tar:" + targetPosition);
                if (datas != null) {
                    mPresenter.changeLikeTime(datas.get(srcPosition).getId(), datas
                        .get(targetPosition)
                        .getTime(), srcPosition < targetPosition);
                    Collections.swap(datas, srcPosition, targetPosition);
                    mAdapter.notifyItemMoved(srcPosition, targetPosition);
                    return true;
                }
                return false;
            }

            @Override
            public void onSwiped(int position) {
                if (datas != null) {
                    //删除数据
                    mPresenter.deleteLikeData(datas.get(position).getId());
                    datas.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        });
        itemTouchHelperCallback.setCanDrag(true);
        itemTouchHelperCallback.setCanSwipe(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvLikeList);
        mPresenter.getLikeData();
    }

    @Override
    public void showContent(List<LikeBean> info) {
        Log.w("phone", "showContent");
        datas.clear();
        datas.addAll(info);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //在Fragment由隐藏到显示时，需要重新查询数据库
        if (!hidden) {
            mPresenter.getLikeData();
        }
    }
}
