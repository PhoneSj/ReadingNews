package com.phonesj.news.ui.gank.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.App;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.BaseActivity;
import com.phonesj.news.base.contract.gank.TechDetailContract;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.presenter.gank.TechDetailPresenter;
import com.phonesj.news.util.LogUtil;
import com.phonesj.news.util.ShareUtil;
import com.phonesj.news.util.SystemUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/27.
 */

public class TechDetailActivity extends BaseActivity<TechDetailPresenter> implements TechDetailContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.wv_tech_content)
    WebView wvTechContent;
    @BindView(R.id.tv_progress)
    TextView tvProgress;

    private String id;
    private String image;
    private String title;
    private String url;
    private int type;

    private MenuItem likeMenuItem;
    private boolean isLiked;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_tech_detail;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        id = intent.getExtras().getString(Constants.INTENT_TECH_DETAIL_ID);
        image = intent.getExtras().getString(Constants.INTENT_TECH_DETAIL_IMAGE);
        title = intent.getExtras().getString(Constants.INTENT_TECH_DETAIL_TITLE);
        url = intent.getExtras().getString(Constants.INTENT_TECH_DETAIL_URL);
        type = intent.getExtras().getInt(Constants.INTENT_TECH_DETAIL_TYPE);

        setToolbar(toolBar, title);

        //WebView配置参数对象
        WebSettings settings = wvTechContent.getSettings();
        if (mPresenter.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (mPresenter.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (SystemUtil.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        wvTechContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        wvTechContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                if (tvProgress == null) {
                    return;
                }
                if (newProgress == 100) {
                    tvProgress.setVisibility(View.GONE);
                } else {
                    tvProgress.setVisibility(View.VISIBLE);
                    //通过View的宽度值显示加载进度
                    ViewGroup.LayoutParams lp = tvProgress.getLayoutParams();
                    lp.width = App.SCREEN_WIDTH * newProgress / 100;
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitle(title);
            }
        });
        LogUtil.w("url:" + url);
        //加载
        wvTechContent.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tech_meun, menu);
        likeMenuItem = menu.findItem(R.id.action_like);
        isLiked = mPresenter.queryLikeId(id);
        setLikeState();
        return true;
    }

    private void setLikeState() {
        if (isLiked) {
            likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
        } else {
            likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_like:
                isLiked = !isLiked;
                if (!isLiked) {
                    mPresenter.deleteLikeBean(id);
                } else {
                    LikeBean bean = new LikeBean();
                    bean.setId(id);
                    bean.setTitle(title);
                    bean.setImage(image);
                    bean.setUrl(url);
                    bean.setType(type);
                    bean.setTime(System.currentTimeMillis());
                    mPresenter.insertLikeBean(bean);
                }
                setLikeState();
                break;
            case R.id.action_copy:
                SystemUtil.copyToClipBoard(mContext, url);
                break;
            case R.id.action_share:
                ShareUtil.shareText(mContext, url, "分享一篇文章");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvTechContent.canGoBack()) {
            wvTechContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finishAfterTransition();
        }
    }

    public static class Builder {
        private String id;
        private String image;
        private String title;
        private String url;
        private int type;
        private Context mContext;
        private Activity mActivity;
        private View shareView;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setImage(String image) {
            this.image = image;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setAnimConfig(Activity mActivity, View shareView) {
            this.mActivity = mActivity;
            this.shareView = shareView;
            return this;
        }

    }

    public static void launch(Builder builder) {
        Intent intent = new Intent(builder.mContext, TechDetailActivity.class);
        intent.putExtra(Constants.INTENT_TECH_DETAIL_ID, builder.id);
        intent.putExtra(Constants.INTENT_TECH_DETAIL_TITLE, builder.title);
        intent.putExtra(Constants.INTENT_TECH_DETAIL_TYPE, builder.type);
        intent.putExtra(Constants.INTENT_TECH_DETAIL_URL, builder.url);
        intent.putExtra(Constants.INTENT_TECH_DETAIL_IMAGE, builder.image);
        if (builder.shareView != null) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(builder.mActivity, builder.shareView, "shareView");
            builder.mContext.startActivity(intent, options.toBundle());
        } else {
            builder.mContext.startActivity(intent);
        }
    }

}
