package com.phonesj.news.ui.zhihu.activity;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RootActivity;
import com.phonesj.news.base.contract.zhihu.ZhihuDetailContract;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.presenter.zhihu.ZhihuDetailPresenter;
import com.phonesj.news.util.HtmlUtil;
import com.phonesj.news.util.ShareUtil;
import com.phonesj.news.util.SystemUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Phone on 2017/7/18.
 */

public class ZhihuDetailActivity extends RootActivity<ZhihuDetailPresenter> implements ZhihuDetailContract.View {


    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.view_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout clpToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_main)
    WebView viewMain;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.tv_detail_bottom_like)
    TextView tvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView tvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView tvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout llDetailBottom;
    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;

    int id = 0;
    int allNum = 0;
    int shortNum = 0;
    int longNum = 0;

    boolean isBottomShow = true;//llDetailBottom是否已经显示了
    private String shareUrl;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        setToolbar(viewToolbar, "");
        Intent intent = getIntent();
        id = intent.getExtras().getInt(Constants.INTENT_ZHIHU_DETAIL_ID);
        //查询数据库，该id的新闻之前是否标记过
        mPresenter.queryLikeData(id);
        //获取网络数据
        mPresenter.getDetailData(id);
        mPresenter.getDetailExtraData(id);
        stateLoading();
        WebSettings settings = viewMain.getSettings();
        if (mPresenter.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (mPresenter.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            //缓存策略
            if (SystemUtil.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);//设置可运行js代码
        settings.setLoadWithOverviewMode(true);//web页面缩放至屏幕大小
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把所有内容放到WebView组件等宽的一列中
        settings.setSupportZoom(true);//支持缩放
        viewMain.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY && isBottomShow) {
                    //向下滑动，llDetailBottom隐藏
                    isBottomShow = false;
                    llDetailBottom.animate().translationY(llDetailBottom.getHeight());
                } else if (scrollY < oldScrollY && !isBottomShow) {
                    //向上滑动，llDetailBottom显示
                    isBottomShow = true;
                    llDetailBottom.animate().translationY(0);
                }
            }
        });
    }

    @Override
    public void showContent(ZhihuDetailBean info) {
        stateMain();
        String imgUrl = info.getImage();
        shareUrl = info.getShare_url();

        ImageLoader.load(mContext, info.getImage(), detailBarImage);
        clpToolbar.setTitle(info.getTitle());
        detailBarCopyright.setText(info.getImage_source());

        String htmlData = HtmlUtil.createHtmlData(info.getBody(), info.getCss(), info.getJs());
        viewMain.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtraInfo(ZhihuDetailExtraBean info) {
        tvDetailBottomLike.setText(String.format("%d个点赞", info.getPopularity()));
        tvDetailBottomComment.setText(String.format("%d条评论", info.getComments()));
        allNum = info.getComments();
        shortNum = info.getShort_comments();
        longNum = info.getLong_comments();
    }

    @Override
    public void setLikeButtonState(boolean isLike) {
        fabLike.setSelected(isLike);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && viewMain.canGoBack()) {
            //当游览器历史记录可以返回时，执行webView的返回操作
            viewMain.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_detail_bottom_comment, R.id.tv_detail_bottom_share, R.id.fab_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_detail_bottom_comment:
                Intent intent = new Intent(ZhihuDetailActivity.this, CommentActivity.class);
                intent.putExtra(Constants.INTENT_ZHIHU_COMMON_ID, id);
                intent.putExtra(Constants.INTENT_ZHIHU_COMMON_ALL_NUM, allNum);
                intent.putExtra(Constants.INTENT_ZHIHU_COMMONT_SHORT_NUM, shortNum);
                intent.putExtra(Constants.INTENT_ZHIHU_COMMONT_LONG_NUM, longNum);
                startActivity(intent);
                break;
            case R.id.tv_detail_bottom_share:
                ShareUtil.shareText(mContext, shareUrl, "分享一篇文章");
                break;
            case R.id.fab_like:
                if (fabLike.isSelected()) {
                    fabLike.setSelected(false);
                    mPresenter.deleteLikeData();
                } else {
                    fabLike.setSelected(true);
                    mPresenter.inertLikeData();
                }
                break;
        }
    }
}
