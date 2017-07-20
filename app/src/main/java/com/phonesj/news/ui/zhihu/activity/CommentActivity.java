package com.phonesj.news.ui.zhihu.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.SimpleActivity;
import com.phonesj.news.ui.zhihu.adapter.CommonMainAdapter;
import com.phonesj.news.ui.zhihu.fragment.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;

import static com.phonesj.news.app.Constants.ZHIHU_COMMON_TYPE_SHORT;

/**
 * Created by Phone on 2017/7/20.
 */

public class CommentActivity extends SimpleActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_comment)
    TabLayout tabComment;
    @BindView(R.id.vp_comment)
    ViewPager vpComment;

    List<Fragment> fragments = new ArrayList<Fragment>();
    CommonMainAdapter mAdapter;

    @Override
    protected void onViewCreated() {
        //这里不做处理
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        int id = intent.getExtras().getInt(Constants.INTENT_ZHIHU_COMMON_ID);
        int allNum = intent.getExtras().getInt(Constants.INTENT_ZHIHU_COMMON_ALL_NUM);
        int shortNum = intent.getExtras().getInt(Constants.INTENT_ZHIHU_COMMONT_SHORT_NUM);
        int longNum = intent.getExtras().getInt(Constants.INTENT_ZHIHU_COMMONT_LONG_NUM);

        setToolbar(toolbar, String.format("%d条评论", allNum));

        //短评论
        CommonFragment shortFragment = new CommonFragment();
        Bundle shortBundle = new Bundle();
        shortBundle.putInt(Constants.BUNDLE_ZHIHU_COMMON_ID, id);
        shortBundle.putInt(Constants.BUNDLE_ZHIHU_COMMON_KIND, ZHIHU_COMMON_TYPE_SHORT);
        shortFragment.setArguments(shortBundle);
        //长评论
        CommonFragment longFragment = new CommonFragment();
        Bundle longBundle = new Bundle();
        longBundle.putInt(Constants.BUNDLE_ZHIHU_COMMON_ID, id);
        longBundle.putInt(Constants.BUNDLE_ZHIHU_COMMON_KIND, Constants.ZHIHU_COMMON_TYPE_LONG);
        longFragment.setArguments(longBundle);

        fragments.add(shortFragment);
        fragments.add(longFragment);
        mAdapter = new CommonMainAdapter(getSupportFragmentManager(), fragments);
        vpComment.setAdapter(mAdapter);

        tabComment.addTab(tabComment.newTab().setText(String.format("短评论%d", shortNum)));
        tabComment.addTab(tabComment.newTab().setText(String.format("长评论%d", longNum)));
        tabComment.setupWithViewPager(vpComment);//使ViewPager与TabLayout联动
        tabComment.getTabAt(0).setText(String.format("短评论%d", shortNum));
        tabComment.getTabAt(1).setText(String.format("长评论%d", longNum));
    }

}
