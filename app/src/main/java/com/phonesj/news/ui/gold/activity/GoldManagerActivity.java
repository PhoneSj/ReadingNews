package com.phonesj.news.ui.gold.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.SimpleActivity;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.bean.gold.GoldManagerBean;
import com.phonesj.news.model.bean.gold.GoldManagerItemBean;
import com.phonesj.news.ui.gold.adapter.GoldManagerAdapter;
import com.phonesj.news.widget.DefaultItemTouchHelperCallback;

import java.util.Collections;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldManagerActivity extends SimpleActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_gold_manager_list)
    RecyclerView rvGoldManagerList;

    private RealmList<GoldManagerItemBean> mList;
    private GoldManagerAdapter mAdapter;
    private DefaultItemTouchHelperCallback mCallBack;

    @Override
    protected int getLayout() {
        return R.layout.activity_gold_manager;
    }

    @Override
    protected void initEventAndData() {
        setToolbar(toolBar, "首页特别展示");
        Intent intent = getIntent();
        GoldManagerBean bean = intent.getExtras().getParcelable(Constants.INTENT_GOLD_MANAGER);
        mList = bean.getManagerList();

        mAdapter = new GoldManagerAdapter(mContext, mList);
        rvGoldManagerList.setLayoutManager(new LinearLayoutManager(mContext));
        rvGoldManagerList.setAdapter(mAdapter);

        mCallBack = new DefaultItemTouchHelperCallback(new DefaultItemTouchHelperCallback.OnItemTouchCallbackListener() {
            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                if (mList != null) {
                    Collections.swap(mList, srcPosition, targetPosition);
                    mAdapter.notifyItemMoved(srcPosition, targetPosition);
                    return true;
                }
                return false;
            }

            @Override
            public void onSwiped(int position) {
            }
        });
        mCallBack.setCanDrag(true);
        mCallBack.setCanSwipe(false);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallBack);
        itemTouchHelper.attachToRecyclerView(rvGoldManagerList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //该界面销毁时，发送Rxbus事件
        RxBus.getDefault().post(new GoldManagerBean(mList));
    }
}
