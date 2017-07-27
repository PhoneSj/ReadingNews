package com.phonesj.news.ui.gank.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.BaseActivity;
import com.phonesj.news.base.contract.gank.WelfareDetailContract;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.presenter.gank.WelfareDetailPresenter;
import com.phonesj.news.util.ShareUtil;
import com.phonesj.news.util.SystemUtil;
import com.phonesj.news.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Phone on 2017/7/27.
 */

public class WelfareDetailActivity extends BaseActivity<WelfareDetailPresenter> implements WelfareDetailContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_girl_detail)
    ImageView ivGirlDetail;

    private PhotoViewAttacher attacher;

    private String id;
    private String url;

    private MenuItem likeMenuItem;
    private boolean isLiked;

    private RxPermissions rxPermissions;
    private Bitmap bitmap;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welfare_detail;
    }

    @Override
    protected void initEventAndData() {
        setToolbar(toolBar, "");
        Intent intent = getIntent();
        id = intent.getExtras().getString(Constants.INTENT_WELFARE_DETAIL_ID);
        url = intent.getExtras().getString(Constants.INTENT_WELFARE_DETAIL_URL);

        if (url != null) {
            Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                    ivGirlDetail.setImageBitmap(bitmap);
                    attacher = new PhotoViewAttacher(ivGirlDetail);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welfare_menu, menu);
        likeMenuItem = menu.findItem(R.id.action_like);
        isLiked = mPresenter.queryLikeId(id);
        setLikeState();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_like:
                isLiked = !isLiked;
                if (isLiked) {
                    likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
                    LikeBean bean = new LikeBean();
                    bean.setId(id);
                    bean.setImage(url);
                    bean.setType(Constants.TYPE_WELFARE);
                    bean.setTime(System.currentTimeMillis());
                    mPresenter.insertLikeBean(bean);
                } else {
                    likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
                    mPresenter.deleteLikeBean(id);
                }
                setLikeState();
                break;
            case R.id.action_share:
            case R.id.action_save:
                checkPermissionAndAction(item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPermissionAndAction(final int itemId) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(mContext);
        }
        rxPermissions
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean allow) throws Exception {
                    if (allow) {
                        switch (itemId) {
                            case R.id.action_share:
                                ShareUtil.shareImage(mContext, SystemUtil.saveBitmapToFile(mContext, url, bitmap, ivGirlDetail, true), "分享妹纸一枚");
                                break;
                            case R.id.action_save:
                                SystemUtil.saveBitmapToFile(mContext, url, bitmap, ivGirlDetail, false);
                                break;
                        }
                    } else {
                        ToastUtil.shortShow("获取写入权限失败");
                    }
                }
            });
    }

    private void setLikeState() {
        if (isLiked) {
            likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_p);
        } else {
            likeMenuItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finishAfterTransition();
        }
    }
}
