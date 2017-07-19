package com.phonesj.news.ui.main.activity;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.phonesj.news.R;
import com.phonesj.news.app.App;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.BaseActivity;
import com.phonesj.news.base.contract.main.MainContract;
import com.phonesj.news.presenter.main.MainPresenter;
import com.phonesj.news.ui.gank.fragment.GankMainFragment;
import com.phonesj.news.ui.gold.fragment.GoldMainFragment;
import com.phonesj.news.ui.main.fragment.AboutFragment;
import com.phonesj.news.ui.main.fragment.LikeFragment;
import com.phonesj.news.ui.main.fragment.SettingFragment;
import com.phonesj.news.ui.vtex.fragment.VtexMainFragment;
import com.phonesj.news.ui.wechat.fragment.WechatMainFragment;
import com.phonesj.news.ui.zhihu.fragment.ZhihuMainFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.view_search)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_container)
    FrameLayout toolbarContainer;
    @BindView(R.id.fl_main_content)
    FrameLayout mMainContent;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    ZhihuMainFragment mZhihuMainFragment;
    WechatMainFragment mWechatMainFragment;
    GankMainFragment mGankMainFragment;
    GoldMainFragment mGoldMainFragment;
    VtexMainFragment mVtexMainFragment;
    LikeFragment mLikeFragment;
    SettingFragment mSettingFragment;
    AboutFragment mAboutFragment;

    ActionBarDrawerToggle mDrawerToggle;
    MenuItem mLastMenuItem;
    MenuItem mSearchMenuItem;

    private int hideFragment = Constants.TYPE_ZHIHU;
    private int showFragment = Constants.TYPE_ZHIHU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPresenter.setNightModeState(false);
        } else {
            showFragment = mPresenter.getCurrentItem();//从sp中获得上次选中项
            hideFragment = Constants.TYPE_ZHIHU;
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            mNavigationView.getMenu().findItem(R.id.drawer_zhihu).setChecked(false);
            mToolBar.setTitle(mNavigationView
                .getMenu()
                .findItem(getCurrentItem(showFragment))
                .getTitle());
            hideFragment = showFragment;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        setToolbar(mToolBar, "知乎日报");
        mZhihuMainFragment = new ZhihuMainFragment();
        mWechatMainFragment = new WechatMainFragment();
        mGankMainFragment = new GankMainFragment();
        mGoldMainFragment = new GoldMainFragment();
        mVtexMainFragment = new VtexMainFragment();
        mLikeFragment = new LikeFragment();
        mSettingFragment = new SettingFragment();
        mAboutFragment = new AboutFragment();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.drawer_zhihu);
        loadMultipleRootFragment(R.id.fl_main_content, 0, mZhihuMainFragment);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_zhihu:
                        showFragment = Constants.TYPE_ZHIHU;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_wechat:
                        showFragment = Constants.TYPE_WECHAT;
                        mSearchMenuItem.setVisible(true);
                        break;
                    case R.id.drawer_gank:
                        showFragment = Constants.TYPE_GANK;
                        mSearchMenuItem.setVisible(true);
                        break;
                    case R.id.drawer_gold:
                        showFragment = Constants.TYPE_GOLD;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_vtex:
                        showFragment = Constants.TYPE_VTEX;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_like:
                        showFragment = Constants.TYPE_LIKE;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_setting:
                        showFragment = Constants.TYPE_SETTING;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_about:
                        showFragment = Constants.TYPE_ABOUT;
                        mSearchMenuItem.setVisible(false);
                        break;
                }
                if (mLastMenuItem != null) {
                    mLastMenuItem.setChecked(false);
                }
                mLastMenuItem = item;
                mPresenter.setCurrentItem(showFragment);//保存当前选中的到sp中
                item.setChecked(true);
                mToolBar.setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        mSearchView.setMenuItem(item);
        mSearchMenuItem = item;
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().exitApp();//退出应用
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private SupportFragment getTargetFragment(int key) {
        switch (key) {
            case Constants.TYPE_ZHIHU:
                return mZhihuMainFragment;
            case Constants.TYPE_WECHAT:
                return mWechatMainFragment;
            case Constants.TYPE_GANK:
                return mGankMainFragment;
            case Constants.TYPE_GOLD:
                return mGoldMainFragment;
            case Constants.TYPE_VTEX:
                return mVtexMainFragment;
            case Constants.TYPE_LIKE:
                return mLikeFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_ABOUT:
                return mAboutFragment;
        }
        return mZhihuMainFragment;
    }

    private int getCurrentItem(int key) {
        switch (key) {
            case Constants.TYPE_ZHIHU:
                return R.id.drawer_zhihu;
            case Constants.TYPE_WECHAT:
                return R.id.drawer_wechat;
            case Constants.TYPE_GANK:
                return R.id.drawer_gank;
            case Constants.TYPE_GOLD:
                return R.id.drawer_gold;
            case Constants.TYPE_VTEX:
                return R.id.drawer_vtex;
            case Constants.TYPE_LIKE:
                return R.id.drawer_like;
            case Constants.TYPE_SETTING:
                return R.id.drawer_setting;
            case Constants.TYPE_ABOUT:
                return R.id.drawer_about;
        }
        return R.id.drawer_zhihu;
    }

}
